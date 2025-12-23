import { cookies } from "next/headers"
import { UserDto } from "@/lib/api"

export interface Session {
  user: UserDto | null
  accessToken: string | null
  isAuthenticated: boolean
}

export async function getSession(): Promise<Session> {
  const cookieStore = await cookies()
  const accessToken = cookieStore.get("accessToken")?.value

  if (!accessToken) {
    return { isAuthenticated: false, user: null, accessToken: null }
  }

  try {
    // We use native fetch here to avoid OpenAPI singleton race conditions on the server
    const baseUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080"
    const response = await fetch(`${baseUrl}/api/users/me`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      cache: "no-store", // Ensure we always get fresh data
    })

    if (!response.ok) {
      // If the token is invalid (401), we return unauthenticated
      return { isAuthenticated: false, user: null, accessToken: null }
    }

    const user = (await response.json()) as UserDto
    return { isAuthenticated: true, user, accessToken }
  } catch (error) {
    console.error("Failed to fetch session:", error)
    return { isAuthenticated: false, user: null, accessToken: null }
  }
}
