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
    authorIds: Array<number>;
    categoryId: number;
};

