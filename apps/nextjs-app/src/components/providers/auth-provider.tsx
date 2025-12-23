"use client"

import React, { useEffect } from "react"
import { useAuthStore } from "@/lib/store/auth-store"
import { UserControllerService, UserDto } from "@/lib/api"
import { OpenAPI } from "@/lib/api/core/OpenAPI"
import { logoutAction } from "@/app/actions/auth"
import { useRouter } from "next/navigation"
import { useApiQuery } from "@/lib/api-client/api-hooks"
import { setupAxiosInterceptors } from "@/lib/api-client/axios-interceptor"

interface AuthProviderProps {
  children: React.ReactNode
  accessToken?: string
}

export function AuthProvider({ children, accessToken }: AuthProviderProps) {
  const setUser = useAuthStore((state) => state.setUser)
  const setIsLoading = useAuthStore((state) => state.setIsLoading)
  const router = useRouter()

  // Setup Axios Interceptors once on mount
  useEffect(() => {
    setupAxiosInterceptors();
  }, []);

  // Configure OpenAPI globally
  // We do this in a useEffect to avoid side effects during render
  // and to satisfy ESLint rules about immutability during render
  useEffect(() => {
    OpenAPI.BASE = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";
    if (accessToken) {
      OpenAPI.TOKEN = accessToken
    }
  }, [accessToken])

  // Use React Query to fetch the profile
  const { data: userProfile, isError, isLoading: isQueryLoading } = useApiQuery(
    ['my-profile'],
    UserControllerService.getMyProfile,
    [],
    {
      enabled: !!accessToken, // Only fetch if token exists
      retry: false, // Don't retry if 401/403
      staleTime: 1000 * 60 * 5, // Cache profile for 5 minutes
    }
  )

  useEffect(() => {
    if (!accessToken) {
      setUser(null)
      setIsLoading(false)
      return
    }

    if (userProfile?.data) {
      // UserPrivateProfile and UserDto have the same structure
      setUser(userProfile.data as unknown as UserDto)
      setIsLoading(false)
    } else if (isError) {
      console.error("AuthProvider: Failed to fetch user profile")
      setUser(null)
      setIsLoading(false)
      // If fetch fails (likely 401), logout
      logoutAction().then(() => router.refresh())
    } else if (!isQueryLoading) {
        // Case where query finished but no data (shouldn't happen with successful response)
        // or initial state
    }

    // Sync loading state
    setIsLoading(isQueryLoading)

  }, [accessToken, userProfile, isError, isQueryLoading, setUser, setIsLoading, router])

  return <>{children}</>
}
