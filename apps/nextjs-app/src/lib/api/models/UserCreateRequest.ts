/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type UserCreateRequest = {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    roles: Array<'MEMBER' | 'LIBRARIAN' | 'ADMIN'>;
};

