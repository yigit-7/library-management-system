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

export default function BooksPage() {
  const { user } = useAuth()
  const router = useRouter()
  
  // Mock Data
  const books = [
    { id: 1, title: "Dune", author: "Frank Herbert", isbn: "9780441013593", category: "Sci-Fi", copies: 5 },
    { id: 2, title: "The Hobbit", author: "J.R.R. Tolkien", isbn: "9780547928227", category: "Fantasy", copies: 3 },
    { id: 3, title: "1984", author: "George Orwell", isbn: "9780451524935", category: "Dystopian", copies: 8 },
  ]

  return (
    <div className="flex flex-col gap-4">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold tracking-tight">Books</h1>
        <Button onClick={() => router.push("/dashboard/books/new")}>
          <Plus className="mr-2 h-4 w-4" /> Add Book
        </Button>
      </div>
      
      <div className="flex items-center gap-2">
        <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              type="search"
              placeholder="Search books..."
              className="pl-8"
            />
        </div>
      </div>

      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Title</TableHead>
              <TableHead>Author</TableHead>
              <TableHead>ISBN</TableHead>
              <TableHead>Category</TableHead>
              <TableHead className="text-right">Copies</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {books.map((book) => (
              <TableRow key={book.id}>
                <TableCell className="font-medium">{book.title}</TableCell>
                <TableCell>{book.author}</TableCell>
                <TableCell>{book.isbn}</TableCell>
                <TableCell>{book.category}</TableCell>
                <TableCell className="text-right">{book.copies}</TableCell>
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
