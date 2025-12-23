/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserPublicProfile } from './UserPublicProfile';
export type ApiResponseUserPublicProfile = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserPublicProfile;
    error?: ApiErrorResponse;
};

