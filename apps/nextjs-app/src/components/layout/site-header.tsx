import Link from "next/link"
import { Button } from "@/components/ui/button"
import { ModeToggle } from "@/components/layout/mode-toggle"
import { UserNav } from "@/components/layout/user-nav"
import { BookOpen } from "lucide-react"
import { MainNav } from "@/components/layout/main-nav"
import { MobileNav } from "@/components/layout/mobile-nav"
import { getCurrentUser } from "@/lib/auth/auth-utils"

export async function SiteHeader() {
  const user = await getCurrentUser()

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container mx-auto flex h-14 items-center px-4 md:px-8 max-w-7xl">
        {/* Mobile Menu Trigger */}
        <MobileNav />

        {/* Left: Logo (Hidden on mobile if needed, or adjusted) */}
        <div className="flex items-center mr-4 md:mr-8">
          <Link className="flex items-center space-x-2 group" href="/apps/nextjs-app/public">
            <BookOpen className="h-6 w-6 text-primary group-hover:text-primary/80 transition-colors" />
            <span className="hidden font-bold sm:inline-block text-lg tracking-tight">
              Library System
            </span>
          </Link>
        </div>

        {/* Center: Navigation (Desktop) */}
        <div className="flex flex-1 items-center justify-center">
          <MainNav />
        </div>

        {/* Right: Auth & Theme */}
        <div className="flex items-center gap-2 ml-auto md:ml-8">
          <ModeToggle />
          {user ? (
            <UserNav user={user} />
          ) : (
            <>
              <Button variant="ghost" size="sm" asChild className="hidden sm:inline-flex">
                <Link href="/login">
                  Login
                </Link>
              </Button>
              <Button size="sm" asChild>
                <Link href="/register">
                  Register
                </Link>
              </Button>
            </>
          )}
        </div>
      </div>
    </header>
  )
}
