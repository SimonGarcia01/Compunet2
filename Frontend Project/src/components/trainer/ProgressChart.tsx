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
import type { ExerciseProgress } from "@/store/trainer/trainerSlice"
import { format, startOfWeek, endOfWeek } from "date-fns";
import { es } from "date-fns/locale";

interface ProgressChartProps {
  progress: ExerciseProgress[];
}

export function ProgressChart({ progress }: ProgressChartProps) {
  // Group data by week for calories and RPE
  const weeklyData = progress.reduce((acc, record) => {
    const date = new Date(record.recordDate);
    const weekStart = startOfWeek(date, { locale: es });
    const weekKey = format(weekStart, 'yyyy-MM-dd');
    
    if (!acc[weekKey]) {
      acc[weekKey] = {
        week: format(weekStart, "d MMM", { locale: es }),
        exercises: 0,
        calories: 0,
        rpe: [],
      };
    }
    
    acc[weekKey].exercises += 1;
    acc[weekKey].calories += record.estimatedCaloriesBurnt || 0;
    if (record.rpe) {
      acc[weekKey].rpe.push(record.rpe);
    }
    
    return acc;
  }, {} as Record<string, any>);

  const chartData = Object.values(weeklyData)
    .map((week: any) => ({
      week: week.week,
      exercises: week.exercises,
      calories: Math.round(week.calories),
      avgRpe: week.rpe.length > 0 
        ? (week.rpe.reduce((sum: number, val: number) => sum + val, 0) / week.rpe.length).toFixed(1)
        : 0
    }))
    .sort((a, b) => a.week.localeCompare(b.week))
    .slice(-8); // Last 8 weeks

  // Exercise type distribution
  const exerciseTypes = progress.reduce((acc, record) => {
    const type = record.exercise?.type || 'Otro';
    acc[type] = (acc[type] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);

  const typeData = Object.entries(exerciseTypes).map(([type, count]) => ({
    type,
    count
  }));

  return (
    <Card>
      <CardHeader>
        <CardTitle>Gráficos de Progreso</CardTitle>
      </CardHeader>
      <CardContent>
        <Tabs defaultValue="timeline" className="space-y-4">
          <TabsList className="grid w-full grid-cols-3">
            <TabsTrigger value="timeline">Línea Temporal</TabsTrigger>
            <TabsTrigger value="calories">Calorías</TabsTrigger>
            <TabsTrigger value="types">Por Tipo</TabsTrigger>
          </TabsList>

          <TabsContent value="timeline" className="space-y-4">
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={chartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="week" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line
                    type="monotone"
                    dataKey="exercises"
                    stroke="hsl(var(--primary))"
                    strokeWidth={2}
                    name="Ejercicios"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          <TabsContent value="calories" className="space-y-4">
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={chartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="week" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar
                    dataKey="calories"
                    fill="hsl(var(--primary))"
                    name="Calorías"
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          <TabsContent value="types" className="space-y-4">
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={typeData} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis type="number" />
                  <YAxis dataKey="type" type="category" />
                  <Tooltip />
                  <Legend />
                  <Bar
                    dataKey="count"
                    fill="hsl(var(--primary))"
                    name="Cantidad"
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
}
