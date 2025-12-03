import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { fetchHistory } from '@/store/history/thunk';
import { clearError } from '@/store/history/historySlice';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { 
  History as HistoryIcon, 
  Activity, 
  Calendar, 
  Flame, 
  TrendingUp, 
  Search,
  Dumbbell,
  Users,
  Clock,
  AlertCircle,
} from 'lucide-react';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { toast } from 'sonner';
import type { HistoryActivity } from '@/store/history/historySlice';

export default function History() {
  const dispatch = useDispatch<AppDispatch>();
  const { activities, loading, error, metrics } = useSelector((state: RootState) => state.history);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState<string>('all');
  const [filterDate, setFilterDate] = useState<string>('all');

  useEffect(() => {
    dispatch(fetchHistory());
  }, [dispatch]);

  useEffect(() => {
    if (error) {
      toast.error(error);
      dispatch(clearError());
    }
  }, [error, dispatch]);

  const filteredActivities = activities.filter((activity) => {
    const matchesSearch = 
      activity.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      activity.description?.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesType = filterType === 'all' || activity.type === filterType;
    
    let matchesDate = true;
    if (filterDate !== 'all') {
      const activityDate = new Date(activity.date);
      const now = new Date();
      const daysDiff = Math.floor((now.getTime() - activityDate.getTime()) / (1000 * 60 * 60 * 24));
      
      switch (filterDate) {
        case 'today':
          matchesDate = daysDiff === 0;
          break;
        case 'week':
          matchesDate = daysDiff <= 7;
          break;
        case 'month':
          matchesDate = daysDiff <= 30;
          break;
        case 'year':
          matchesDate = daysDiff <= 365;
          break;
      }
    }
    
    return matchesSearch && matchesType && matchesDate;
  });

  const getActivityIcon = (type: string) => {
    switch (type) {
      case 'progress':
        return <Activity className="h-5 w-5" />;
      case 'routine':
        return <Dumbbell className="h-5 w-5" />;
      case 'event':
        return <Users className="h-5 w-5" />;
      case 'historical':
        return <HistoryIcon className="h-5 w-5" />;
      default:
        return <Calendar className="h-5 w-5" />;
    }
  };

  const getActivityBadge = (type: string) => {
    const badges = {
      progress: { label: 'Progreso', variant: 'default' as const },
      routine: { label: 'Rutina', variant: 'secondary' as const },
      event: { label: 'Evento', variant: 'outline' as const },
      historical: { label: 'Histórico', variant: 'outline' as const },
    };
    return badges[type as keyof typeof badges] || badges.progress;
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2 flex items-center gap-3">
          <HistoryIcon className="h-8 w-8 text-primary" />
          Historial de Actividades
        </h1>
        <p className="text-muted-foreground">
          Consulta tu historial completo de actividades, rutinas pasadas y métricas de rendimiento
        </p>
      </div>

      {/* Métricas */}
      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
          {[...Array(5)].map((_, i) => (
            <Skeleton key={i} className="h-24" />
          ))}
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground">Total Actividades</p>
                  <p className="text-2xl font-bold">{metrics.totalActivities}</p>
                </div>
                <Activity className="h-8 w-8 text-primary" />
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground">Calorías Totales</p>
                  <p className="text-2xl font-bold">{metrics.totalCalories.toFixed(0)}</p>
                </div>
                <Flame className="h-8 w-8 text-orange-500" />
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground">Rutinas Completadas</p>
                  <p className="text-2xl font-bold">{metrics.completedRoutines}</p>
                </div>
                <Dumbbell className="h-8 w-8 text-blue-500" />
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground">Eventos Asistidos</p>
                  <p className="text-2xl font-bold">{metrics.eventsAttended}</p>
                </div>
                <Users className="h-8 w-8 text-green-500" />
              </div>
            </CardContent>
          </Card>
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground">Días Activos</p>
                  <p className="text-2xl font-bold">{metrics.daysActive}</p>
                </div>
                <TrendingUp className="h-8 w-8 text-purple-500" />
              </div>
            </CardContent>
          </Card>
        </div>
      )}

      {/* Filtros */}
      <Card>
        <CardHeader>
          <CardTitle>Filtros</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
              <Input
                placeholder="Buscar actividades..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
            <Select value={filterType} onValueChange={setFilterType}>
              <SelectTrigger>
                <SelectValue placeholder="Tipo de actividad" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todos los tipos</SelectItem>
                <SelectItem value="progress">Progreso</SelectItem>
                <SelectItem value="routine">Rutinas</SelectItem>
                <SelectItem value="event">Eventos</SelectItem>
                <SelectItem value="historical">Históricos</SelectItem>
              </SelectContent>
            </Select>
            <Select value={filterDate} onValueChange={setFilterDate}>
              <SelectTrigger>
                <SelectValue placeholder="Período" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todo el tiempo</SelectItem>
                <SelectItem value="today">Hoy</SelectItem>
                <SelectItem value="week">Última semana</SelectItem>
                <SelectItem value="month">Último mes</SelectItem>
                <SelectItem value="year">Último año</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      {/* Lista de actividades */}
      {loading ? (
        <div className="space-y-4">
          {[...Array(5)].map((_, i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
      ) : filteredActivities.length === 0 ? (
        <Card>
          <CardContent className="p-12 text-center">
            <AlertCircle className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
            <h3 className="text-xl font-bold mb-2">No hay actividades</h3>
            <p className="text-muted-foreground">
              {searchTerm || filterType !== 'all' || filterDate !== 'all'
                ? 'No se encontraron actividades con los filtros seleccionados'
                : 'Aún no tienes actividades registradas en tu historial'}
            </p>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-4">
          {filteredActivities.map((activity) => {
            const badge = getActivityBadge(activity.type);
            return (
              <Card key={activity.id} className="hover:shadow-md transition-shadow">
                <CardContent className="p-6">
                  <div className="flex items-start gap-4">
                    <div className="p-3 rounded-lg bg-primary/10 text-primary">
                      {getActivityIcon(activity.type)}
                    </div>
                    <div className="flex-1 space-y-2">
                      <div className="flex items-start justify-between gap-4">
                        <div className="flex-1">
                          <div className="flex items-center gap-2 mb-1">
                            <h3 className="text-lg font-semibold">{activity.title}</h3>
                            <Badge variant={badge.variant}>{badge.label}</Badge>
                          </div>
                          {activity.description && (
                            <p className="text-sm text-muted-foreground mb-2">
                              {activity.description}
                            </p>
                          )}
                          <div className="flex items-center gap-4 text-sm text-muted-foreground">
                            <div className="flex items-center gap-1">
                              <Calendar className="h-4 w-4" />
                              {format(new Date(activity.date), 'PPP', { locale: es })}
                            </div>
                            {activity.calories && (
                              <div className="flex items-center gap-1">
                                <Flame className="h-4 w-4" />
                                {activity.calories.toFixed(0)} kcal
                              </div>
                            )}
                            {activity.details?.timeMinutes && (
                              <div className="flex items-center gap-1">
                                <Clock className="h-4 w-4" />
                                {activity.details.timeMinutes} min
                              </div>
                            )}
                          </div>
                          {activity.details && (
                            <div className="mt-2 flex flex-wrap gap-2">
                              {activity.details.repetitions && (
                                <Badge variant="outline">
                                  {activity.details.repetitions} repeticiones
                                </Badge>
                              )}
                              {activity.details.distanceKm && (
                                <Badge variant="outline">
                                  {activity.details.distanceKm} km
                                </Badge>
                              )}
                              {activity.details.rpe && (
                                <Badge variant="outline">
                                  RPE: {activity.details.rpe}/10
                                </Badge>
                              )}
                              {activity.details.exercisesCount && (
                                <Badge variant="outline">
                                  {activity.details.exercisesCount} ejercicios
                                </Badge>
                              )}
                            </div>
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            );
          })}
        </div>
      )}
    </div>
  );
}


