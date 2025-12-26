import { Suspense } from "react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Skeleton } from "@/components/ui/skeleton"
import { 
  BookOpen, 
  Library,
  Filter
} from "lucide-react"
import Link from "next/link"
import Image from "next/image"
import { BookFilters } from "@/components/books/book-filters"
import { BookSort } from "@/components/books/book-sort"
import { BookSearch } from "@/components/books/book-search"
import { BookPagination } from "@/components/books/book-pagination"
import { BookViewOptions } from "@/components/books/book-view-options"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { bookService } from "@/lib/books/service"
import { Book } from "@/lib/books/types"
import { cn } from "@/lib/utils"

export const dynamic = 'force-dynamic'

interface CatalogPageProps {
  searchParams: Promise<{ [key: string]: string | string[] | undefined }>
}

export default async function CatalogPage(props: CatalogPageProps) {
  const searchParams = await props.searchParams;

  const search = typeof searchParams.search === 'string' ? searchParams.search : undefined

  let categoryIds: number[] | undefined = undefined
  if (searchParams.categoryIds) {
    const rawIds = Array.isArray(searchParams.categoryIds) 
      ? searchParams.categoryIds 
      : [searchParams.categoryIds]
    categoryIds = rawIds.map(Number).filter(n => !isNaN(n))
  }

  const available = searchParams.available === 'true'
  const sort = typeof searchParams.sort === 'string' ? searchParams.sort : "title,asc"

  const pageParam = Number(searchParams.page)
  const page = pageParam > 0 ? pageParam - 1 : 0

  const sizeParam = Number(searchParams.size)
  const size = [12, 24, 48].includes(sizeParam) ? sizeParam : 12
  
  const colsParam = searchParams.cols
  const cols = colsParam === '2' ? 2 : colsParam === '3' ? 3 : 4

  let books: Book[] = []
  let totalPages = 0
  let totalElements = 0
  let error = null

  try {
    const response = await bookService.getBooks({
      search,
      categoryIds,
      available: available ? true : undefined,
      page,
      size,
      sort
    })
    books = response.content || []
    totalPages = response.page?.totalPages || 0
    totalElements = response.page?.totalElements || 0
  } catch (e) {
    console.error("Failed to fetch books:", e)
    error = e
  }

  const gridClass = cn(
    "grid gap-6",
    cols === 2 && "grid-cols-1 sm:grid-cols-2",
    cols === 3 && "grid-cols-1 sm:grid-cols-2 lg:grid-cols-3",
    cols === 4 && "grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4"
  )

  return (
    <div className="flex min-h-screen flex-col">
      <main className="flex-1 container mx-auto max-w-7xl py-8 px-4 md:px-8">
        
        <div className="flex flex-col space-y-6">
            <div className="flex flex-col lg:flex-row gap-8">
                
                {/* Sidebar Filters (Desktop) */}
                <aside className="hidden lg:block w-64 shrink-0">
                    <Suspense fallback={<FiltersSkeleton />}>
                        <BookFilters />
                    </Suspense>
                </aside>

                {/* Main Content */}
                <div className="flex-1 space-y-6">
                    
                    {/* Toolbar */}
                    <div className="flex flex-col xl:flex-row gap-4 items-start xl:items-center justify-between bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60 sticky top-14 z-10 py-2 -my-2">
                        <Suspense fallback={<Skeleton className="h-10 w-full sm:max-w-sm" />}>
                            <BookSearch key={search} />
                        </Suspense>
                        
                        <div className="flex flex-wrap items-center gap-2 w-full xl:w-auto">
                            <Sheet>
                                <SheetTrigger asChild>
                                    <Button variant="outline" size="sm" className="lg:hidden h-9">
                                        <Filter className="mr-2 h-4 w-4" /> Filters
                                    </Button>
                                </SheetTrigger>
                                <SheetContent side="left">
                                    <div className="py-4">
                                        <Suspense fallback={<FiltersSkeleton />}>
                                            <BookFilters />
                                        </Suspense>
                                    </div>
                                </SheetContent>
                            </Sheet>
                            
                            <Suspense fallback={<Skeleton className="h-9 w-[180px]" />}>
                                <BookSort />
                            </Suspense>

                            <div className="hidden sm:block w-px h-6 bg-border mx-1" />

                            <Suspense fallback={<Skeleton className="h-9 w-[150px]" />}>
                                <BookViewOptions />
                            </Suspense>
                        </div>
                    </div>

                    {/* Results Count Indicator */}
                    <div className="text-sm text-muted-foreground">
                        {search ? (
                            <>
                                Found <span className="font-medium text-foreground">{totalElements}</span> results for <span className="font-medium text-foreground">&quot;{search}&quot;</span>
                            </>
                        ) : (
                            <>
                                Showing <span className="font-medium text-foreground">{totalElements}</span> books
                            </>
                        )}
                    </div>

                    {/* Books Grid */}
                    {error ? (
                        <div className="flex flex-col items-center justify-center py-20 text-center border rounded-lg bg-destructive/10 border-destructive/20">
                            <h3 className="text-lg font-semibold text-destructive">Error loading books</h3>
                            <p className="text-muted-foreground">Please try again later.</p>
                        </div>
                    ) : books.length > 0 ? (
                        <div className={gridClass}>
                            {books.map((book) => (
                                <BookCard key={book.id} book={book} />
                            ))}
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center py-20 text-center border rounded-lg bg-muted/10 border-dashed">
                            <Library className="h-16 w-16 text-muted-foreground/20 mb-4" />
                            <h3 className="text-lg font-semibold">No books found</h3>
                            <p className="text-muted-foreground">Try adjusting your search or filters.</p>
                            <Button variant="link" asChild className="mt-2">
                                <Link href="/books">Clear all filters</Link>
                            </Button>
                        </div>
                    )}

                    {/* Pagination */}
                    <Suspense fallback={<Skeleton className="h-10 w-full mt-8" />}>
                        <BookPagination totalPages={totalPages} />
                    </Suspense>
                </div>
            </div>
        </div>
      </main>
    </div>
  )
}

// --- Sub-Components ---

function BookCard({ book }: { book: Book }) {
  const isAvailable = (book.availableCopies || 0) > 0

  return (
    <Card className="flex flex-col h-full overflow-hidden hover:shadow-md transition-shadow group">
      <Link href={`/books/${book.id}`} className="relative aspect-[2/3] w-full bg-muted overflow-hidden block cursor-pointer">
        {book.coverImageUrl ? (
          <Image
            src={book.coverImageUrl}
            alt={book.title || "Book Cover"}
            fill
            className="object-cover group-hover:scale-105 transition-transform duration-300"
            sizes="(max-width: 768px) 100vw, (max-width: 1200px) 50vw, 33vw"
          />
        ) : (
          <div className="flex h-full w-full items-center justify-center">
            <BookOpen className="h-12 w-12 text-muted-foreground/20" />
          </div>
        )}
        
        {/* Status Badge */}
        <div className="absolute top-2 right-2">
            {isAvailable ? (
                <Badge variant="secondary" className="bg-green-500/90 text-white hover:bg-green-600/90 backdrop-blur-sm shadow-sm">
                    In Stock
                </Badge>
            ) : (
                <Badge variant="destructive" className="opacity-90 backdrop-blur-sm shadow-sm">
                    Out of Stock
                </Badge>
            )}
        </div>
      </Link>
      
      <CardHeader className="p-4 pb-2">
        <div className="space-y-1">
            <CardTitle className="text-base line-clamp-1" title={book.title}>
                <Link href={`/books/${book.id}`} className="hover:underline">
                    {book.title}
                </Link>
            </CardTitle>
            <p className="text-sm text-muted-foreground line-clamp-1">
                {book.authors && book.authors.length > 0 
                    ? book.authors.map(a => a.name).join(", ") 
                    : "Unknown Author"}
            </p>
        </div>
      </CardHeader>
      
      <CardContent className="p-4 pt-0 flex-1">
        <div className="flex items-center gap-2 mt-2">
            <Badge variant="outline" className="text-xs font-normal">
                {book.category?.name || "General"}
            </Badge>
            {book.publishedDate && (
                <span className="text-xs text-muted-foreground">
                    {new Date(book.publishedDate).getFullYear()}
                </span>
            )}
        </div>
      </CardContent>
      
      <CardFooter className="p-4 pt-0 pb-4">
        <Button asChild className="w-full" variant="secondary">
            <Link href={`/books/${book.id}`}>View Details</Link>
        </Button>
      </CardFooter>
    </Card>
  )
}

function FiltersSkeleton() {
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between h-8">
                <Skeleton className="h-6 w-16" />
            </div>
            <div className="h-px bg-muted" />
            <div className="space-y-3">
                <Skeleton className="h-5 w-24" />
                <div className="space-y-2">
                    <Skeleton className="h-4 w-32" />
                </div>
            </div>
            <div className="h-px bg-muted" />
            <div className="space-y-3">
                <Skeleton className="h-5 w-24" />
                <div className="space-y-2">
                    {Array.from({ length: 5 }).map((_, i) => (
                        <Skeleton key={i} className="h-4 w-full" />
                    ))}
                </div>
            </div>
        </div>
    )
}
