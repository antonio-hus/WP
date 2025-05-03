import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book';
import {CommonModule} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-lend-book',
  templateUrl: './lend-book.component.html',
  styleUrls: ['./lend-book.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class LendBookComponent implements OnInit {
  lendForm: FormGroup;
  book: Book | null = null;
  bookId: number = 0;
  isLoading: boolean = true;
  isSubmitting: boolean = false;
  errorMessage: string = '';
  alreadyLent: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) {
    this.lendForm = this.fb.group({
      lent_to: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]]
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
        this.book = book;
        // Check if the book is already lent
        if (book.lent_to) {
          this.alreadyLent = true;
          this.lendForm.get('lent_to')?.setValue(book.lent_to);
          this.lendForm.disable();
        }
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
    if (this.lendForm.invalid) {
      // Mark fields as touched to trigger validation display
      this.lendForm.get('lent_to')?.markAsTouched();
      return;
    }

    this.isSubmitting = true;
    const lentTo = this.lendForm.get('lent_to')?.value;

    this.bookService.lendBook(this.bookId, lentTo).subscribe({
      next: (response) => {
        if (response?.status === 'ok') {
          this.router.navigate(['/']);
        } else {
          this.errorMessage = 'Failed to lend book';
          this.isSubmitting = false;
        }
      },
      error: (error) => {
        console.error('Error lending book:', error);
        this.errorMessage = 'Error occurred while lending the book';
        this.isSubmitting = false;
      }
    });
  }
}
