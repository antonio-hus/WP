import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book';
import {CommonModule} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class EditBookComponent implements OnInit {
  bookForm: FormGroup;
  bookId: number = 0;
  isLoading: boolean = true;
  isSubmitting: boolean = false;
  errorMessage: string = '';
  notFound: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) {
    this.bookForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      author: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      pages: ['', [Validators.required, Validators.min(1)]],
      genre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.bookId = +id;
      this.loadBook();
    } else {
      this.router.navigate(['/']);
    }
  }

  loadBook(): void {
    this.isLoading = true;
    this.bookService.getBook(this.bookId).subscribe({
      next: (book) => {
        this.bookForm.patchValue({
          title: book.title,
          author: book.author,
          pages: book.pages,
          genre: book.genre
        });
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error fetching book:', error);
        this.errorMessage = 'Failed to load book details';
        this.isLoading = false;
      }
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
    const book: Book = {
      ...this.bookForm.value,
      id: this.bookId
    };

    this.bookService.updateBook(book).subscribe({
      next: (response) => {
        if (response?.status === 'ok') {
          this.router.navigate(['/']);
        } else {
          this.errorMessage = 'Failed to update book';
          this.isSubmitting = false;
        }
      },
      error: (error) => {
        console.error('Error updating book:', error);
        this.errorMessage = 'Error occurred while updating the book';
        this.isSubmitting = false;
      }
    });
  }
}
