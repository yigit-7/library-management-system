/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserEditProfileResponse } from './UserEditProfileResponse';
export type ApiResponseUserEditProfileResponse = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserEditProfileResponse;
    error?: ApiErrorResponse;
};

