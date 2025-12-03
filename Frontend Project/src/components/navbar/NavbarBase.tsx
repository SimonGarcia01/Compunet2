import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";
import { Menu, User, Bell, Sparkles } from "lucide-react";
import { cn } from "@/lib/utils";
import gymLogo from "@/assets/gym-logo-new.png";

export interface NavLink {
  label: string;
  to: string;
  icon?: React.ReactNode;
}

export interface UserMenuItem {
  label: string;
  to?: string;
  onClick?: () => void;
  variant?: "default" | "destructive";
}

interface NavbarBaseProps {
  logo?: string;
  links: NavLink[];
  userMenu: UserMenuItem[];
  userEmail?: string;
  userRole?: string;
  notifications?: boolean;
  onAIndyClick?: () => void;
}

export function NavbarBase({
  logo = "Gym Icesi",
  links,
  userMenu,
  userEmail,
  userRole,
  notifications = false,
  onAIndyClick,
}: NavbarBaseProps) {
  const [mobileOpen, setMobileOpen] = useState(false);
  const location = useLocation();

  const isActive = (path: string) => location.pathname === path;

  return (
    <nav className="sticky top-0 z-50 w-full border-b-4 border-foreground bg-gradient-to-r from-background via-background to-muted/20 backdrop-blur-lg shadow-xl">
      {/* Animated gradient line */}
      <div className="absolute bottom-0 left-0 right-0 h-1 bg-gradient-to-r from-primary via-foreground to-primary animate-pulse" />
      
      <div className="container mx-auto px-4">
        <div className="flex h-20 items-center justify-between">
          {/* Logo */}
          <Link 
            to="/app" 
            className="flex items-center gap-3 group relative"
          >
            <div className="relative">
              {/* Glow effect */}
              <div className="absolute inset-0 bg-primary/20 rounded-xl blur-xl group-hover:bg-primary/40 transition-all duration-300" />
              
              {/* Logo container */}
              <div className="relative w-14 h-14 bg-gradient-to-br from-foreground to-foreground/80 rounded-xl flex items-center justify-center p-2 shadow-2xl border-2 border-foreground group-hover:scale-110 group-hover:rotate-3 transition-all duration-300">
                <img 
                  src={gymLogo} 
                  alt="Gym Logo" 
                  className="w-full h-full object-contain drop-shadow-2xl" 
                />
              </div>
            </div>
            
            <div className="hidden sm:block">
              <span className="text-2xl font-black tracking-tight text-foreground group-hover:text-primary transition-all duration-300">
                {logo}
              </span>
              <div className="h-1 w-0 group-hover:w-full bg-primary transition-all duration-300 rounded-full" />
            </div>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center gap-2">
            {links.map((link) => (
              <Link
                key={link.to}
                to={link.to}
                className={cn(
                  "relative px-4 py-2 text-sm font-bold tracking-wide transition-all duration-300 rounded-lg group",
                  isActive(link.to)
                    ? "text-background bg-foreground shadow-lg"
                    : "text-foreground hover:text-primary hover:bg-muted"
                )}
              >
                {link.label}
                
                {/* Underline animation for non-active links */}
                {!isActive(link.to) && (
                  <span className="absolute bottom-0 left-0 w-0 h-1 bg-primary group-hover:w-full transition-all duration-300 rounded-full" />
                )}
              </Link>
            ))}
          </div>

          {/* Right Side Actions */}
          <div className="flex items-center gap-3">
            {/* AIndy Chat Button */}
            {onAIndyClick && (
              <Button 
                variant="ghost" 
                size="icon" 
                onClick={onAIndyClick}
                className="relative hover:bg-primary/10 transition-all duration-300 group"
                title="Abrir AIndy Chat"
              >
                <Sparkles className="h-5 w-5 group-hover:scale-110 group-hover:rotate-12 transition-all duration-300 text-primary" />
                <span className="absolute top-1.5 right-1.5 w-2.5 h-2.5 bg-primary rounded-full animate-pulse shadow-lg shadow-primary/50" />
              </Button>
            )}

            {/* Notifications */}
            {notifications && (
              <Button 
                variant="ghost" 
                size="icon" 
                className="relative hover:bg-primary/10 transition-all duration-300 group"
              >
                <Bell className="h-5 w-5 group-hover:scale-110 group-hover:rotate-12 transition-all duration-300" />
                <span className="absolute top-1.5 right-1.5 w-2.5 h-2.5 bg-primary rounded-full animate-pulse shadow-lg shadow-primary/50" />
              </Button>
            )}

            {/* User Menu - Desktop */}
            <div className="hidden md:block">
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button 
                    variant="ghost" 
                    size="icon"
                    className="relative w-10 h-10 rounded-full bg-gradient-to-br from-primary/20 to-primary/10 hover:from-primary/30 hover:to-primary/20 transition-all duration-300 group border-2 border-foreground/10 hover:border-primary/30 shadow-md hover:shadow-lg hover:scale-110"
                    title={userEmail}
                  >
                    <User className="h-5 w-5 text-foreground group-hover:text-primary transition-colors duration-300" />
                    {/* Subtle pulse effect */}
                    <span className="absolute inset-0 rounded-full bg-primary/20 opacity-0 group-hover:opacity-100 animate-pulse transition-opacity duration-300" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className="w-56 bg-background border-2">
                  <DropdownMenuLabel>
                    <div className="flex flex-col space-y-1">
                      <p className="text-sm font-semibold">{userEmail}</p>
                      {userRole && (
                        <p className="text-xs text-muted-foreground font-normal">
                          Rol: {userRole}
                        </p>
                      )}
                    </div>
                  </DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  {userMenu.map((item, idx) => (
                    <DropdownMenuItem
                      key={idx}
                      asChild={!!item.to}
                      onClick={item.onClick}
                      className={cn(
                        "cursor-pointer font-medium",
                        item.variant === "destructive" && "text-destructive focus:text-destructive"
                      )}
                    >
                      {item.to ? (
                        <Link to={item.to}>{item.label}</Link>
                      ) : (
                        <span>{item.label}</span>
                      )}
                    </DropdownMenuItem>
                  ))}
                </DropdownMenuContent>
              </DropdownMenu>
            </div>

            {/* Mobile Menu */}
            <Sheet open={mobileOpen} onOpenChange={setMobileOpen}>
              <SheetTrigger asChild className="md:hidden">
                <Button 
                  variant="outline" 
                  size="icon"
                  className="border-2 hover:bg-foreground hover:text-background transition-all duration-300 shadow-lg"
                >
                  <Menu className="h-5 w-5" />
                </Button>
              </SheetTrigger>
              <SheetContent side="right" className="w-[300px] sm:w-[400px] bg-gradient-to-b from-background to-muted/20 border-l-4 border-foreground">
                <div className="flex flex-col gap-6 mt-8">
                  {/* User Info */}
                  <div className="pb-4 border-b-4 border-foreground/10">
                    <div className="flex items-center gap-3 mb-2">
                      <div className="w-12 h-12 rounded-full bg-primary/10 flex items-center justify-center border-2 border-foreground">
                        <User className="h-6 w-6" />
                      </div>
                      <div>
                        <p className="text-sm font-bold tracking-wide">{userEmail?.split("@")[0]}</p>
                        {userRole && (
                          <p className="text-xs font-semibold text-primary mt-0.5">
                            {userRole}
                          </p>
                        )}
                      </div>
                    </div>
                  </div>

                  {/* Navigation Links */}
                  <div className="flex flex-col gap-2">
                    {links.map((link) => (
                      <Link
                        key={link.to}
                        to={link.to}
                        onClick={() => setMobileOpen(false)}
                        className={cn(
                          "px-4 py-3 rounded-lg text-sm font-bold tracking-wide transition-all duration-300 border-2",
                          isActive(link.to)
                            ? "bg-foreground text-background border-foreground shadow-lg"
                            : "border-transparent hover:bg-muted hover:border-foreground/20"
                        )}
                      >
                        {link.label}
                      </Link>
                    ))}
                  </div>

                  {/* User Menu Items */}
                  <div className="pt-4 border-t-4 border-foreground/10 flex flex-col gap-2">
                    {userMenu.map((item, idx) => (
                      <div key={idx}>
                        {item.to ? (
                          <Link
                            to={item.to}
                            onClick={() => setMobileOpen(false)}
                            className="block px-4 py-3 rounded-lg text-sm font-bold tracking-wide hover:bg-muted transition-all duration-300 border-2 border-transparent hover:border-foreground/20"
                          >
                            {item.label}
                          </Link>
                        ) : (
                          <button
                            onClick={() => {
                              item.onClick?.();
                              setMobileOpen(false);
                            }}
                            className={cn(
                              "w-full text-left px-4 py-3 rounded-lg text-sm font-bold tracking-wide transition-all duration-300 border-2",
                              item.variant === "destructive"
                                ? "text-destructive hover:bg-destructive/10 border-transparent hover:border-destructive/20"
                                : "border-transparent hover:bg-muted hover:border-foreground/20"
                            )}
                          >
                            {item.label}
                          </button>
                        )}
                      </div>
                    ))}
                  </div>
                </div>
              </SheetContent>
            </Sheet>
          </div>
        </div>
      </div>
    </nav>
  );
}
