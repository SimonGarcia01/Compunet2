import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Activity, Flame, TrendingUp, Calendar, Dumbbell, Clock } from "lucide-react";
import { Progress } from "@/components/ui/progress";
import type { ExerciseProgress } from "@/store/trainer/trainerSlice"
import { format } from "date-fns";
import { es } from "date-fns/locale";

interface ProgressStatsProps {
  progress: ExerciseProgress[];
}

export function ProgressStats({ progress }: ProgressStatsProps) {
  const totalExercises = progress.length;
  const totalCalories = progress.reduce((sum, p) => sum + (p.estimatedCaloriesBurnt || 0), 0);
  const avgRpe = progress.length > 0
    ? progress.reduce((sum, p) => sum + (p.rpe || 0), 0) / progress.filter(p => p.rpe).length
    : 0;
  
  const uniqueDays = new Set(progress.map(p => p.recordDate.split('T')[0])).size;
  
  const workoutCounts = progress.reduce((acc, p) => {
    const workoutName = p.workoutProgram?.name || 'Sin rutina';
    acc[workoutName] = (acc[workoutName] || 0) + 1;
    return acc;
  }, {} as Record<string, number>);
  
  const mostUsedWorkout = Object.entries(workoutCounts).sort((a, b) => b[1] - a[1])[0];
  
  const lastActivity = progress.length > 0
    ? new Date(progress[0].recordDate)
    : null;

  const stats = [
    {
      title: "Ejercicios Completados",
      value: totalExercises,
      icon: Activity,
      description: "Total registrado"
    },
    {
      title: "Calorías Quemadas",
      value: Math.round(totalCalories),
      icon: Flame,
      description: "Estimado total"
    },
    {
      title: "RPE Promedio",
      value: avgRpe.toFixed(1),
      icon: TrendingUp,
      description: "Esfuerzo percibido",
      progress: (avgRpe / 10) * 100
    },
    {
      title: "Días Activos",
      value: uniqueDays,
      icon: Calendar,
      description: "Días con actividad"
    },
    {
      title: "Rutina Más Usada",
      value: mostUsedWorkout?.[0] || "N/A",
      icon: Dumbbell,
      description: mostUsedWorkout ? `${mostUsedWorkout[1]} veces` : "Sin datos"
    },
    {
      title: "Última Actividad",
      value: lastActivity ? format(lastActivity, "d MMM", { locale: es }) : "N/A",
      icon: Clock,
      description: lastActivity ? format(lastActivity, "yyyy", { locale: es }) : "Sin registros"
    }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {stats.map((stat, index) => {
        const Icon = stat.icon;
        return (
          <Card key={index}>
            <CardHeader className="flex flex-row items-center justify-between pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                {stat.title}
              </CardTitle>
              <Icon className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stat.value}</div>
              <p className="text-xs text-muted-foreground mt-1">
                {stat.description}
              </p>
              {stat.progress !== undefined && (
                <Progress value={stat.progress} className="mt-2" />
              )}
            </CardContent>
          </Card>
        );
      })}
    </div>
  );
}
