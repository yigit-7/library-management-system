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

export default function LoansPage() {
  // Mock Data
  const loans = [
    { id: 1, user: "John Doe", book: "Dune", loanDate: "2023-10-01", dueDate: "2023-10-15", status: "ACTIVE" },
    { id: 2, user: "Jane Smith", book: "The Hobbit", loanDate: "2023-09-20", dueDate: "2023-10-04", status: "OVERDUE" },
    { id: 3, user: "Alice Johnson", book: "1984", loanDate: "2023-10-05", dueDate: "2023-10-19", status: "RETURNED" },
  ]

  return (
    <div className="flex flex-col gap-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold tracking-tight">Loans</h1>
        <Button>New Loan</Button>
      </div>
      
      <div className="flex items-center gap-2">
        <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              type="search"
              placeholder="Search loans..."
              className="pl-8"
            />
        </div>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>User</TableHead>
              <TableHead>Book</TableHead>
              <TableHead>Loan Date</TableHead>
              <TableHead>Due Date</TableHead>
              <TableHead>Status</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loans.map((loan) => (
              <TableRow key={loan.id}>
                <TableCell className="font-medium">{loan.user}</TableCell>
                <TableCell>{loan.book}</TableCell>
                <TableCell>{loan.loanDate}</TableCell>
                <TableCell>{loan.dueDate}</TableCell>
                <TableCell>
                    <Badge variant={loan.status === 'ACTIVE' ? 'default' : loan.status === 'OVERDUE' ? 'destructive' : 'secondary'}>
                        {loan.status}
                    </Badge>
                </TableCell>
                <TableCell className="text-right">
                    {loan.status === 'ACTIVE' && <Button variant="outline" size="sm">Return</Button>}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}
