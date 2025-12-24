/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AuthorDto } from './AuthorDto';
import type { CategoryDto } from './CategoryDto';
export type BookDto = {
    id?: number;
    isbn?: string;
    title?: string;
    description?: string;
    coverImageUrl?: string;
    price?: number;
    publisher?: string;
    publishedDate?: string;
    pageCount?: number;
    language?: string;
    format?: string;
    availableCopies?: number;
    availableLocation?: string;
    authors?: Array<AuthorDto>;
    category?: CategoryDto;
};

