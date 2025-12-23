/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { CopyDto } from './CopyDto';
export type ApiResponseCopyDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: CopyDto;
    error?: ApiErrorResponse;
};

