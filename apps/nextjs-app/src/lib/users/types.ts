export type Role = 'ADMIN' | 'LIBRARIAN' | 'MEMBER';

export type User = {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  roles: Role[];
  phoneNumber?: string;
  address?: string;
  createdAt?: string;
  updatedAt?: string;
  active: boolean;
  locked: boolean;
}

export type UserCreateRequest = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  roles: Role[];
  phoneNumber?: string;
  address?: string;
}

export type UserUpdateRequest = {
  firstName?: string;
  lastName?: string;
  roles?: Role[];
  phoneNumber?: string;
  address?: string;
}

export type UserBanRequest = {
  reason: string;
}

export type UserSearchParams = {
  page?: number;
  size?: number;
  sort?: string;
  // Add search/filter params if backend supports them in getAllUsers (currently it seems to only take pageable)
}
