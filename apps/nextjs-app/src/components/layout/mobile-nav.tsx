"use client"

import * as React from "react"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { BookOpen, Menu } from "lucide-react"

import { Button } from "@/components/ui/button"
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet"
import { cn } from "@/lib/utils"

export function MobileNav() {
  const [open, setOpen] = React.useState(false)
  const pathname = usePathname()

  const navItems = [
    { href: "/catalog", label: "Catalog" },
    { href: "/authors", label: "Authors" },
    { href: "/categories", label: "Categories" },
    { href: "/about", label: "About" },
    { href: "/contact", label: "Contact" },
  ]

  return (
    <Sheet open={open} onOpenChange={setOpen}>
      <SheetTrigger asChild>
        <Button
          variant="ghost"
          className="mr-4 px-0 text-base hover:bg-transparent focus-visible:bg-transparent focus-visible:ring-0 focus-visible:ring-offset-0 md:hidden"
        >
          <Menu className="h-6 w-6" />
          <span className="sr-only">Toggle Menu</span>
        </Button>
      </SheetTrigger>
      <SheetContent side="left" className="pr-0">
        <SheetHeader className="px-1">
            <SheetTitle asChild>
                <Link
                href="/apps/nextjs-app/public"
                className="flex items-center"
                onClick={() => setOpen(false)}
                >
                <BookOpen className="mr-2 h-4 w-4" />
                <span className="font-bold">Library System</span>
                </Link>
            </SheetTitle>
        </SheetHeader>
        <div className="my-4 h-[calc(100vh-8rem)] pb-10 pl-1">
          <div className="flex flex-col space-y-3">
            {navItems.map(
              (item) => (
                <Link
                  key={item.href}
                  href={item.href}
                  onClick={() => setOpen(false)}
                  className={cn(
                    "text-foreground/70 transition-colors hover:text-foreground",
                    pathname === item.href && "text-foreground font-medium"
                  )}
                >
                  {item.label}
                </Link>
              )
            )}
          </div>
        </div>
      </SheetContent>
    </Sheet>
  )
}
