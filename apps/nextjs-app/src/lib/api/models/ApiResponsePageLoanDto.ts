/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { PageObject } from './PageObject';
export type ApiResponsePageLoanDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: PageObject;
    error?: ApiErrorResponse;
};

