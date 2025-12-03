import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import { Activity, Flame, Calendar, TrendingUp, Dumbbell, Clock } from "lucide-react";
import type { ProgressAnalysisResult } from "@/store/recommendations/recommendationsSlice";

interface ProgressMetricsProps {
  analysis: ProgressAnalysisResult | null;
  loading?: boolean;
}

export function ProgressMetrics({ analysis, loading }: ProgressMetricsProps) {
  if (loading) {
    return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {[1, 2, 3, 4, 5, 6].map((i) => (
          <Skeleton key={i} className="h-[120px]" />
        ))}
      </div>
    );
  }

  if (!analysis) {
    return null;
  }

  const metrics = [
    {
      title: "RPE Promedio",
      value: analysis.avgRpe.toFixed(1),
      icon: Activity,
      description: "Últimos registros"
    },
    {
      title: "Calorías Totales",
      value: analysis.totalCalories.toLocaleString(),
      icon: Flame,
      description: "Total quemadas"
    },
    {
      title: "Días Activos",
      value: analysis.activeDays,
      icon: Calendar,
      description: "Con actividad"
    },
    {
      title: "Rutina Favorita",
      value: analysis.mostUsedWorkout,
      icon: Dumbbell,
      description: "Más utilizada",
      isText: true
    },
    {
      title: "Última Actividad",
      value: analysis.lastActivity,
      icon: Clock,
      description: "Fecha registro",
      isText: true
    },
    {
      title: "Tendencia General",
      value: analysis.trends.rpeIncreasing ? "↑ Aumentando" : analysis.trends.frequencyDecreasing ? "↓ Disminuyendo" : "→ Estable",
      icon: TrendingUp,
      description: "Patrón detectado",
      isText: true
    }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      {metrics.map((metric) => {
        const Icon = metric.icon;
        return (
          <Card key={metric.title}>
            <CardHeader className="pb-3">
              <CardTitle className="text-sm font-medium flex items-center gap-2">
                <Icon className="h-4 w-4 text-muted-foreground" />
                {metric.title}
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-1">
                <p className={`${metric.isText ? 'text-xl' : 'text-2xl'} font-bold`}>
                  {metric.value}
                </p>
                <p className="text-xs text-muted-foreground">{metric.description}</p>
              </div>
            </CardContent>
          </Card>
        );
      })}
    </div>
  );
}
