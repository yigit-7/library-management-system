/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type CopyPatchRequest = {
    barcode?: string;
    status?: CopyPatchRequest.status;
};
export namespace CopyPatchRequest {
    export enum status {
        AVAILABLE = 'AVAILABLE',
        LOANED = 'LOANED',
        MAINTENANCE = 'MAINTENANCE',
        LOST = 'LOST',
        RETIRED = 'RETIRED',
    }
}

