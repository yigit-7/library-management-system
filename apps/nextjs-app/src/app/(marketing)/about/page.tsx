import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { BookOpen, Users, Globe, Award, LucideIcon } from "lucide-react"

export default function AboutPage() {
  return (
    <div className="flex min-h-screen flex-col">
      
      <main className="flex-1 container mx-auto max-w-5xl py-12 px-4 md:px-8">
        <div className="space-y-12">
            
            {/* Header */}
            <div className="text-center space-y-4">
                <h1 className="text-4xl font-bold tracking-tight">About Us</h1>
                <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
                    We are dedicated to providing access to knowledge and fostering a community of lifelong learners.
                </p>
            </div>

            {/* Mission */}
            <div className="grid md:grid-cols-2 gap-12 items-center">
                <div className="space-y-4">
                    <h2 className="text-3xl font-bold">Our Mission</h2>
                    <p className="text-lg text-muted-foreground leading-relaxed">
                        Our mission is to empower individuals through free and open access to information, ideas, and culture. We believe that libraries are essential for a democratic society and that everyone deserves the opportunity to learn and grow.
                    </p>
                </div>
                <div className="bg-muted rounded-xl aspect-video flex items-center justify-center">
                    <BookOpen className="h-24 w-24 text-muted-foreground/20" />
                </div>
            </div>

            {/* Stats */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
                <StatCard icon={BookOpen} value="50k+" label="Books" />
                <StatCard icon={Users} value="10k+" label="Members" />
                <StatCard icon={Globe} value="24/7" label="Online Access" />
                <StatCard icon={Award} value="100+" label="Years of Service" />
            </div>

            {/* Team */}
            <div className="space-y-8">
                <h2 className="text-3xl font-bold text-center">Our Team</h2>
                <div className="grid md:grid-cols-3 gap-8">
                    <TeamCard name="Jane Doe" role="Head Librarian" />
                    <TeamCard name="John Smith" role="Archivist" />
                    <TeamCard name="Emily Brown" role="Community Manager" />
                </div>
            </div>

        </div>
      </main>
    </div>
  )
}

interface StatCardProps {
    icon: LucideIcon
    value: string
    label: string
}

function StatCard({ icon: Icon, value, label }: StatCardProps) {
    return (
        <Card className="text-center p-6">
            <CardContent className="pt-6 space-y-2">
                <Icon className="h-8 w-8 mx-auto text-primary" />
                <div className="text-3xl font-bold">{value}</div>
                <div className="text-sm text-muted-foreground">{label}</div>
            </CardContent>
        </Card>
    )
}

interface TeamCardProps {
    name: string
    role: string
}

function TeamCard({ name, role }: TeamCardProps) {
    return (
        <Card className="text-center">
            <CardHeader>
                <div className="w-24 h-24 bg-muted rounded-full mx-auto mb-4" />
                <CardTitle>{name}</CardTitle>
                <p className="text-sm text-muted-foreground">{role}</p>
            </CardHeader>
        </Card>
    )
}
