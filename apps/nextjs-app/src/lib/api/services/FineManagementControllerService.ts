/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponseFineDto } from '../models/ApiResponseFineDto';
import type { ApiResponsePageFineDto } from '../models/ApiResponsePageFineDto';
import type { ApiResponseString } from '../models/ApiResponseString';
import type { FineCreateRequest } from '../models/FineCreateRequest';
import type { FinePatchRequest } from '../models/FinePatchRequest';
import type { FineSearchRequest } from '../models/FineSearchRequest';
import type { FineUpdateRequest } from '../models/FineUpdateRequest';
import type { Pageable } from '../models/Pageable';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class FineManagementControllerService {
    /**
     * @param id
     * @returns ApiResponseFineDto OK
     * @throws ApiError
     */
    public static getFineById(
        id: number,
    ): CancelablePromise<ApiResponseFineDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/management/fines/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @param requestBody
     * @returns ApiResponseFineDto OK
     * @throws ApiError
     */
    public static updateFine(
        id: number,
        requestBody: FineUpdateRequest,
    ): CancelablePromise<ApiResponseFineDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/management/fines/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param id
     * @returns ApiResponseString OK
     * @throws ApiError
     */
    public static deleteFine(
        id: number,
    ): CancelablePromise<ApiResponseString> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/management/fines/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @param requestBody
     * @returns ApiResponseFineDto OK
     * @throws ApiError
     */
    public static patchFine(
        id: number,
        requestBody: FinePatchRequest,
    ): CancelablePromise<ApiResponseFineDto> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/management/fines/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param request
     * @param pageable
     * @returns ApiResponsePageFineDto OK
     * @throws ApiError
     */
    public static getAllFines(
        request: FineSearchRequest,
        pageable: Pageable,
    ): CancelablePromise<ApiResponsePageFineDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/management/fines',
            query: {
                'request': request,
                'pageable': pageable,
            },
        });
    }
    /**
     * @param requestBody
     * @returns ApiResponseFineDto Created
     * @throws ApiError
     */
    public static createFine(
        requestBody: FineCreateRequest,
    ): CancelablePromise<ApiResponseFineDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/management/fines',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
