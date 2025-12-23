import { Metadata } from "next"
import { SiteHeader } from "@/components/site-header"

export const metadata: Metadata = {
  title: "Terms of Service - Library System",
  description: "Read our terms of service.",
}

export default function TermsPage() {
  return (
    <div className="flex min-h-screen flex-col">
      <SiteHeader />
      <main className="flex-1 container max-w-3xl mx-auto py-12 md:py-24">
        <div className="space-y-8">
          <div className="space-y-2">
            <h1 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">
              Terms of Service
            </h1>
            <p className="text-muted-foreground">
              Last updated: December 22, 2025
            </p>
          </div>
          
          <div className="space-y-6 text-base leading-7 text-muted-foreground">
            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">1. Acceptance of Terms</h2>
              <p>
                By accessing and using the Library Management System ("the Service"), you accept and agree to be bound by the terms and provision of this agreement. In addition, when using these particular services, you shall be subject to any posted guidelines or rules applicable to such services.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">2. Description of Service</h2>
              <p>
                The Library Management System provides users with access to a collection of resources, including books, journals, and digital media. You are responsible for obtaining access to the Service and that access may involve third party fees (such as Internet service provider or airtime charges).
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">3. User Conduct</h2>
              <p>
                You agree to use the Service only for lawful purposes. You are prohibited from posting on or transmitting through the Service any material that is unlawful, harmful, threatening, abusive, harassing, defamatory, vulgar, obscene, sexually explicit, profane, hateful, racially, ethnically, or otherwise objectionable.
              </p>
              <ul className="list-disc pl-6 mt-2 space-y-1">
                <li>You must not damage library property.</li>
                <li>You must return borrowed items by the due date.</li>
                <li>You are responsible for any fines incurred due to late returns or damage.</li>
              </ul>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">4. Membership and Accounts</h2>
              <p>
                To access certain features of the Service, you may be required to register for an account. You agree to provide accurate and complete information when creating your account and to keep this information up to date. You are solely responsible for the activity that occurs on your account, and you must keep your account password secure.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">5. Termination</h2>
              <p>
                We may terminate or suspend access to our Service immediately, without prior notice or liability, for any reason whatsoever, including without limitation if you breach the Terms.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">6. Changes to Terms</h2>
              <p>
                We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a revision is material we will try to provide at least 30 days' notice prior to any new terms taking effect.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">7. Contact Us</h2>
              <p>
                If you have any questions about these Terms, please contact us at support@library.com.
              </p>
            </section>
          </div>
        </div>
      </main>
      <footer className="border-t py-6 md:py-0">
        <div className="container flex flex-col items-center justify-between gap-4 md:h-24 md:flex-row mx-auto">
          <p className="text-center text-sm leading-loose text-muted-foreground md:text-left">
            © 2025 Library System. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  )
}
