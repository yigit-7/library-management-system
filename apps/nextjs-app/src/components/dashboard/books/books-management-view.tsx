"use client"

import { useState } from "react"
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query"
import { bookService } from "@/lib/books/service"
import { bookManagementService } from "@/lib/books/management-service"
import { BookCreateRequest } from "@/lib/books/management-types"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Skeleton } from "@/components/ui/skeleton"
import { useToast } from "@/components/ui/use-toast"
import { 
  MoreHorizontal, 
  Plus, 
  Search, 
  ChevronLeft, 
  ChevronRight, 
  Trash, 
  Edit 
} from "lucide-react"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"

export function BooksManagementView() {
  const [page, setPage] = useState(0)
  const [searchQuery, setSearchQuery] = useState("")
  const [isCreateOpen, setIsCreateOpen] = useState(false)
  
  const { toast } = useToast()
  const queryClient = useQueryClient()

  // Fetch Books
  const { data: booksData, isLoading } = useQuery({
    queryKey: ['books-management', page, searchQuery],
    queryFn: () => bookService.getBooks({ 
        page, 
        size: 10, 
        search: searchQuery || undefined
    })
  })

  const books = booksData?.content || []
  // Fix: Access nested page object for pagination info
  const totalPages = booksData?.page?.totalPages || 0

  // Delete Mutation
  const deleteBookMutation = useMutation({
    mutationFn: (id: number) => bookManagementService.deleteBook(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['books-management'] })
      toast({ title: "Book deleted", description: "Book has been deleted successfully." })
    },
    onError: (error: Error) => toast({ title: "Error", description: error.message, variant: "destructive" })
  })

  return (
    <div className="space-y-4">
      {/* Filters */}
      <div className="flex items-center justify-between">
        <div className="relative w-full sm:w-64">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input 
                placeholder="Search books..." 
                className="pl-9"
                value={searchQuery}
                onChange={(e) => { setSearchQuery(e.target.value); setPage(0); }}
            />
        </div>
        <Button onClick={() => setIsCreateOpen(true)}>
          <Plus className="mr-2 h-4 w-4" /> Add Book
        </Button>
      </div>

      {/* Table */}
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Title</TableHead>
              <TableHead>Author</TableHead>
              <TableHead>Category</TableHead>
              <TableHead>Price</TableHead>
              <TableHead>Stock</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {isLoading ? (
              Array.from({ length: 5 }).map((_, i) => (
                <TableRow key={i}>
                  <TableCell><Skeleton className="h-4 w-8" /></TableCell>
                  <TableCell><Skeleton className="h-4 w-48" /></TableCell>
                  <TableCell><Skeleton className="h-4 w-32" /></TableCell>
                  <TableCell><Skeleton className="h-4 w-24" /></TableCell>
                  <TableCell><Skeleton className="h-4 w-16" /></TableCell>
                  <TableCell><Skeleton className="h-4 w-12" /></TableCell>
                  <TableCell><Skeleton className="h-8 w-8 ml-auto" /></TableCell>
                </TableRow>
              ))
            ) : books.length === 0 ? (
              <TableRow>
                <TableCell colSpan={7} className="h-24 text-center">
                  No books found.
                </TableCell>
              </TableRow>
            ) : (
              books.map((book) => (
                <TableRow key={book.id}>
                  <TableCell>{book.id}</TableCell>
                  <TableCell className="font-medium line-clamp-1" title={book.title}>{book.title}</TableCell>
                  <TableCell>{book.authors.map(a => a.name).join(", ")}</TableCell>
                  <TableCell>
                    <Badge variant="outline">{book.category.name}</Badge>
                  </TableCell>
                  <TableCell>${book.price}</TableCell>
                  <TableCell>
                    <Badge variant={book.availableCopies && book.availableCopies > 0 ? "secondary" : "destructive"}>
                        {book.availableCopies}
                    </Badge>
                  </TableCell>
                  <TableCell className="text-right">
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" className="h-8 w-8 p-0">
                          <span className="sr-only">Open menu</span>
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuLabel>Actions</DropdownMenuLabel>
                        <DropdownMenuItem>
                            <Edit className="mr-2 h-4 w-4" /> Edit Details
                        </DropdownMenuItem>
                        <DropdownMenuSeparator />
                        <DropdownMenuItem onClick={() => deleteBookMutation.mutate(book.id)} className="text-destructive focus:text-destructive">
                            <Trash className="mr-2 h-4 w-4" /> Delete Book
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </div>

      {/* Pagination */}
      <div className="flex items-center justify-end space-x-2 py-4">
        <Button
          variant="outline"
          size="sm"
          onClick={() => setPage((p) => Math.max(0, p - 1))}
          disabled={page === 0 || isLoading}
        >
          <ChevronLeft className="h-4 w-4" />
          Previous
        </Button>
        <div className="text-sm font-medium">
            Page {page + 1} of {totalPages || 1}
        </div>
        <Button
          variant="outline"
          size="sm"
          onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
          disabled={page >= totalPages - 1 || isLoading}
        >
          Next
          <ChevronRight className="h-4 w-4" />
        </Button>
      </div>

      {/* Create Dialog (Placeholder) */}
      <Dialog open={isCreateOpen} onOpenChange={setIsCreateOpen}>
        <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
                <DialogTitle>Add New Book</DialogTitle>
                <DialogDescription>
                    Create a new book entry. Click save when you're done.
                </DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="title" className="text-right">Title</Label>
                    <Input id="title" className="col-span-3" />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                    <Label htmlFor="isbn" className="text-right">ISBN</Label>
                    <Input id="isbn" className="col-span-3" />
                </div>
                {/* More fields... */}
            </div>
            <DialogFooter>
                <Button type="submit">Save changes</Button>
            </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}
