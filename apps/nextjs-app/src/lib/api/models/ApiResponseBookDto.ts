/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { BookDto } from './BookDto';
export type ApiResponseBookDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: BookDto;
    error?: ApiErrorResponse;
};

