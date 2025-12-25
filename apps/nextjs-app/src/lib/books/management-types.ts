export type BookCreateRequest = {
  isbn: string;
  title: string;
  description: string;
  coverImageUrl?: string;
  price: number;
  publisher?: string;
  publishedDate?: string;
  pageCount?: number;
  language?: string;
  format?: string;
  authorIds: number[];
  categoryId: number;
}

export type BookUpdateRequest = {
  isbn: string;
  title: string;
  description: string;
  coverImageUrl: string;
  price: number;
  publisher?: string;
  publishedDate?: string;
  pageCount?: number;
  language?: string;
  format?: string;
  authorIds: number[];
  categoryId: number;
}
