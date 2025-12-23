"use client"

import { QueryClientProvider } from "@tanstack/react-query"
import { queryClient } from "@/lib/api-client/query-client"
import { ReactNode } from "react"

export function QueryProvider({ children }: { children: ReactNode }) {
  return (
    <QueryClientProvider client={queryClient}>
      {children}
    </QueryClientProvider>
  )
}
