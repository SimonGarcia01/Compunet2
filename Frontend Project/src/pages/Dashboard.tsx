import { useSelector } from "react-redux";
import type { RootState } from "@/store/index";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function Dashboard() {
  const { user } = useSelector((state: RootState) => state.auth);
  const roles = user?.roles ?? [];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2">Dashboard</h1>
        <p className="text-muted-foreground">
          Bienvenido, {user?.email}
        </p>
        <p className="text-sm text-muted-foreground mt-1">
          Roles: {roles.join(", ")}
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        <Card>
          <CardHeader>
            <CardTitle>Bienvenido</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-muted-foreground">
              Esta es tu área personal. Selecciona una opción del menú para comenzar.
            </p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

