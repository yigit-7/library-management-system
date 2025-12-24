/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
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
    authorIds: Array<number>;
    categoryId: number;
};

