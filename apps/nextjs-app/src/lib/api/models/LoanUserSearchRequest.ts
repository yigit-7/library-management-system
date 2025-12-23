/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type LoanUserSearchRequest = {
    bookTitle?: string;
    isbn?: string;
    status?: LoanUserSearchRequest.status;
    overdue?: boolean;
};
export namespace LoanUserSearchRequest {
    export enum status {
        ACTIVE = 'ACTIVE',
        OVERDUE = 'OVERDUE',
        RETURNED = 'RETURNED',
        RETURNED_OVERDUE = 'RETURNED_OVERDUE',
        RETURNED_DAMAGED = 'RETURNED_DAMAGED',
        LOST = 'LOST',
    }
}

