import { useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { loginThunk, type RootState } from "@/store/auth/thunk";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { toast } from "@/hooks/use-toast";
import { Link as LinkIcon, Lock, Mail } from "lucide-react";
import { Link, useNavigate, Navigate } from "react-router-dom";
import gymLogo from "@/assets/gym-logo-new.png";

const ICE_DOMAIN = /^[A-Za-z0-9._%+-]+@u\.icesi\.edu\.co$/;

export default function Login() {
  const dispatch = useDispatch<any>();
  const navigate = useNavigate();
  const { token, loading, error } = useSelector((s: RootState) => s.auth);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [touchedUser, setTouchedUser] = useState(false);

  const normalizedUser = useMemo(() => username.trim().toLowerCase(), [username]);
  const isInstitutional = useMemo(() => ICE_DOMAIN.test(normalizedUser), [normalizedUser]);

  const showUserError = touchedUser && username.length > 0 && !isInstitutional;
  const canSubmit = isInstitutional && password.length > 0;

  if (token) return <Navigate to="/app" replace />;

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!isInstitutional) {
      toast({
        title: "Correo no válido",
        description: "Debes usar tu correo institucional @u.icesi.edu.co",
        variant: "destructive",
      });
      return;
    }
    const ok = await dispatch(loginThunk({ username: normalizedUser, password }));
    if (ok) {
      toast({ title: "Bienvenido 👋", description: "Autenticación exitosa." });
      navigate("/app");
    } else {
      toast({
        title: "Error de autenticación",
        description: error ?? "Revisa tus credenciales.",
        variant: "destructive",
      });
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background relative overflow-hidden">
      {/* Animated Background Grid */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#80808012_1px,transparent_1px),linear-gradient(to_bottom,#80808012_1px,transparent_1px)] bg-[size:24px_24px]" />
      
      {/* Floating Orbs */}
      <div className="absolute top-20 left-20 w-72 h-72 bg-primary/5 rounded-full blur-3xl animate-float" />
      <div className="absolute bottom-20 right-20 w-96 h-96 bg-primary/5 rounded-full blur-3xl animate-float" style={{ animationDelay: '2s' }} />
      
      {/* Spotlight Effect */}
      <div className="absolute top-0 left-1/2 -translate-x-1/2 w-[800px] h-[600px] bg-gradient-to-b from-primary/10 via-transparent to-transparent blur-3xl" />

      <Card className="w-full max-w-md border-2 relative z-10 animate-fade-in-up shadow-2xl backdrop-blur-sm bg-card/95">
        <CardHeader className="space-y-4 text-center pb-8">
          <div className="mx-auto w-24 h-24 bg-white rounded-2xl flex items-center justify-center animate-scale-in p-4 shadow-xl border-4 border-foreground hover:scale-110 transition-transform duration-300">
            <img src={gymLogo} alt="Gym Logo" className="w-full h-full object-contain" />
          </div>
          <div>
            <CardTitle className="text-3xl font-black mb-2">INICIAR SESIÓN</CardTitle>
            <p className="text-sm text-muted-foreground">
              Ingresa con tu cuenta institucional
            </p>
          </div>
        </CardHeader>

        <CardContent className="space-y-6">
          <form onSubmit={onSubmit} className="space-y-5">
            {/* Email Field */}
            <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: '0.1s' }}>
              <Label htmlFor="username" className="text-sm font-bold flex items-center gap-2">
                <Mail className="w-4 h-4" />
                Correo institucional
              </Label>
              <div className="relative group">
                <Input
                  id="username"
                  type="email"
                  placeholder="tuusuario@u.icesi.edu.co"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  onBlur={() => setTouchedUser(true)}
                  aria-invalid={showUserError ? "true" : "false"}
                  aria-describedby="username-help"
                  className={`h-12 transition-all duration-300 ${
                    showUserError 
                      ? "border-destructive focus-visible:ring-destructive" 
                      : "group-hover:border-primary/50"
                  }`}
                  required
                />
                <div className={`absolute inset-0 -z-10 rounded-md blur-md transition-opacity duration-300 ${
                  showUserError ? "opacity-0" : "opacity-0 group-hover:opacity-20 bg-primary"
                }`} />
              </div>
              <p
                id="username-help"
                className={`text-xs flex items-center gap-1 transition-colors duration-300 ${
                  showUserError ? "text-destructive font-medium" : "text-muted-foreground"
                }`}
              >
                {showUserError ? "⚠️ " : ""}
                Usa tu correo terminado en <code className="font-mono bg-muted px-1 py-0.5 rounded">@u.icesi.edu.co</code>
              </p>
            </div>

            {/* Password Field */}
            <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: '0.2s' }}>
              <Label htmlFor="password" className="text-sm font-bold flex items-center gap-2">
                <Lock className="w-4 h-4" />
                Contraseña
              </Label>
              <div className="relative group">
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="h-12 transition-all duration-300 group-hover:border-primary/50"
                  required
                />
                <div className="absolute inset-0 -z-10 rounded-md blur-md transition-opacity duration-300 opacity-0 group-hover:opacity-20 bg-primary" />
              </div>
            </div>

            {/* Submit Button */}
            <Button 
              type="submit" 
              className="w-full h-12 text-base font-bold animate-fade-in-up relative overflow-hidden group" 
              style={{ animationDelay: '0.3s' }}
            >
              <span className="relative z-10">
                {loading ? "ENTRAR" : "ENTRAR"}
              </span>
              <div className="absolute inset-0 bg-gradient-to-r from-primary/0 via-primary-foreground/10 to-primary/0 translate-x-[-100%] group-hover:translate-x-[100%] transition-transform duration-700" />
            </Button>
          </form>

          {/* Error Message */}
          {error && (
            <div className="p-4 bg-destructive/10 border border-destructive/20 rounded-md animate-fade-in">
              <p className="text-sm text-destructive font-medium text-center">{error}</p>
            </div>
          )}

          {/* Footer Links */}
          <div className="pt-6 border-t border-border/50 animate-fade-in-up" style={{ animationDelay: '0.4s' }}>
                <p className="text-sm text-center">
                    <span className="text-foreground/70">¿No tienes cuenta?{" "}</span>
                    <Link 
                      to="/register" 
                      className="text-primary font-bold hover:text-primary/80 hover:underline underline-offset-4 transition-all duration-200 inline-flex items-center gap-1"
                    >
                        <LinkIcon className="w-3 h-3" />
                        Crea una ahora
                    </Link>
                </p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
