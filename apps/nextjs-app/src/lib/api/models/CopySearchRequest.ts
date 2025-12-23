/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type CopySearchRequest = {
    barcode?: string;
    isbn?: string;
    bookId?: number;
    copyStatus?: CopySearchRequest.copyStatus;
};
export namespace CopySearchRequest {
    export enum copyStatus {
        AVAILABLE = 'AVAILABLE',
        LOANED = 'LOANED',
        MAINTENANCE = 'MAINTENANCE',
        LOST = 'LOST',
        RETIRED = 'RETIRED',
    }
}

