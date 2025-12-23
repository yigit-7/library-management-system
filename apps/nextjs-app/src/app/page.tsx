import { FeaturesSection } from "@/components/features-section"
import { SiteHeader } from "@/components/site-header"
import { HeroSection } from "@/components/hero-section"
import { cookies } from "next/headers"
import { decodeJwt, JWTPayload } from "jose"
import { UserDto } from "@/lib/api"

interface CustomJwtPayload extends JWTPayload {
  email?: string
  firstName?: string
  lastName?: string
  roles?: string[]
}

export default async function Home() {
  const cookieStore = await cookies()
  const accessToken = cookieStore.get("accessToken")?.value
  
  let user: UserDto | null = null

  if (accessToken) {
    try {
      const payload = decodeJwt(accessToken) as CustomJwtPayload
      user = {
        email: payload.email || payload.sub,
        firstName: payload.firstName || "User",
        lastName: payload.lastName || "",
        roles: (payload.roles as Array<'MEMBER' | 'LIBRARIAN' | 'ADMIN'>) || []
      }
    } catch (e) {
      console.error("Failed to decode token on Home page", e)
    }
  }

  return (
    <div className="flex min-h-screen flex-col">
      <SiteHeader />
      <main className="flex-1">
        <HeroSection initialUser={user} />
        <FeaturesSection />
      </main>
      <footer className="py-6 md:px-8 md:py-0 border-t">
        <div className="container mx-auto flex flex-col items-center justify-between gap-4 md:h-24 md:flex-row px-4 md:px-6">
          <p className="text-center text-sm leading-loose text-muted-foreground md:text-left">
            Built by{" "}
            <a
              href="#"
              target="_blank"
              rel="noreferrer"
              className="font-medium underline underline-offset-4"
            >
              Yiğit
            </a>
            . The source code is available on{" "}
            <a
              href="https://github.com/yigit-7/library-management-system"
              target="_blank"
              rel="noreferrer"
              className="font-medium underline underline-offset-4"
            >
              GitHub
            </a>
            .
          </p>
        </div>
      </footer>
    </div>
  )
}
