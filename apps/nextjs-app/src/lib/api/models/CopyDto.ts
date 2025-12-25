/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BookShortDto } from './BookShortDto';
export type CopyDto = {
    id?: number;
    book?: BookShortDto;
    barcode?: string;
    status?: CopyDto.status;
    location?: string;
};
export namespace CopyDto {
    export enum status {
        AVAILABLE = 'AVAILABLE',
        LOANED = 'LOANED',
        MAINTENANCE = 'MAINTENANCE',
        LOST = 'LOST',
        RETIRED = 'RETIRED',
    }
}

