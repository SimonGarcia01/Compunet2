import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import type { RootState } from "@/store/index";
import { RoleBasedNavbar } from "@/components/navbar";

export default function AppPage() {
  const { token, user } = useSelector((s: RootState) => s.auth);

  if (!token) return <Navigate to="/login" replace />;

  const getRoleMessage = () => {
    const roles = user?.roles ?? [];
    if (roles.includes("Administrador")) {
      return {
        title: "Panel de Administración",
        description: "Gestiona usuarios, entrenadores, ejercicios y eventos de la plataforma.",
      };
    }
    if (roles.includes("Entrenador")) {
      return {
        title: "Panel de Entrenador",
        description: "Administra tus alumnos, crea rutinas y genera recomendaciones personalizadas.",
      };
    }
    return {
      title: "Panel de Usuario",
      description: "Gestiona tus rutinas de ejercicio y sigue tu progreso.",
    };
  };

  const roleInfo = getRoleMessage();

  return (
    <div className="min-h-screen bg-background">
      <RoleBasedNavbar />
      
      <div className="container mx-auto p-8">
        <div className="max-w-4xl mx-auto">
          <div className="mb-8">
            <h1 className="text-4xl font-black mb-2">{roleInfo.title}</h1>
            <p className="text-muted-foreground text-lg">
              Bienvenido, {user?.email?.split("@")[0]}
            </p>
          </div>
          
          <div className="grid gap-6">
            <div className="p-6 border-2 rounded-lg bg-card">
              <h2 className="text-2xl font-bold mb-2">¡Bienvenido a Gym Icesi!</h2>
              <p className="text-muted-foreground mb-4">
                {roleInfo.description}
              </p>
              <div className="flex gap-2 text-sm">
                <span className="px-3 py-1 bg-primary/10 text-primary rounded-full font-semibold">
                  Rol: {user?.roles?.[0]}
                </span>
              </div>
            </div>

            <div className="grid md:grid-cols-2 gap-4">
              <div className="p-6 border-2 rounded-lg hover:border-primary transition-colors">
                <h3 className="text-lg font-bold mb-2">🚀 Próximamente</h3>
                <p className="text-sm text-muted-foreground">
                  Más funcionalidades estarán disponibles pronto.
                </p>
              </div>
              <div className="p-6 border-2 rounded-lg hover:border-primary transition-colors">
                <h3 className="text-lg font-bold mb-2">📊 Estadísticas</h3>
                <p className="text-sm text-muted-foreground">
                  Visualiza tu progreso y rendimiento.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
