"use client"

import { useAuthStore } from "@/lib/store/auth-store"

export function useAuth() {
  const { user, isAuthenticated, isLoading } = useAuthStore()
  return { user, isAuthenticated, isLoading }
}
