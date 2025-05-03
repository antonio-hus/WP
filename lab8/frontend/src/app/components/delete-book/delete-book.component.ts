import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book';
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";

@Component({
  standalone: true,
  selector: 'app-delete-book',
  templateUrl: './delete-book.component.html',
  styleUrls: ['./delete-book.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class DeleteBookComponent implements OnInit {
  book: Book | null = null;
  bookId: number = 0;
  isLoading: boolean = true;
  isDeleting: boolean = false;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) { }

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
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error fetching book:', error);
        this.errorMessage = 'Failed to load book details';
        this.isLoading = false;
      }
    });
  }

  confirmDelete(): void {
    this.isDeleting = true;
    this.bookService.deleteBook(this.bookId).subscribe({
      next: (response) => {
        if (response?.status === 'ok') {
          this.router.navigate(['/']);
        } else {
          this.errorMessage = 'Failed to delete book';
          this.isDeleting = false;
        }
      },
      error: (error) => {
        console.error('Error deleting book:', error);
        this.errorMessage = 'Error occurred while deleting the book';
        this.isDeleting = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
