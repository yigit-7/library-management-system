/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiErrorResponse } from './ApiErrorResponse';
import type { UserDto } from './UserDto';
export type ApiResponseUserDto = {
    success?: boolean;
    timestamp?: string;
    message?: string;
    data?: UserDto;
    error?: ApiErrorResponse;
};

