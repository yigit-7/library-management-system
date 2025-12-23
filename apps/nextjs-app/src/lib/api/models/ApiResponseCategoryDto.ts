/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { CategoryDto } from './CategoryDto';
export type ApiResponseCategoryDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: CategoryDto;
    error?: ApiErrorResponse;
};

