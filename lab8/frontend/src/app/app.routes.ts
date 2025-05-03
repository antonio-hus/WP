import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AddBookComponent } from './components/add-book/add-book.component';
import { EditBookComponent } from './components/edit-book/edit-book.component';
import { DeleteBookComponent } from './components/delete-book/delete-book.component';
import { LendBookComponent } from './components/lend-book/lend-book.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'add-book', component: AddBookComponent },
  { path: 'edit-book/:id', component: EditBookComponent },
  { path: 'delete-book/:id', component: DeleteBookComponent },
  { path: 'lend-book/:id', component: LendBookComponent },
  { path: '**', redirectTo: '' }
];
