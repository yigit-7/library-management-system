/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserPrivateProfile } from './UserPrivateProfile';
export type ApiResponseUserPrivateProfile = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserPrivateProfile;
    error?: ApiErrorResponse;
};

