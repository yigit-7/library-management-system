/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { AuthorDto } from './AuthorDto';
export type ApiResponseAuthorDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: AuthorDto;
    error?: ApiErrorResponse;
};

