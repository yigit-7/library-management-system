/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { FineDto } from './FineDto';
export type ApiResponseFineDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: FineDto;
    error?: ApiErrorResponse;
};

