/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type UpdateNotificationPreferenceRequest = {
    category: UpdateNotificationPreferenceRequest.category;
    channels: Array<'EMAIL' | 'SMS' | 'PUSH_NOTIFICATION'>;
};
export namespace UpdateNotificationPreferenceRequest {
    export enum category {
        LOAN_OVERDUE = 'LOAN_OVERDUE',
        LOAN_DUE_SOON = 'LOAN_DUE_SOON',
        FINE_ISSUED = 'FINE_ISSUED',
        FINE_PAID = 'FINE_PAID',
        GENERAL_ANNOUNCEMENT = 'GENERAL_ANNOUNCEMENT',
    }
}

