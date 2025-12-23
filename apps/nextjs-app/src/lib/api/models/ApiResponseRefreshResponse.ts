/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { RefreshResponse } from './RefreshResponse';
export type ApiResponseRefreshResponse = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: RefreshResponse;
    error?: ApiErrorResponse;
};

