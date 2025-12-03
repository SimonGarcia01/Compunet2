import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  LineChart,
  Line,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer
} from "recharts";
import type { ExerciseProgress } from "@/store/userProgress/userProgressSlice";
import { format, startOfWeek, startOfMonth } from "date-fns";
import { es } from "date-fns/locale";

interface UserProgressChartProps {
  progress: ExerciseProgress[];
}

export function UserProgressChart({ progress }: UserProgressChartProps) {
  if (progress.length === 0) {
    return (
      <Card>
        <CardHeader>
          <CardTitle>Gráficos de Progreso</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="h-[300px] flex items-center justify-center text-muted-foreground">
            <p>No hay suficientes datos para mostrar gráficos. Registra más progreso para ver tus estadísticas visuales.</p>
          </div>
        </CardContent>
      </Card>
    );
  }

  // Group data by week
  const weeklyData = progress.reduce((acc, record) => {
    const date = new Date(record.recordDate);
    const weekStart = startOfWeek(date, { locale: es });
    const weekKey = format(weekStart, 'yyyy-MM-dd');
    
    if (!acc[weekKey]) {
      acc[weekKey] = {
        week: format(weekStart, "d MMM", { locale: es }),
        exercises: 0,
        calories: 0,
        time: 0,
        rpe: [],
      };
    }
    
    acc[weekKey].exercises += 1;
    acc[weekKey].calories += record.estimatedCaloriesBurnt || 0;
    acc[weekKey].time += record.timeMinutes || 0;
    if (record.rpe) {
      acc[weekKey].rpe.push(record.rpe);
    }
    
    return acc;
  }, {} as Record<string, any>);

  const weeklyChartData = Object.values(weeklyData)
    .map((week: any) => ({
      week: week.week,
      ejercicios: week.exercises,
      calorias: Math.round(week.calories),
      tiempo: week.time,
      rpePromedio: week.rpe.length > 0 
        ? parseFloat((week.rpe.reduce((sum: number, val: number) => sum + val, 0) / week.rpe.length).toFixed(1))
        : 0
    }))
    .sort((a, b) => a.week.localeCompare(b.week))
    .slice(-8); // Last 8 weeks

  // Group data by month
  const monthlyData = progress.reduce((acc, record) => {
    const date = new Date(record.recordDate);
    const monthStart = startOfMonth(date);
    const monthKey = format(monthStart, 'yyyy-MM');
    
    if (!acc[monthKey]) {
      acc[monthKey] = {
        month: format(monthStart, "MMM yyyy", { locale: es }),
        exercises: 0,
        calories: 0,
        time: 0,
        rpe: [],
      };
    }
    
    acc[monthKey].exercises += 1;
    acc[monthKey].calories += record.estimatedCaloriesBurnt || 0;
    acc[monthKey].time += record.timeMinutes || 0;
    if (record.rpe) {
      acc[monthKey].rpe.push(record.rpe);
    }
    
    return acc;
  }, {} as Record<string, any>);

  const monthlyChartData = Object.values(monthlyData)
    .map((month: any) => ({
      month: month.month,
      ejercicios: month.exercises,
      calorias: Math.round(month.calories),
      tiempo: month.time,
      rpePromedio: month.rpe.length > 0 
        ? parseFloat((month.rpe.reduce((sum: number, val: number) => sum + val, 0) / month.rpe.length).toFixed(1))
        : 0
    }))
    .sort((a, b) => a.month.localeCompare(b.month))
    .slice(-6); // Last 6 months

  // Exercise type distribution
  const exerciseTypes = progress.reduce((acc, record) => {
    const type = record.exercise?.type || 'Otro';
    acc[type] = (acc[type] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);

  const typeData = Object.entries(exerciseTypes).map(([type, count]) => ({
    tipo: type,
    cantidad: count
  }));

  // Workout distribution
  const workoutDistribution = progress.reduce((acc, record) => {
    const workout = record.workoutProgram?.name || 'Sin rutina';
    acc[workout] = (acc[workout] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);

  const workoutData = Object.entries(workoutDistribution)
    .map(([workout, count]) => ({
      rutina: workout,
      cantidad: count
    }))
    .sort((a, b) => b.cantidad - a.cantidad)
    .slice(0, 5); // Top 5 rutinas

  return (
    <Card>
      <CardHeader>
        <CardTitle>Gráficos de Progreso</CardTitle>
      </CardHeader>
      <CardContent>
        <Tabs defaultValue="weekly" className="space-y-4">
          <TabsList className="grid w-full grid-cols-4">
            <TabsTrigger value="weekly">Semanal</TabsTrigger>
            <TabsTrigger value="monthly">Mensual</TabsTrigger>
            <TabsTrigger value="types">Por Tipo</TabsTrigger>
            <TabsTrigger value="workouts">Por Rutina</TabsTrigger>
          </TabsList>

          {/* Gráfico Semanal */}
          <TabsContent value="weekly" className="space-y-4">
            <div className="h-[350px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={weeklyChartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="week" />
                  <YAxis yAxisId="left" />
                  <YAxis yAxisId="right" orientation="right" />
                  <Tooltip />
                  <Legend />
                  <Line
                    yAxisId="left"
                    type="monotone"
                    dataKey="ejercicios"
                    stroke="hsl(var(--primary))"
                    strokeWidth={2}
                    name="Ejercicios"
                  />
                  <Line
                    yAxisId="right"
                    type="monotone"
                    dataKey="rpePromedio"
                    stroke="hsl(var(--destructive))"
                    strokeWidth={2}
                    name="RPE Promedio"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
            <div className="h-[350px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={weeklyChartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="week" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar
                    dataKey="calorias"
                    fill="hsl(var(--primary))"
                    name="Calorías"
                  />
                  <Bar
                    dataKey="tiempo"
                    fill="hsl(var(--secondary-foreground))"
                    name="Tiempo (min)"
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          {/* Gráfico Mensual */}
          <TabsContent value="monthly" className="space-y-4">
            <div className="h-[350px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={monthlyChartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis yAxisId="left" />
                  <YAxis yAxisId="right" orientation="right" />
                  <Tooltip />
                  <Legend />
                  <Line
                    yAxisId="left"
                    type="monotone"
                    dataKey="ejercicios"
                    stroke="hsl(var(--primary))"
                    strokeWidth={2}
                    name="Ejercicios"
                  />
                  <Line
                    yAxisId="right"
                    type="monotone"
                    dataKey="rpePromedio"
                    stroke="hsl(var(--destructive))"
                    strokeWidth={2}
                    name="RPE Promedio"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
            <div className="h-[350px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={monthlyChartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar
                    dataKey="calorias"
                    fill="hsl(var(--primary))"
                    name="Calorías"
                  />
                  <Bar
                    dataKey="tiempo"
                    fill="hsl(var(--secondary-foreground))"
                    name="Tiempo (min)"
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          {/* Distribución por Tipo de Ejercicio */}
          <TabsContent value="types" className="space-y-4">
            <div className="h-[350px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={typeData} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" />
                  <YAxis dataKey="tipo" type="category" width={100} />
                  <Tooltip />
                  <Legend />
                  <Bar
                    dataKey="cantidad"
                    fill="hsl(var(--primary))"
                    name="Cantidad de Registros"
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          {/* Distribución por Rutina */}
          <TabsContent value="workouts" className="space-y-4">
            {workoutData.length > 0 ? (
              <div className="h-[350px]">
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={workoutData} layout="vertical">
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis type="number" />
                    <YAxis dataKey="rutina" type="category" width={120} />
                    <Tooltip />
                    <Legend />
                    <Bar
                      dataKey="cantidad"
                      fill="hsl(var(--primary))"
                      name="Registros"
                    />
                  </BarChart>
                </ResponsiveContainer>
              </div>
            ) : (
              <div className="h-[350px] flex items-center justify-center text-muted-foreground">
                <p>No hay datos de rutinas para mostrar</p>
              </div>
            )}
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
}

