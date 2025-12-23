import { Metadata } from "next"
import { SiteHeader } from "@/components/site-header"
import { Separator } from "@/components/ui/separator"

export const metadata: Metadata = {
  title: "About Us - Library System",
  description: "Learn more about our library and mission.",
}

export default function AboutPage() {
  return (
    <div className="flex min-h-screen flex-col">
      <SiteHeader />
      <main className="flex-1 container mx-auto max-w-7xl py-12 px-4 md:px-8">
        <div className="max-w-3xl mx-auto space-y-8">
          <div className="space-y-4 text-center">
            <h1 className="text-4xl font-bold tracking-tighter sm:text-5xl">About Our Library</h1>
            <p className="text-xl text-muted-foreground">
              Empowering minds, connecting communities, and preserving knowledge for future generations.
            </p>
          </div>
          
          <Separator />

          <div className="space-y-8 text-lg leading-relaxed text-muted-foreground">
            <section>
              <h2 className="text-2xl font-semibold text-foreground mb-4">Our Mission</h2>
              <p>
                Our mission is to provide free and open access to a vast collection of information, resources, and services that support the educational, cultural, and recreational needs of our diverse community. We believe that libraries are essential for a democratic society and that knowledge is a fundamental human right.
              </p>
            </section>

            <section>
              <h2 className="text-2xl font-semibold text-foreground mb-4">Our History</h2>
              <p>
                Founded in 1985, the Library System started as a small community reading room. Over the decades, we have grown into a modern network of libraries, serving thousands of members with a collection of over 100,000 books, digital media, and historical archives.
              </p>
            </section>

            <section>
              <h2 className="text-2xl font-semibold text-foreground mb-4">What We Offer</h2>
              <ul className="list-disc pl-6 space-y-2">
                <li>Extensive collection of fiction and non-fiction books.</li>
                <li>Digital resources including e-books, audiobooks, and research databases.</li>
                <li>Quiet study areas and collaborative workspaces.</li>
                <li>Community events, workshops, and author talks.</li>
                <li>Free Wi-Fi and computer access.</li>
              </ul>
            </section>
          </div>
        </div>
      </main>
      <footer className="py-6 md:px-8 md:py-0 border-t mt-auto">
        <div className="container mx-auto max-w-7xl flex flex-col items-center justify-between gap-4 md:h-24 md:flex-row px-4 md:px-8">
          <p className="text-center text-sm leading-loose text-muted-foreground md:text-left">
            © 2025 Library System. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  )
}
