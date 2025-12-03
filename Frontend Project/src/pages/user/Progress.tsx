import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { fetchMyProgress, deleteProgress } from '@/store/userProgress/thunk';
import { clearError } from '@/store/userProgress/userProgressSlice';
import { ProgressForm } from '@/components/user/ProgressForm';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Alert, AlertDescription } from '@/components/ui/alert';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog';
import { Plus, Edit, Trash2, Calendar, Activity, TrendingUp, AlertCircle } from 'lucide-react';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { toast } from 'sonner';
import type { ExerciseProgress } from '@/store/userProgress/userProgressSlice';
import { UserProgressChart } from '@/components/user/UserProgressChart';

export default function Progress() {
  const dispatch = useDispatch<AppDispatch>();
  const { items, loading, error } = useSelector((state: RootState) => state.userProgress);

  const [formOpen, setFormOpen] = useState(false);
  const [progressToEdit, setProgressToEdit] = useState<ExerciseProgress | null>(null);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [progressToDelete, setProgressToDelete] = useState<string | null>(null);

  useEffect(() => {
    console.log('Cargando progreso...');
    dispatch(fetchMyProgress());
  }, [dispatch]);

  useEffect(() => {
    if (error) {
      console.error('Error en progreso:', error);
      toast.error(error);
      dispatch(clearError());
    }
  }, [error, dispatch]);

  useEffect(() => {
    console.log('Items de progreso actualizados:', items);
  }, [items]);

  const handleAddNew = () => {
    setProgressToEdit(null);
    setFormOpen(true);
  };

  const handleEdit = (progress: ExerciseProgress) => {
    setProgressToEdit(progress);
    setFormOpen(true);
  };

  const handleDeleteClick = (progressId: string) => {
    setProgressToDelete(progressId);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = async () => {
    if (progressToDelete) {
      try {
        await dispatch(deleteProgress(progressToDelete)).unwrap();
        toast.success('Registro de progreso eliminado exitosamente');
        dispatch(fetchMyProgress()); // Refrescar lista
      } catch (err) {
        toast.error('Error al eliminar el registro');
      }
    }
    setDeleteDialogOpen(false);
    setProgressToDelete(null);
  };

  const handleCloseForm = () => {
    setFormOpen(false);
    setProgressToEdit(null);
    dispatch(fetchMyProgress()); // Refrescar lista después de crear/editar
  };

  // Calcular estadísticas básicas
  const safeItems = Array.isArray(items) ? items : [];
  const stats = {
    totalRecords: safeItems.length,
    totalCalories: safeItems.reduce((sum, p) => sum + (p.estimatedCaloriesBurnt || 0), 0),
    avgRpe: safeItems.length > 0
      ? safeItems.reduce((sum, p) => sum + (p.rpe || 0), 0) / safeItems.length
      : 0,
    totalTime: safeItems.reduce((sum, p) => sum + (p.timeMinutes || 0), 0),
  };

  // Ordenar por fecha descendente
  const sortedProgress = [...safeItems].sort(
    (a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime()
  );

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold mb-2">Mi Progreso</h1>
          <p className="text-muted-foreground">
            Registra y visualiza tu progreso de entrenamiento
          </p>
        </div>
        <Button onClick={handleAddNew}>
          <Plus className="mr-2 h-4 w-4" />
          Registrar Progreso
        </Button>
      </div>

      {/* Estadísticas */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Registros</CardTitle>
            <Activity className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalRecords}</div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Calorías Totales</CardTitle>
            <TrendingUp className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalCalories.toFixed(0)}</div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">RPE Promedio</CardTitle>
            <TrendingUp className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {stats.avgRpe > 0 ? stats.avgRpe.toFixed(1) : '-'}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Tiempo Total</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalTime} min</div>
          </CardContent>
        </Card>
      </div>

      {/* Gráficos de Progreso */}
      {sortedProgress.length > 0 && (
        <div className="space-y-6">
          <div>
            <h2 className="text-2xl font-bold mb-2">Estadísticas Visuales</h2>
            <p className="text-muted-foreground">
              Visualiza tu progreso semanal y mensual con gráficos interactivos
            </p>
          </div>
          <UserProgressChart progress={sortedProgress} />
        </div>
      )}

      {/* Lista de progreso */}
      {loading && safeItems.length === 0 ? (
        <Card>
          <CardContent className="py-12">
            <div className="space-y-4">
              <Skeleton className="h-24 w-full" />
              <Skeleton className="h-24 w-full" />
              <Skeleton className="h-24 w-full" />
            </div>
          </CardContent>
        </Card>
      ) : safeItems.length === 0 ? (
        <Card>
          <CardContent className="py-12 text-center">
            <AlertCircle className="h-12 w-12 mx-auto text-muted-foreground mb-4" />
            <h3 className="text-lg font-semibold mb-2">No hay registros de progreso</h3>
            <p className="text-muted-foreground mb-4">
              Comienza registrando tu primer entrenamiento
            </p>
            <Button onClick={handleAddNew}>
              <Plus className="mr-2 h-4 w-4" />
              Registrar Progreso
            </Button>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-4">
          <div>
            <h2 className="text-2xl font-bold mb-2">Historial de Registros</h2>
            <p className="text-muted-foreground">
              Revisa todos tus registros de progreso ordenados por fecha
            </p>
          </div>
          {sortedProgress.map((progress) => (
            <Card key={progress.progressId}>
              <CardContent className="p-6">
                <div className="flex items-start justify-between">
                  <div className="flex-1 space-y-2">
                    <div className="flex items-center gap-2">
                      <h3 className="text-lg font-semibold">
                        {format(new Date(progress.recordDate), 'PPP', { locale: es })}
                      </h3>
                      <Badge variant={progress.periodType === 'DAILY' ? 'default' : 'secondary'}>
                        {progress.periodType === 'DAILY' ? 'Diario' : 'Semanal'}
                      </Badge>
                    </div>

                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm">
                      {progress.workoutProgram && (
                        <div>
                          <p className="text-muted-foreground">Rutina</p>
                          <p className="font-medium">{progress.workoutProgram.name}</p>
                        </div>
                      )}
                      {progress.exercise && (
                        <div>
                          <p className="text-muted-foreground">Ejercicio</p>
                          <p className="font-medium">{progress.exercise.name}</p>
                        </div>
                      )}
                      {progress.repetitions !== undefined && (
                        <div>
                          <p className="text-muted-foreground">Repeticiones</p>
                          <p className="font-medium">{progress.repetitions}</p>
                        </div>
                      )}
                      {progress.timeMinutes !== undefined && (
                        <div>
                          <p className="text-muted-foreground">Tiempo</p>
                          <p className="font-medium">{progress.timeMinutes} min</p>
                        </div>
                      )}
                      {progress.distanceKm !== undefined && (
                        <div>
                          <p className="text-muted-foreground">Distancia</p>
                          <p className="font-medium">{progress.distanceKm} km</p>
                        </div>
                      )}
                      {progress.rpe !== undefined && (
                        <div>
                          <p className="text-muted-foreground">RPE</p>
                          <p className="font-medium">{progress.rpe}/10</p>
                        </div>
                      )}
                      {progress.estimatedCaloriesBurnt !== undefined && (
                        <div>
                          <p className="text-muted-foreground">Calorías</p>
                          <p className="font-medium">{progress.estimatedCaloriesBurnt.toFixed(0)}</p>
                        </div>
                      )}
                    </div>

                    {progress.notes && (
                      <div className="mt-2">
                        <p className="text-sm text-muted-foreground">Notas:</p>
                        <p className="text-sm">{progress.notes}</p>
                      </div>
                    )}
                  </div>

                  <div className="flex gap-2 ml-4">
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => handleEdit(progress)}
                    >
                      <Edit className="h-4 w-4" />
                    </Button>
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => progress.progressId && handleDeleteClick(progress.progressId)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {/* Formulario */}
      <ProgressForm
        open={formOpen}
        onClose={handleCloseForm}
        progressToEdit={progressToEdit}
      />

      {/* Diálogo de confirmación de eliminación */}
      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>¿Eliminar registro de progreso?</AlertDialogTitle>
            <AlertDialogDescription>
              Esta acción no se puede deshacer. Se eliminará permanentemente este registro de progreso.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={handleDeleteConfirm}>Eliminar</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}

