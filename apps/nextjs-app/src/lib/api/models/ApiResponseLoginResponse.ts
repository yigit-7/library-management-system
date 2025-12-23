/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { LoginResponse } from './LoginResponse';
export type ApiResponseLoginResponse = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: LoginResponse;
    error?: ApiErrorResponse;
};

