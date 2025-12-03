import { useMemo, useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { toast } from "@/hooks/use-toast";
import { registerThunk } from "@/store/auth/register";
import { Lock, Mail, User as UserIcon, IdCard } from "lucide-react";
import gymLogo from "@/assets/gym-logo-new.png";

const ICE_DOMAIN = /^[A-Za-z0-9._%+-]+@u\.icesi\.edu\.co$/;

// ❗ Reemplaza estos IDs por los reales de tu base de datos
const ROLE_OPTIONS = [
  { id: 1, label: "Usuario" },
  { id: 2, label: "Entrenador" },
  { id: 3, label: "Administrador" },
];

export default function Register() {
  const dispatch = useDispatch<any>();
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [fullName, setFullName] = useState("");
  const [personalId, setPersonalId] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [selected, setSelected] = useState<number[]>([]);

  const normalizedEmail = useMemo(() => email.trim().toLowerCase(), [email]);
  const isInstitutional = useMemo(() => ICE_DOMAIN.test(normalizedEmail), [normalizedEmail]);

  const minPass = password.length >= 8;
  const passMatch = password.length > 0 && password === confirm;
  const hasRoles = selected.length > 0;

  const canSubmit = isInstitutional && minPass && passMatch && hasRoles && fullName.trim().length > 0;

  const toggleRole = (id: number, checked: boolean | string) => {
    const isOn = checked === true;
    setSelected((prev) => (isOn ? [...prev, id] : prev.filter((r) => r !== id)));
  };

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
    if (!minPass) {
      toast({
        title: "Contraseña muy corta",
        description: "Debe tener al menos 8 caracteres.",
        variant: "destructive",
      });
      return;
    }
    if (!passMatch) {
      toast({
        title: "Las contraseñas no coinciden",
        description: "Verifica tu confirmación.",
        variant: "destructive",
      });
      return;
    }
    if (!hasRoles) {
      toast({
        title: "Selecciona al menos un rol",
        description: "Usuario, Entrenador o Administrador.",
        variant: "destructive",
      });
      return;
    }

    const { ok, message } = await dispatch(
      registerThunk({
        email: normalizedEmail,
        fullName: fullName.trim(),
        personalId: personalId.trim(),
        password,
        roleIds: selected,
      })
    );

    if (ok) {
      toast({ title: "Usuario creado", description: "Inicia sesión con tu cuenta institucional." });
      navigate("/login");
    } else {
      toast({
        title: "No se pudo crear el usuario",
        description: message ?? "Intenta nuevamente.",
        variant: "destructive",
      });
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background relative overflow-hidden">
      {/* Fondo animado igual al login */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#80808012_1px,transparent_1px),linear-gradient(to_bottom,#80808012_1px,transparent_1px)] bg-[size:24px_24px]" />
      <div className="absolute top-20 left-20 w-72 h-72 bg-primary/5 rounded-full blur-3xl animate-float" />
      <div className="absolute bottom-20 right-20 w-96 h-96 bg-primary/5 rounded-full blur-3xl animate-float" style={{ animationDelay: "2s" }} />
      <div className="absolute top-0 left-1/2 -translate-x-1/2 w-[800px] h-[600px] bg-gradient-to-b from-primary/10 via-transparent to-transparent blur-3xl" />

      <Card className="w-full max-w-2xl border-2 relative z-10 animate-fade-in-up shadow-2xl backdrop-blur-sm bg-card/95">
        <CardHeader className="space-y-4 text-center pb-6">
          <div className="mx-auto w-24 h-24 bg-white rounded-2xl flex items-center justify-center animate-scale-in p-4 shadow-xl border-4 border-foreground hover:scale-110 transition-transform duration-300">
            <img src={gymLogo} alt="Gym Logo" className="w-full h-full object-contain" />
          </div>
          <div>
            <CardTitle className="text-3xl font-black mb-2">CREAR USUARIO</CardTitle>
            <p className="text-sm text-muted-foreground">Completa tu registro con correo institucional</p>
          </div>
        </CardHeader>

        <CardContent className="space-y-6">
          <form onSubmit={onSubmit} className="space-y-5">
            {/* Email */}
            <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: "0.05s" }}>
              <Label htmlFor="email" className="text-sm font-bold flex items-center gap-2">
                <Mail className="w-4 h-4" />
                Email institucional
              </Label>
              <div className="relative group">
                <Input
                  id="email"
                  type="email"
                  placeholder="tuusuario@u.icesi.edu.co"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className={`h-12 transition-all duration-300 ${
                    email && !isInstitutional ? "border-destructive focus-visible:ring-destructive" : "group-hover:border-primary/50"
                  }`}
                  required
                />
                <div className={`absolute inset-0 -z-10 rounded-md blur-md transition-opacity duration-300 ${
                  email && !isInstitutional ? "opacity-0" : "opacity-0 group-hover:opacity-20 bg-primary"
                }`} />
              </div>
              <p className={`text-xs ${email && !isInstitutional ? "text-destructive font-medium" : "text-muted-foreground"}`}>
                Usa tu correo <code className="font-mono bg-muted px-1 py-0.5 rounded">@u.icesi.edu.co</code>
              </p>
            </div>

            {/* Nombre completo */}
            <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: "0.1s" }}>
              <Label htmlFor="fullName" className="text-sm font-bold flex items-center gap-2">
                <UserIcon className="w-4 h-4" />
                Nombre completo
              </Label>
              <Input
                id="fullName"
                placeholder="Juan Pérez"
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
                className="h-12 transition-all duration-300 group-hover:border-primary/50"
                required
              />
            </div>

            {/* Identificación personal */}
            <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: "0.15s" }}>
              <Label htmlFor="personalId" className="text-sm font-bold flex items-center gap-2">
                <IdCard className="w-4 h-4" />
                Identificación personal
              </Label>
              <Input
                id="personalId"
                placeholder="1234567890"
                value={personalId}
                onChange={(e) => setPersonalId(e.target.value)}
                className="h-12 transition-all duration-300 group-hover:border-primary/50"
              />
            </div>

            {/* Contraseña + Confirmación */}
            <div className="grid sm:grid-cols-2 gap-4">
              <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: "0.2s" }}>
                <Label htmlFor="password" className="text-sm font-bold flex items-center gap-2">
                  <Lock className="w-4 h-4" />
                  Contraseña
                </Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className={`h-12 transition-all duration-300 ${
                    password && password.length < 8 ? "border-destructive focus-visible:ring-destructive" : "group-hover:border-primary/50"
                  }`}
                  required
                />
                <p className={`text-xs ${password && password.length < 8 ? "text-destructive font-medium" : "text-muted-foreground"}`}>
                  Mínimo 8 caracteres
                </p>
              </div>

              <div className="space-y-2 animate-fade-in-up" style={{ animationDelay: "0.25s" }}>
                <Label htmlFor="confirm" className="text-sm font-bold">Confirmar contraseña</Label>
                <Input
                  id="confirm"
                  type="password"
                  placeholder="••••••••"
                  value={confirm}
                  onChange={(e) => setConfirm(e.target.value)}
                  className={`h-12 transition-all duration-300 ${
                    confirm && confirm !== password ? "border-destructive focus-visible:ring-destructive" : "group-hover:border-primary/50"
                  }`}
                  required
                />
                <p className={`text-xs ${confirm && confirm !== password ? "text-destructive font-medium" : "text-muted-foreground"}`}>
                  Debe coincidir con la contraseña
                </p>
              </div>
            </div>

            {/* Roles */}
            <div className="space-y-3 animate-fade-in-up" style={{ animationDelay: "0.3s" }}>
              <Label className="text-sm font-bold">Roles</Label>
              <div className="rounded-md border p-4 space-y-3 bg-muted/40">
                {ROLE_OPTIONS.map((r) => (
                  <label key={r.id} className="flex items-center gap-3">
                    <Checkbox
                      checked={selected.includes(r.id)}
                      onCheckedChange={(ck) => toggleRole(r.id, ck)}
                    />
                    <span className="text-sm">{r.label}</span>
                  </label>
                ))}
              </div>
              {!hasRoles && (
                <p className="text-xs text-muted-foreground">
                  Selecciona al menos un rol para continuar.
                </p>
              )}
            </div>

            {/* Acciones */}
            <div className="flex flex-col sm:flex-row gap-3 pt-2">
              <Button
                type="submit"
                className="w-full h-12 text-base font-bold relative overflow-hidden group"
                disabled={!canSubmit}
              >
                <span className="relative z-10">Crear Usuario</span>
                <div className="absolute inset-0 bg-gradient-to-r from-primary/0 via-primary-foreground/10 to-primary/0 translate-x-[-100%] group-hover:translate-x-[100%] transition-transform duration-700" />
              </Button>

              <Button variant="outline" className="w-full h-12" asChild>
                <Link to="/login">Volver al login</Link>
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}