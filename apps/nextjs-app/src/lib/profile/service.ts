import { 
  UserPublicProfile, 
  UserPrivateProfile, 
  UserEditProfileRequest, 
  UserEditProfileResponse, 
  ChangePasswordRequest,
  UserNotificationPreferenceDto,
  UpdateNotificationPreferenceRequest
} from "./types";
import { OpenAPI } from "@/lib/api/core/OpenAPI";
import { ApiError } from "@/lib/api/core/ApiError";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

async function fetchJson<T>(url: string, options: RequestInit = {}): Promise<T> {
  const token = OpenAPI.TOKEN;
  
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...(token ? { "Authorization": `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(url, {
    ...options,
    headers,
  });

  if (!response.ok) {
    if (response.status === 401) {
        throw new Error("Unauthorized");
    }
    const errorBody = await response.json().catch(() => ({}));
    
    // Throw ApiError to be compatible with parseApiError utility
    throw new ApiError(
        {
            method: options.method as any || 'GET',
            url: url,
        }, 
        {
            ok: false,
            status: response.status,
            statusText: response.statusText,
            body: errorBody,
            url: url
        }, 
        errorBody.message || `API Error: ${response.status}`
    );
  }

  const json = await response.json();
  return json.data; 
}

export const profileService = {
  getMyProfile: async (): Promise<UserPrivateProfile> => {
    return fetchJson<UserPrivateProfile>(`${API_BASE_URL}/api/users/me`);
  },

  getUserPublicProfile: async (id: number): Promise<UserPublicProfile> => {
    return fetchJson<UserPublicProfile>(`${API_BASE_URL}/api/users/${id}`);
  },

  editMyProfile: async (data: UserEditProfileRequest): Promise<UserEditProfileResponse> => {
    return fetchJson<UserEditProfileResponse>(`${API_BASE_URL}/api/users/me`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  changePassword: async (data: ChangePasswordRequest): Promise<void> => {
      // Endpoint not yet implemented in backend
      console.warn("Change password endpoint not implemented yet.");
      return fetchJson<void>(`${API_BASE_URL}/api/users/me/password`, {
          method: "POST",
          body: JSON.stringify(data)
      });
  },

  getNotificationPreferences: async (): Promise<UserNotificationPreferenceDto[]> => {
    return fetchJson<UserNotificationPreferenceDto[]>(`${API_BASE_URL}/api/notification-preferences`);
  },

  updateNotificationPreference: async (data: UpdateNotificationPreferenceRequest): Promise<UserNotificationPreferenceDto> => {
    return fetchJson<UserNotificationPreferenceDto>(`${API_BASE_URL}/api/notification-preferences`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }
};
