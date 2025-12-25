import { Metadata } from "next"

export const metadata: Metadata = {
  title: "Privacy Policy - Library System",
  description: "Read our privacy policy.",
}

export default function PrivacyPage() {
  return (
    <div className="flex min-h-screen flex-col">
      <main className="flex-1 container max-w-3xl mx-auto py-12 md:py-24">
        <div className="space-y-8">
          <div className="space-y-2">
            <h1 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">
              Privacy Policy
            </h1>
            <p className="text-muted-foreground">
              Last updated: December 22, 2025
            </p>
          </div>
          
          <div className="space-y-6 text-base leading-7 text-muted-foreground">
            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">1. Information We Collect</h2>
              <p>
                We collect information you provide directly to us, such as when you create an account, update your profile, request customer support, or otherwise communicate with us.
              </p>
              <ul className="list-disc pl-6 mt-2 space-y-1">
                <li>Name and contact information (email address).</li>
                <li>Account credentials (username and password).</li>
                <li>Borrowing history and reading preferences.</li>
              </ul>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">2. How We Use Your Information</h2>
              <p>
                We use the information we collect to provide, maintain, and improve our services, such as to:
              </p>
              <ul className="list-disc pl-6 mt-2 space-y-1">
                <li>Process your loans and returns.</li>
                <li>Send you technical notices, updates, security alerts, and support messages.</li>
                <li>Respond to your comments, questions, and requests.</li>
                <li>Monitor and analyze trends, usage, and activities in connection with our Service.</li>
              </ul>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">3. Sharing of Information</h2>
              <p>
                We do not share your personal information with third parties except as described in this privacy policy. We may share your information with:
              </p>
              <ul className="list-disc pl-6 mt-2 space-y-1">
                <li>Service providers who need access to such information to carry out work on our behalf.</li>
                <li>In response to a request for information if we believe disclosure is in accordance with any applicable law, regulation, or legal process.</li>
              </ul>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">4. Data Security</h2>
              <p>
                We take reasonable measures to help protect information about you from loss, theft, misuse and unauthorized access, disclosure, alteration and destruction. However, no internet or email transmission is ever fully secure or error free.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">5. Your Choices</h2>
              <p>
                You may update, correct, or delete information about you at any time by logging into your online account or emailing us. If you wish to delete or deactivate your account, please email us at support@library.com, but note that we may retain certain information as required by law or for legitimate business purposes.
              </p>
            </section>

            <section>
              <h2 className="text-xl font-semibold text-foreground mb-2">6. Contact Us</h2>
              <p>
                If you have any questions about this Privacy Policy, please contact us at privacy@library.com.
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
