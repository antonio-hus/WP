import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book';

@Injectable({providedIn: 'root'})
export class BookService {
  private apiUrl = 'http://localhost:8000/api';

  constructor(private http: HttpClient) { }

  // Get all books or filter by genre
  getBooks(genre: string = ''): Observable<Book[]> {
    let params = new HttpParams();
    if (genre) {
      params = params.set('genre', genre);
    }
    return this.http.get<Book[]>(`${this.apiUrl}/get_books.php`, { params });
  }

  // Get a single book by ID
  getBook(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiUrl}/get_book.php?id=${id}`);
  }

  // Add a new book
  addBook(book: Book): Observable<any> {
    const formData = new FormData();
    formData.append('title', book.title);
    formData.append('author', book.author);
    formData.append('pages', book.pages.toString());
    formData.append('genre', book.genre);

    return this.http.post<any>(`${this.apiUrl}/add_book.php`, formData);
  }

  // Update an existing book
  updateBook(book: Book): Observable<any> {
    const formData = new FormData();
    formData.append('id', book.id!.toString());
    formData.append('title', book.title);
    formData.append('author', book.author);
    formData.append('pages', book.pages.toString());
    formData.append('genre', book.genre);

    return this.http.post<any>(`${this.apiUrl}/edit_book.php`, formData);
  }

  // Delete a book
  deleteBook(id: number): Observable<any> {
    const formData = new FormData();
    formData.append('id', id.toString());

    return this.http.post<any>(`${this.apiUrl}/delete_book.php`, formData);
  }

  // Lend a book
  lendBook(id: number, lentTo: string): Observable<any> {
    const formData = new FormData();
    formData.append('id', id.toString());
    formData.append('lent_to', lentTo);

    return this.http.post<any>(`${this.apiUrl}/lend_book.php`, formData);
  }
}
