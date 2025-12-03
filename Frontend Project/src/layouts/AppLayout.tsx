import { RoleBasedNavbar } from "@/components/navbar";

interface AppLayoutProps {
  children: React.ReactNode;
}

export default function AppLayout({ children }: AppLayoutProps) {
  return (
    <div className="app-container min-h-screen bg-background">
      <RoleBasedNavbar />
      <main className="container mx-auto px-4 py-8">
        {children}
      </main>
    </div>
  );
}

