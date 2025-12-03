import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import { Users, UserCheck, Dumbbell, Calendar, TrendingUp, Activity, BookOpen, AlertCircle } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";
import type { RootState, AppDispatch } from "@/store/index";
import { fetchAdminStats } from "@/store/admin/thunk";
import { clearError } from "@/store/admin/adminSlice";

export default function AdminDashboard() {
  const dispatch = useDispatch<AppDispatch>();
  const { stats, loading, error } = useSelector((s: RootState) => s.admin);

  useEffect(() => {
    dispatch(fetchAdminStats());
  }, [dispatch]);

  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => {
        dispatch(clearError());
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [error, dispatch]);

  const statCards = [
    {
      title: "Usuarios",
      value: stats?.totalUsers ?? 0,
      description: `${stats?.activeUsers ?? 0} activos`,
      icon: Users,
      color: "text-blue-600",
      bgColor: "bg-blue-50 dark:bg-blue-950",
    },
    {
      title: "Entrenadores",
      value: stats?.totalTrainers ?? 0,
      description: "Entrenadores registrados",
      icon: UserCheck,
      color: "text-green-600",
      bgColor: "bg-green-50 dark:bg-green-950",
    },
    {
      title: "Ejercicios",
      value: stats?.totalExercises ?? 0,
      description: "Ejercicios disponibles",
      icon: Dumbbell,
      color: "text-purple-600",
      bgColor: "bg-purple-50 dark:bg-purple-950",
    },
    {
      title: "Eventos",
      value: stats?.totalEvents ?? 0,
      description: `${stats?.upcomingEvents ?? 0} próximos`,
      icon: Calendar,
      color: "text-orange-600",
      bgColor: "bg-orange-50 dark:bg-orange-950",
    },
    {
      title: "Rutinas",
      value: stats?.totalRoutines ?? 0,
      description: "Rutinas creadas",
      icon: BookOpen,
      color: "text-indigo-600",
      bgColor: "bg-indigo-50 dark:bg-indigo-950",
    },
    {
      title: "Usuarios Activos",
      value: stats?.activeUsers ?? 0,
      description: `${stats?.totalUsers ? Math.round((stats.activeUsers / stats.totalUsers) * 100) : 0}% del total`,
      icon: Activity,
      color: "text-emerald-600",
      bgColor: "bg-emerald-50 dark:bg-emerald-950",
    },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2">Panel de Administración</h1>
        <p className="text-muted-foreground">
          Gestiona usuarios, entrenadores, ejercicios y eventos
        </p>
      </div>

      {error && (
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {statCards.map((stat, index) => {
          const Icon = stat.icon;
          return (
            <Card key={index} className="relative overflow-hidden">
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">{stat.title}</CardTitle>
                <div className={`${stat.bgColor} p-2 rounded-lg`}>
                  <Icon className={`h-5 w-5 ${stat.color}`} />
                </div>
              </CardHeader>
              <CardContent>
                {loading ? (
                  <div className="space-y-2">
                    <Skeleton className="h-8 w-20" />
                    <Skeleton className="h-4 w-32" />
                  </div>
                ) : (
                  <>
                    <div className="text-2xl font-bold">{stat.value}</div>
                    <p className="text-xs text-muted-foreground mt-1">{stat.description}</p>
                  </>
                )}
              </CardContent>
            </Card>
          );
        })}
      </div>

      {/* Sección de métricas adicionales */}
      {!loading && stats && (
        <div className="grid gap-4 md:grid-cols-2">
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <TrendingUp className="h-5 w-5" />
                Resumen de Actividad
              </CardTitle>
              <CardDescription>Métricas generales del sistema</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Tasa de usuarios activos</span>
                <span className="text-sm font-semibold">
                  {stats.totalUsers > 0
                    ? `${Math.round((stats.activeUsers / stats.totalUsers) * 100)}%`
                    : '0%'}
                </span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Proporción entrenadores/usuarios</span>
                <span className="text-sm font-semibold">
                  {stats.totalUsers > 0
                    ? `1:${Math.round(stats.totalUsers / (stats.totalTrainers || 1))}`
                    : 'N/A'}
                </span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-sm text-muted-foreground">Eventos próximos</span>
                <span className="text-sm font-semibold">{stats.upcomingEvents}</span>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Activity className="h-5 w-5" />
                Distribución
              </CardTitle>
              <CardDescription>Distribución de recursos</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div>
                <div className="flex justify-between text-sm mb-1">
                  <span className="text-muted-foreground">Usuarios</span>
                  <span className="font-semibold">{stats.totalUsers}</span>
                </div>
                <div className="w-full bg-secondary rounded-full h-2">
                  <div
                    className="bg-blue-600 h-2 rounded-full"
                    style={{ width: '100%' }}
                  />
                </div>
              </div>
              <div>
                <div className="flex justify-between text-sm mb-1">
                  <span className="text-muted-foreground">Entrenadores</span>
                  <span className="font-semibold">{stats.totalTrainers}</span>
                </div>
                <div className="w-full bg-secondary rounded-full h-2">
                  <div
                    className="bg-green-600 h-2 rounded-full"
                    style={{
                      width: stats.totalUsers > 0
                        ? `${(stats.totalTrainers / stats.totalUsers) * 100}%`
                        : '0%',
                    }}
                  />
                </div>
              </div>
              <div>
                <div className="flex justify-between text-sm mb-1">
                  <span className="text-muted-foreground">Ejercicios</span>
                  <span className="font-semibold">{stats.totalExercises}</span>
                </div>
                <div className="w-full bg-secondary rounded-full h-2">
                  <div
                    className="bg-purple-600 h-2 rounded-full"
                    style={{
                      width: stats.totalExercises > 0
                        ? `${Math.min((stats.totalExercises / 100) * 100, 100)}%`
                        : '0%',
                    }}
                  />
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  );
}
