/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserNotificationPreferenceDto } from './UserNotificationPreferenceDto';
export type ApiResponseUserNotificationPreferenceDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserNotificationPreferenceDto;
    error?: ApiErrorResponse;
};

