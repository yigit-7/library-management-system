"use client"

import { useAuth } from "@/hooks/use-auth"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Plus, Search } from "lucide-react"
import { ROLES } from "@/lib/constants"
import { useRouter } from "next/navigation"
import { useEffect } from "react"

export default function UsersPage() {
  const { user, isLoading } = useAuth()
  const router = useRouter()
  const isAdmin = user?.roles?.includes(ROLES.ADMIN)

  useEffect(() => {
    if (!isLoading && !isAdmin) {
        router.push("/dashboard")
    }
  }, [isLoading, isAdmin, router])

  if (isLoading || !isAdmin) {
      return null // Or a loading spinner
  }
  
  // Mock Data
  const users = [
    { id: 1, name: "John Doe", email: "john@example.com", role: "MEMBER", status: "ACTIVE" },
    { id: 2, name: "Jane Smith", email: "jane@example.com", role: "LIBRARIAN", status: "ACTIVE" },
    { id: 3, name: "Admin User", email: "admin@library.com", role: "ADMIN", status: "ACTIVE" },
  ]

  return (
    <div className="flex flex-col gap-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold tracking-tight">Users</h1>
        <Button>
          <Plus className="mr-2 h-4 w-4" /> Add User
        </Button>
      </div>
      
      <div className="flex items-center gap-2">
        <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              type="search"
              placeholder="Search users..."
              className="pl-8"
            />
        </div>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Name</TableHead>
              <TableHead>Email</TableHead>
              <TableHead>Role</TableHead>
              <TableHead>Status</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {users.map((u) => (
              <TableRow key={u.id}>
                <TableCell className="font-medium">{u.name}</TableCell>
                <TableCell>{u.email}</TableCell>
                <TableCell>{u.role}</TableCell>
                <TableCell>{u.status}</TableCell>
                <TableCell className="text-right">
                    <Button variant="ghost" size="sm">Edit</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}
