import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class AddBookComponent {
  bookForm: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private router: Router
  ) {
    this.bookForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      author: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      pages: ['', [Validators.required, Validators.min(1)]],
      genre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]]
    });
  }

  onSubmit(): void {
    if (this.bookForm.invalid) {
      // Mark all fields as touched to trigger validation display
      Object.keys(this.bookForm.controls).forEach(key => {
        const control = this.bookForm.get(key);
        control?.markAsTouched();
      });
      return;
    }

    this.isSubmitting = true;
    const book: Book = this.bookForm.value;

    this.bookService.addBook(book).subscribe({
      next: (response) => {
        if (response?.status === 'ok') {
          this.router.navigate(['/']);
        } else {
          this.errorMessage = 'Failed to add book';
          this.isSubmitting = false;
        }
      },
      error: (error) => {
        console.error('Error adding book:', error);
        this.errorMessage = 'Error occurred while adding the book';
        this.isSubmitting = false;
      }
    });
  }
}
