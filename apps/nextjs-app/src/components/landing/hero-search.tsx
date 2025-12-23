"use client"

import * as React from "react"
import { useRouter } from "next/navigation"
import { Search } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

export function HeroSearch() {
  const router = useRouter()
  const [query, setQuery] = React.useState("")

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    if (!query.trim()) return

    const params = new URLSearchParams()
    params.set("q", query.trim())
    router.push(`/books?${params.toString()}`)
  }

  return (
    <form 
      onSubmit={handleSearch} 
      className="w-full max-w-2xl mx-auto flex flex-col sm:flex-row items-center gap-3"
    >
      <div className="relative flex-1 w-full">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
        <Input
          className="h-12 sm:h-14 pl-10 text-base sm:text-lg bg-background shadow-sm"
          placeholder="Search by Title, Author, ISBN..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
      </div>
      <Button
        size="lg"
        className="h-12 sm:h-14 px-8 text-lg"
        type="submit"
      >
        Search
      </Button>
    </form>
  )
}
