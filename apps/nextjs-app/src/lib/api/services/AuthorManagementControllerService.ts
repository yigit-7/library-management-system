/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponseAuthorDto } from '../models/ApiResponseAuthorDto';
import type { ApiResponseVoid } from '../models/ApiResponseVoid';
import type { AuthorCreateRequest } from '../models/AuthorCreateRequest';
import type { AuthorUpdateRequest } from '../models/AuthorUpdateRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AuthorManagementControllerService {
    /**
     * @param id
     * @param requestBody
     * @returns ApiResponseAuthorDto OK
     * @throws ApiError
     */
    public static updateAuthor(
        id: number,
        requestBody: AuthorUpdateRequest,
    ): CancelablePromise<ApiResponseAuthorDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/management/authors/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param id
     * @returns ApiResponseVoid OK
     * @throws ApiError
     */
    public static deleteAuthor(
        id: number,
    ): CancelablePromise<ApiResponseVoid> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/management/authors/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param requestBody
     * @returns ApiResponseAuthorDto Created
     * @throws ApiError
     */
    public static createAuthor(
        requestBody: AuthorCreateRequest,
    ): CancelablePromise<ApiResponseAuthorDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/management/authors',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
