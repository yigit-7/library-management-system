import { LoansView } from "@/components/loans/loans-view"
import { getCurrentUser } from "@/lib/auth/auth-utils"
import { cookies } from "next/headers"
import { Loan, LoanStatus } from "@/lib/loans/types"
import { AccessDenied } from "@/components/auth/access-denied"

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

async function getLoans(params: URLSearchParams, token: string): Promise<Loan[]> {
  try {
    const url = `${API_BASE_URL}/api/loans/my-loans?${params.toString()}`;
    const response = await fetch(url, {
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      cache: "no-store" // Ensure fresh data
    });

    if (!response.ok) {
      console.error(`Failed to fetch loans: ${response.status} ${response.statusText}`);
      return [];
    }

    const json = await response.json();
    // Handle VIA_DTO structure or flat structure
    const data = json.data;
    return data.content || [];
  } catch (error) {
    console.error("Error fetching loans:", error);
    return [];
  }
}

export default async function LoansPage() {
  const user = await getCurrentUser();
  
  if (!user) {
    return <AccessDenied description="You need to be signed in to view your loans." />;
  }

  const cookieStore = await cookies();
  const token = cookieStore.get("accessToken")?.value;

  if (!token) {
    return <AccessDenied description="Session expired. Please sign in again." />;
  }

  // Fetch All Loans (Single Request)
  // We fetch a large size to get most history.
  const allParams = new URLSearchParams({ size: "1000", sort: "loanDate,desc" });
  const allLoans = await getLoans(allParams, token);

  // Filter Active Loans (ACTIVE + OVERDUE)
  const activeLoans = allLoans.filter(loan => 
    loan.status === LoanStatus.ACTIVE || loan.status === LoanStatus.OVERDUE
  );
  
  // Filter History Loans (Everything else)
  const historyLoans = allLoans.filter(loan => 
    loan.status !== LoanStatus.ACTIVE && loan.status !== LoanStatus.OVERDUE
  );

  return (
    <div className="flex min-h-screen flex-col">
      
      <main className="flex-1 container mx-auto max-w-5xl py-8 px-4 md:px-8">
        <div className="flex flex-col space-y-8">
            
            <div>
                <h1 className="text-3xl font-bold tracking-tight">My Loans</h1>
                <p className="text-muted-foreground">
                    Manage your borrowed books and track due dates.
                </p>
            </div>

            <LoansView activeLoans={activeLoans} historyLoans={historyLoans} />

        </div>
      </main>
    </div>
  )
}
