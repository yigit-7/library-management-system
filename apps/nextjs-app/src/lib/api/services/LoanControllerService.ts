/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ApiResponsePageLoanUserSummaryDto } from '../models/ApiResponsePageLoanUserSummaryDto';
import type { LoanUserSearchRequest } from '../models/LoanUserSearchRequest';
import type { Pageable } from '../models/Pageable';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class LoanControllerService {
    /**
     * @param request
     * @param pageable
     * @returns ApiResponsePageLoanUserSummaryDto OK
     * @throws ApiError
     */
    public static getMyLoans(
        request: LoanUserSearchRequest,
        pageable: Pageable,
    ): CancelablePromise<ApiResponsePageLoanUserSummaryDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/loans/my-loans',
            query: {
                'request': request,
                'pageable': pageable,
            },
        });
    }
}
