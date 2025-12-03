import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function UserRoutines() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2">Mis Rutinas</h1>
        <p className="text-muted-foreground">
          Gestiona tus rutinas de entrenamiento
        </p>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Rutinas Activas</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-muted-foreground">
            No tienes rutinas asignadas aún.
          </p>
        </CardContent>
      </Card>
    </div>
  );
}

