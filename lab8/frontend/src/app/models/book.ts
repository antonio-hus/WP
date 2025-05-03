export interface Book {
  id?: number;
  title: string;
  author: string;
  pages: number;
  genre: string;
  lent_to?: string;
  lent_date?: string;
}
