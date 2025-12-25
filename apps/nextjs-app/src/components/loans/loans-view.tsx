"use client"

import { useState } from "react"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Card, CardContent } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Calendar, Clock, BookOpen, Library } from "lucide-react"
import { Loan, LoanStatus } from "@/lib/loans/types"
import Image from "next/image"
import Link from "next/link"
import { format, differenceInDays, parseISO } from "date-fns"

interface LoansViewProps {
  activeLoans: Loan[]
  historyLoans: Loan[]
}

export function LoansView({ activeLoans, historyLoans }: LoansViewProps) {
  const [activeTab, setActiveTab] = useState("active")

  return (
    <Tabs defaultValue="active" className="w-full" onValueChange={setActiveTab}>
        <TabsList className="grid w-full grid-cols-2 max-w-[400px]">
            <TabsTrigger value="active">Active Loans</TabsTrigger>
            <TabsTrigger value="history">Loan History</TabsTrigger>
        </TabsList>
        
        <TabsContent value="active" className="mt-6 space-y-6">
            {activeLoans.length > 0 ? (
                <div className="grid gap-6 md:grid-cols-2">
                    {activeLoans.map((loan) => (
                        <ActiveLoanCard key={loan.id} loan={loan} />
                    ))}
                </div>
            ) : (
                <EmptyState message="You don't have any active loans." />
            )}
        </TabsContent>
        
        <TabsContent value="history" className="mt-6">
            {historyLoans.length > 0 ? (
                <div className="rounded-md border">
                    <div className="p-4">
                        {historyLoans.map((loan) => (
                            <HistoryLoanItem key={loan.id} loan={loan} />
                        ))}
                    </div>
                </div>
            ) : (
                <EmptyState message="No loan history found." />
            )}
        </TabsContent>
    </Tabs>
  )
}

// --- Sub-Components ---

function ActiveLoanCard({ loan }: { loan: Loan }) {
    const dueDate = parseISO(loan.dueDate)
    const daysLeft = differenceInDays(dueDate, new Date())
    const isOverdue = loan.status === LoanStatus.OVERDUE || daysLeft < 0
    const isDueSoon = !isOverdue && daysLeft >= 0 && daysLeft <= 3

    return (
        <Card className={`overflow-hidden border-l-4 ${isOverdue ? 'border-l-destructive' : isDueSoon ? 'border-l-orange-500' : 'border-l-green-500'}`}>
            <div className="flex h-full">
                {/* Book Cover */}
                <div className="relative w-24 sm:w-32 bg-muted shrink-0">
                    {loan.bookCoverUrl ? (
                        <Image 
                            src={loan.bookCoverUrl} 
                            alt={loan.bookTitle} 
                            fill 
                            className="object-cover"
                        />
                    ) : (
                        <div className="flex h-full w-full items-center justify-center">
                            <BookOpen className="h-8 w-8 text-muted-foreground/30" />
                        </div>
                    )}
                </div>

                {/* Content */}
                <CardContent className="flex-1 p-4 sm:p-6 flex flex-col justify-between">
                    <div>
                        <div className="flex justify-between items-start gap-2">
                            <h3 className="font-semibold text-lg line-clamp-1" title={loan.bookTitle}>
                                {loan.bookTitle}
                            </h3>
                            {isOverdue && <Badge variant="destructive" className="shrink-0">Overdue</Badge>}
                            {isDueSoon && <Badge variant="secondary" className="bg-orange-100 text-orange-700 hover:bg-orange-100 shrink-0">Due Soon</Badge>}
                        </div>
                        
                        <div className="flex items-center gap-2 text-sm mt-4">
                            <Calendar className="h-4 w-4 text-muted-foreground" />
                            <span className="text-muted-foreground">Due:</span>
                            <span className={`font-medium ${isOverdue ? 'text-destructive' : ''}`}>
                                {format(dueDate, "MMM d, yyyy")}
                            </span>
                        </div>
                        
                        <div className="flex items-center gap-2 text-sm mt-1">
                            <Clock className="h-4 w-4 text-muted-foreground" />
                            <span className="text-muted-foreground">
                                {isOverdue 
                                    ? `${Math.abs(daysLeft)} days late` 
                                    : `${daysLeft} days remaining`}
                            </span>
                        </div>
                    </div>
                </CardContent>
            </div>
        </Card>
    )
}

function HistoryLoanItem({ loan }: { loan: Loan }) {
    return (
        <div className="flex items-center justify-between py-4 border-b last:border-0">
            <div className="flex items-center gap-4">
                <div className="h-12 w-8 bg-muted relative rounded overflow-hidden shrink-0 hidden sm:block">
                     {loan.bookCoverUrl && (
                        <Image src={loan.bookCoverUrl} alt="" fill className="object-cover" />
                     )}
                </div>
                <div>
                    <p className="font-medium line-clamp-1">{loan.bookTitle}</p>
                    <p className="text-xs text-muted-foreground">
                        Returned on {loan.returnDate ? format(parseISO(loan.returnDate), "MMM d, yyyy") : "-"}
                    </p>
                </div>
            </div>
            <Badge variant="outline" className="bg-muted text-muted-foreground">
                {loan.status}
            </Badge>
        </div>
    )
}

function EmptyState({ message }: { message: string }) {
    return (
        <div className="flex flex-col items-center justify-center py-16 text-center border rounded-lg bg-muted/10 border-dashed">
            <Library className="h-12 w-12 text-muted-foreground/20 mb-4" />
            <h3 className="text-lg font-semibold">No loans</h3>
            <p className="text-muted-foreground">{message}</p>
            <Button variant="link" asChild className="mt-2">
                <Link href="/books">Browse Catalog</Link>
            </Button>
        </div>
    )
}
