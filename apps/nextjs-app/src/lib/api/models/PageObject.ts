/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { UserDto } from './UserDto';
export type PageObject = {
    totalPages?: number;
    totalElements?: number;
    size?: number;
    content?: Array<UserDto>;
    number?: number;
    sort?: SortObject;
    numberOfElements?: number;
    first?: boolean;
    last?: boolean;
    pageable?: PageableObject;
    empty?: boolean;
};

