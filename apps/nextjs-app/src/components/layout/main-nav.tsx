"use client"

import * as React from "react"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"

export function MainNav() {
  const pathname = usePathname()

  return (
    <nav className="hidden md:flex items-center gap-6 text-sm font-medium">
      <Link
        href="/books"
        className={cn(
          "transition-colors hover:text-foreground/80",
          pathname === "/books" ? "text-foreground" : "text-foreground/60"
        )}
      >
        Catalog
      </Link>
      <Link
        href="/about"
        className={cn(
          "transition-colors hover:text-foreground/80",
          pathname === "/about" ? "text-foreground" : "text-foreground/60"
        )}
      >
        About
      </Link>
      <Link
        href="/contact"
        className={cn(
          "transition-colors hover:text-foreground/80",
          pathname === "/contact" ? "text-foreground" : "text-foreground/60"
        )}
      >
        Contact
      </Link>
    </nav>
  )
}
