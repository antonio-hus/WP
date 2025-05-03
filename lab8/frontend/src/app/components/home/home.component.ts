import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    FormsModule
  ]
})
export class HomeComponent implements OnInit {
  books: Book[] = [];
  currentGenre: string = '';
  filterGenre: string = '';
  loading: boolean = false;
  error: string = '';

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(genre: string = ''): void {
    this.loading = true;
    this.bookService.getBooks(genre).subscribe({
      next: (books) => {
        this.books = books;
        this.currentGenre = genre;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error fetching books:', error);
        this.error = 'Failed to load books. Please try again later.';
        this.loading = false;
      }
    });
  }

  applyFilter(): void {
    this.loadBooks(this.filterGenre);
  }

  clearFilter(): void {
    this.filterGenre = '';
    this.loadBooks('');
  }
}
