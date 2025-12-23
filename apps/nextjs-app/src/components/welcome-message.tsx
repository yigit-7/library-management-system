"use client"

import { useAuth } from "@/hooks/use-auth"
import { UserDto } from "@/lib/api"

interface WelcomeMessageProps {
  initialUser?: UserDto | null
}

export function WelcomeMessage({ initialUser }: WelcomeMessageProps) {
  const { user: authUser, isAuthenticated: authIsAuthenticated } = useAuth()

  // Prefer initialUser (SSR) -> authUser (Client State)
  // Note: During client-side navigation after login, initialUser might be null but authUser will be populated
  const user = initialUser || authUser
  const isAuthenticated = !!initialUser || authIsAuthenticated

  return (
    <div className="space-y-4 max-w-3xl mx-auto w-full flex flex-col items-center justify-center min-h-[160px]">
      <h1 className="text-4xl font-bold tracking-tighter sm:text-5xl md:text-6xl lg:text-7xl animate-in fade-in zoom-in duration-500">
        {isAuthenticated
          ? `Welcome back${user?.firstName ? `, ${user.firstName}` : ' to your library'}.`
          : "Welcome to the Library."
        }
      </h1>
      <p className="mx-auto max-w-[700px] text-muted-foreground md:text-xl animate-in fade-in slide-in-from-bottom-4 duration-700">
        Search for your next favorite book, manage your loans, and explore our vast collection.
      </p>
    </div>
  )
}
