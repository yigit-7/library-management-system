import * as React from "react"
import { UserDto } from "@/lib/api"
import { HeroSearch } from "@/components/hero-search"
import { WelcomeMessage } from "@/components/welcome-message"

interface HeroSectionProps {
  initialUser?: UserDto | null
}

export function HeroSection({ initialUser }: HeroSectionProps) {
  return (
    <section className="relative flex flex-col items-center justify-center py-24 px-4 md:px-6 lg:py-32 text-center space-y-8 bg-background min-h-[600px]">
      {/* Typography (Client Component for Auth State) */}
      <WelcomeMessage initialUser={initialUser} />

      {/* Search Component (Client Component for Interactivity) */}
      <HeroSearch />
    </section>
  )
}
