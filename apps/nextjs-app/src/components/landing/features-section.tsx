import {
  BookOpen,
  History,
  CreditCard,
  Library,
  ShieldCheck,
  Search,
} from "lucide-react"
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card"

export function FeaturesSection() {
  return (
    <section className="container mx-auto px-4 md:px-6 py-12 md:py-24 lg:py-32 bg-muted/50 rounded-3xl my-8">
      <div className="mx-auto flex max-w-[58rem] flex-col items-center justify-center gap-4 text-center mb-12">
        <h2 className="font-heading text-3xl leading-[1.1] sm:text-3xl md:text-6xl font-bold tracking-tight">
          Everything you need
        </h2>
        <p className="max-w-[85%] leading-normal text-muted-foreground sm:text-lg sm:leading-7">
          Explore the features that make our library management system powerful and user-friendly.
        </p>
      </div>
      <div className="mx-auto grid justify-center gap-4 sm:grid-cols-2 md:max-w-[64rem] md:grid-cols-3 lg:gap-8">
        {/* Feature 1 */}
        <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <BookOpen className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>Discovery & Catalog</CardTitle>
                <CardDescription>Find your next great read.</CardDescription>
            </CardHeader>
            <CardContent>
                <ul className="text-sm text-muted-foreground space-y-2">
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Smart Search by Title, ISBN, or Author
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Filter by Genre, Price & Availability
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        View comprehensive book details
                    </li>
                </ul>
            </CardContent>
        </Card>

        {/* Feature 2 */}
        <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <History className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>My Loans</CardTitle>
                <CardDescription>Keep track of what you're reading.</CardDescription>
            </CardHeader>
            <CardContent>
                <ul className="text-sm text-muted-foreground space-y-2">
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Personal dashboard for borrowing history
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Track Due Dates & Return Status
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Get alerts for overdue items
                    </li>
                </ul>
            </CardContent>
        </Card>

        {/* Feature 3 */}
        <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <CreditCard className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>Fines & Payments</CardTitle>
                <CardDescription>Manage your account health.</CardDescription>
            </CardHeader>
            <CardContent>
                <ul className="text-sm text-muted-foreground space-y-2">
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        View complete fine history
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        See detailed reasons & amounts
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Track Paid vs Unpaid status
                    </li>
                </ul>
            </CardContent>
        </Card>

        {/* Feature 4 */}
        <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <Library className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>Inventory Status</CardTitle>
                <CardDescription>Check if it's on the shelf.</CardDescription>
            </CardHeader>
            <CardContent>
                <ul className="text-sm text-muted-foreground space-y-2">
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Real-time physical copy status
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Barcode lookup for specific copies
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Check specific edition availability
                    </li>
                </ul>
            </CardContent>
        </Card>

        {/* Feature 5 */}
        <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <ShieldCheck className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>Security & Privacy</CardTitle>
                <CardDescription>Your data is safe.</CardDescription>
            </CardHeader>
            <CardContent>
                <ul className="text-sm text-muted-foreground space-y-2">
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Secure access to personal records
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Role-based access control
                    </li>
                    <li className="flex items-start">
                        <span className="mr-2">•</span>
                        Strict privacy protection
                    </li>
                </ul>
            </CardContent>
        </Card>
        
         {/* Feature 6 - Smart Search Highlight */}
         <Card className="flex flex-col justify-between border-none shadow-md hover:shadow-lg transition-shadow">
            <CardHeader>
                <div className="p-2 w-fit rounded-lg bg-primary/10 mb-4">
                    <Search className="h-6 w-6 text-primary" />
                </div>
                <CardTitle>Smart Search</CardTitle>
                <CardDescription>Instant results.</CardDescription>
            </CardHeader>
            <CardContent>
                <p className="text-sm text-muted-foreground leading-relaxed">
                    Our powerful search engine helps you find exactly what you're looking for in milliseconds. Just type and discover.
                </p>
            </CardContent>
        </Card>
      </div>
    </section>
  )
}
