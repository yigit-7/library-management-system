/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponseDashboardOverviewDto } from '../models/ApiResponseDashboardOverviewDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class DashboardControllerService {
    /**
     * @returns ApiResponseDashboardOverviewDto OK
     * @throws ApiError
     */
    public static getOverview(): CancelablePromise<ApiResponseDashboardOverviewDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/management/dashboard/overview',
        });
    }
}
