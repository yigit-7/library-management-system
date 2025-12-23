/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { LoanDetailDto } from './LoanDetailDto';
export type ApiResponseLoanDetailDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: LoanDetailDto;
    error?: ApiErrorResponse;
};

