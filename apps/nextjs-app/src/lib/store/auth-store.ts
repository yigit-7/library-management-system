import { create } from 'zustand'
import { UserDto } from '@/lib/api'

interface AuthState {
  user: UserDto | null
  isAuthenticated: boolean
  isLoading: boolean
  setUser: (user: UserDto | null) => void
  setIsLoading: (isLoading: boolean) => void
  logout: () => void
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  isAuthenticated: false,
  isLoading: true,
  setUser: (user) => set({ user, isAuthenticated: !!user, isLoading: false }),
  setIsLoading: (isLoading) => set({ isLoading }),
  logout: () => set({ user: null, isAuthenticated: false, isLoading: false }),
}))
