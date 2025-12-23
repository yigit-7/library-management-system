/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserUpdateResponse } from './UserUpdateResponse';
export type ApiResponseUserUpdateResponse = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserUpdateResponse;
    error?: ApiErrorResponse;
};

