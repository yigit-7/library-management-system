"use client"

import { useAuth } from "@/hooks/use-auth"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Book, Users, Library, AlertCircle } from "lucide-react"
import { ROLES } from "@/lib/constants"
import { useApiQuery } from "@/lib/api-client/api-hooks"
import { 
  BookControllerService, 
  LoanManagementControllerService, 
  UserManagementControllerService,
  LoanSearchRequest
} from "@/lib/api"
import { Skeleton } from "@/components/ui/skeleton"
import { PageResponse } from "@/lib/api-client/api-utils"

export default function DashboardPage() {
  const { user } = useAuth()
  const isAdmin = user?.roles?.includes(ROLES.ADMIN)

  // 1. Total Books Query
  const { data: booksData, isLoading: booksLoading } = useApiQuery(
    ['dashboard-books-count'],
    BookControllerService.getAllBooks,
    [{}, { page: 0, size: 1 }]
  )

  // 2. Active Loans Query
  const { data: activeLoansData, isLoading: activeLoansLoading } = useApiQuery(
    ['dashboard-active-loans-count'],
    LoanManagementControllerService.getAllLoans,
    [{ status: LoanSearchRequest.status.ACTIVE }, { page: 0, size: 1 }]
  )

  // 3. Overdue Loans Query
  const { data: overdueLoansData, isLoading: overdueLoansLoading } = useApiQuery(
    ['dashboard-overdue-loans-count'],
    LoanManagementControllerService.getAllLoans,
    [{ overdue: true }, { page: 0, size: 1 }]
  )

  // 4. Total Users Query (Only for Admin)
  const { data: usersData, isLoading: usersLoading } = useApiQuery(
    ['dashboard-users-count'],
    UserManagementControllerService.getAllUsers,
    [{ page: 0, size: 1 }],
    { enabled: isAdmin }
  )

  const renderStat = (loading: boolean, data: unknown, suffix?: string) => {
    if (loading) {
        return (
            <div className="space-y-1">
                <Skeleton className="h-8 w-16" />
                {suffix && <Skeleton className="h-3 w-24" />}
            </div>
        )
    }
    
    // Cast data to PageResponse to access the nested page object safely
    const pageData = data as PageResponse | undefined;
    const count = pageData?.page?.totalElements ?? 0;

    return (
      <>
        <div className="text-2xl font-bold">{count}</div>
        {suffix && <p className="text-xs text-muted-foreground">{suffix}</p>}
      </>
    )
  }

  return (
    <div className="flex flex-1 flex-col gap-4 p-4 pt-0">
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Total Books
            </CardTitle>
            <Book className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            {renderStat(booksLoading, booksData?.data, "In the library")}
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Active Loans
            </CardTitle>
            <Library className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            {renderStat(activeLoansLoading, activeLoansData?.data, "Currently borrowed")}
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">
              Overdue Books
            </CardTitle>
            <AlertCircle className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            {renderStat(overdueLoansLoading, overdueLoansData?.data, "Needs attention")}
          </CardContent>
        </Card>
        {isAdmin && (
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Total Users
              </CardTitle>
              <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              {renderStat(usersLoading, usersData?.data, "Registered members")}
            </CardContent>
          </Card>
        )}
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="col-span-4">
          <CardHeader>
            <CardTitle>Overview</CardTitle>
          </CardHeader>
          <CardContent className="pl-2">
            {/* Placeholder for Chart */}
            <div className="h-[200px] flex items-center justify-center text-muted-foreground bg-muted/20 rounded-md">
              Chart Component Placeholder
            </div>
          </CardContent>
        </Card>
        <Card className="col-span-3">
          <CardHeader>
            <CardTitle>Recent Activity</CardTitle>
            <p className="text-sm text-muted-foreground">
              Recent loan transactions.
            </p>
          </CardHeader>
          <CardContent>
            {/* Placeholder for Recent Activity */}
            <div className="space-y-8">
               <div className="text-sm text-muted-foreground text-center py-8">
                 No recent activity to display.
               </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
