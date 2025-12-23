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
import { Search } from "lucide-react"
import { Badge } from "@/components/ui/badge"

export default function FinesPage() {
  // Mock Data
  const fines = [
    { id: 1, user: "John Doe", amount: 15.50, reason: "Overdue Book: Dune", status: "UNPAID" },
    { id: 2, user: "Jane Smith", amount: 5.00, reason: "Damaged Book", status: "PAID" },
  ]

  return (
    <div className="flex flex-col gap-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold tracking-tight">Fines</h1>
      </div>
      
      <div className="flex items-center gap-2">
        <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              type="search"
              placeholder="Search fines..."
              className="pl-8"
            />
        </div>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>User</TableHead>
              <TableHead>Amount</TableHead>
              <TableHead>Reason</TableHead>
              <TableHead>Status</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {fines.map((fine) => (
              <TableRow key={fine.id}>
                <TableCell className="font-medium">{fine.user}</TableCell>
                <TableCell>${fine.amount.toFixed(2)}</TableCell>
                <TableCell>{fine.reason}</TableCell>
                <TableCell>
                    <Badge variant={fine.status === 'PAID' ? 'secondary' : 'destructive'}>
                        {fine.status}
                    </Badge>
                </TableCell>
                <TableCell className="text-right">
                    {fine.status === 'UNPAID' && <Button variant="outline" size="sm">Pay</Button>}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}
