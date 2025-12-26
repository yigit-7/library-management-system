"use client"

import * as React from "react"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"
import { motion } from "framer-motion"

const navItems = [
  {
    path: "/books",
    name: "Catalog",
  },
  {
    path: "/about",
    name: "About",
  },
  {
    path: "/contact",
    name: "Contact",
  },
]

export function MainNav() {
  const pathname = usePathname()
  const [hoveredPath, setHoveredPath] = React.useState<string | null>(null)
  const [prevHoveredPath, setPrevHoveredPath] = React.useState<string | null>(null)

  React.useEffect(() => {
    setPrevHoveredPath(hoveredPath)
  }, [hoveredPath])

  return (
    <nav 
      className="hidden md:flex items-center text-sm font-medium"
      onMouseLeave={() => setHoveredPath(null)}
    >
      {navItems.map((item) => {
        const isActive = pathname === item.path
        
        return (
          <Link
            key={item.path}
            href={item.path}
            className={cn(
              "relative px-6 py-3 transition-colors flex items-center justify-center h-12",
              isActive ? "text-foreground" : "text-accent-foreground"
            )}
            onMouseEnter={() => setHoveredPath(item.path)}
          >
            {hoveredPath === item.path && (
              <motion.div
                layoutId="navbar-hover"
                className="absolute left-0 right-0 -z-10 bg-accent border border-border border-b-2 rounded-md h-[36px]"
                style={{ top: 'calc(50% - 18px)' }}
                initial={{ opacity: prevHoveredPath ? 1 : 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{
                  type: "tween",
                  duration: 0.2,
                  ease: "easeInOut"
                }}
              />
            )}
            <span className="relative z-10">{item.name}</span>
          </Link>
        )
      })}
    </nav>
  )
}
