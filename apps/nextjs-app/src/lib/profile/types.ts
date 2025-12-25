export type UserPublicProfile = {
  firstName: string;
  lastName: string;
}

export type UserPrivateProfile = {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
  phoneNumber?: string;
  address?: string;
  createdAt?: string;
}

export type UserEditProfileRequest = {
  firstName: string;
  lastName: string;
  phoneNumber?: string;
  address?: string;
}

export type UserEditProfileResponse = {
  firstName: string;
  lastName: string;
}

export type ChangePasswordRequest = {
  currentPassword?: string;
  newPassword?: string;
  confirmationPassword?: string;
}

export enum NotificationCategory {
  LOAN_OVERDUE = "LOAN_OVERDUE",
  LOAN_DUE_SOON = "LOAN_DUE_SOON",
  FINE_ISSUED = "FINE_ISSUED",
  FINE_PAID = "FINE_PAID",
  GENERAL_ANNOUNCEMENT = "GENERAL_ANNOUNCEMENT"
}

export enum NotificationChannel {
  EMAIL = "EMAIL",
  SMS = "SMS",
  PUSH_NOTIFICATION = "PUSH_NOTIFICATION"
}

export type UserNotificationPreferenceDto = {
  category: NotificationCategory;
  channels: NotificationChannel[];
}

export type UpdateNotificationPreferenceRequest = {
  category: NotificationCategory;
  channels: NotificationChannel[];
}
