/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type CopyUpdateRequest = {
    barcode: string;
    status: CopyUpdateRequest.status;
};
export namespace CopyUpdateRequest {
    export enum status {
        AVAILABLE = 'AVAILABLE',
        LOANED = 'LOANED',
        MAINTENANCE = 'MAINTENANCE',
        LOST = 'LOST',
        RETIRED = 'RETIRED',
    }
}

