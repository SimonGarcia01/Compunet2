import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { AppDispatch, RootState } from '@/store/index';
import { fetchMyPrograms, deleteProgram } from '@/store/workoutPrograms/thunk';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Plus, Edit, Trash2, Copy, Calendar, CheckCircle2, Circle, Play } from 'lucide-react';
import { Skeleton } from '@/components/ui/skeleton';
import { toast } from '@/hooks/use-toast';
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
import { useState } from 'react';

export default function MyRoutines() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { items, loading } = useSelector((state: RootState) => state.workoutPrograms);
  const [deleteId, setDeleteId] = useState<string | null>(null);

  useEffect(() => {
    dispatch(fetchMyPrograms());
  }, [dispatch]);

  const handleDelete = async (id: string) => {
    try {
      await dispatch(deleteProgram(id)).unwrap();
      toast({
        title: 'Rutina eliminada',
        description: 'La rutina ha sido eliminada exitosamente',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'No se pudo eliminar la rutina',
        variant: 'destructive',
      });
    }
    setDeleteId(null);
  };

  const handleDuplicate = (program: any) => {
    // TODO: Implementar duplicación
    toast({
      title: 'Función en desarrollo',
      description: 'La duplicación de rutinas estará disponible pronto',
    });
  };

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 mb-8">
          <div>
            <h1 className="text-4xl font-black mb-2">Mis Rutinas</h1>
            <p className="text-muted-foreground">
              Gestiona tus programas de entrenamiento
            </p>
          </div>
          <Button
            size="lg"
            onClick={() => navigate('/app/user/routines/new')}
            className="gap-2"
          >
            <Plus className="h-5 w-5" />
            Nueva rutina
          </Button>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {[...Array(6)].map((_, i) => (
              <Skeleton key={i} className="h-96 rounded-lg" />
            ))}
          </div>
        ) : !Array.isArray(items) || items.length === 0 ? (
          <div className="text-center py-20">
            <div className="mb-6">
              <div className="w-20 h-20 rounded-full bg-muted mx-auto flex items-center justify-center mb-4">
                <Plus className="h-10 w-10 text-muted-foreground" />
              </div>
              <h3 className="text-xl font-bold mb-2">Aún no tienes rutinas</h3>
              <p className="text-muted-foreground mb-6">
                Crea tu primera rutina para comenzar tu entrenamiento
              </p>
            </div>
            <Button
              size="lg"
              onClick={() => navigate('/app/user/routines/new')}
              className="gap-2"
            >
              <Plus className="h-5 w-5" />
              Crear mi primera rutina
            </Button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {items.map((program) => (
              <Card
                key={program.workoutId}
                className="overflow-hidden hover:shadow-lg transition-all duration-300 border-2 hover:border-primary/20 group"
              >
                <div className="relative aspect-video bg-gradient-to-br from-primary/10 to-primary/5 overflow-hidden">
                  {program.photoUrl ? (
                    <img
                      src={program.photoUrl}
                      alt={program.name}
                      className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
                    />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center">
                      <Calendar className="h-16 w-16 text-primary/20" />
                    </div>
                  )}
                  {program.completed ? (
                    <Badge className="absolute top-3 right-3 gap-1 bg-green-500 text-white border-0">
                      <CheckCircle2 className="h-3 w-3" />
                      Completada
                    </Badge>
                  ) : (
                    <Badge className="absolute top-3 right-3 gap-1 bg-yellow-500 text-white border-0">
                      <Circle className="h-3 w-3" />
                      En progreso
                    </Badge>
                  )}
                </div>

                <CardHeader>
                  <div className="flex items-start justify-between gap-2">
                    <div className="flex-1 min-w-0">
                      <h3 className="font-black text-xl line-clamp-1 mb-1">
                        {program.name}
                      </h3>
                      <p className="text-sm text-muted-foreground">
                        {new Date(program.creationDate).toLocaleDateString('es-ES', {
                          day: 'numeric',
                          month: 'long',
                          year: 'numeric',
                        })}
                      </p>
                    </div>
                  </div>
                </CardHeader>

                <CardContent>
                  <p className="text-sm text-muted-foreground line-clamp-3 mb-4">
                    {program.description}
                  </p>
                  <div className="flex items-center gap-2 text-sm">
                    <Badge variant="secondary">
                      {program.exercises?.length || 0} ejercicios
                    </Badge>
                  </div>
                </CardContent>

                <CardFooter className="flex gap-2 p-4 pt-0">
                  <Button
                    className="flex-1 gap-2"
                    onClick={() => navigate(`/app/user/routines/${program.workoutId}/train`)}
                  >
                    <Play className="h-4 w-4" />
                    Entrenar
                  </Button>
                  <Button
                    variant="outline"
                    className="flex-1 gap-2"
                    onClick={() => navigate(`/app/user/routines/${program.workoutId}/edit`)}
                  >
                    <Edit className="h-4 w-4" />
                    Editar
                  </Button>
                  <Button
                    variant="outline"
                    size="icon"
                    onClick={() => handleDuplicate(program)}
                  >
                    <Copy className="h-4 w-4" />
                  </Button>
                  <Button
                    variant="outline"
                    size="icon"
                    onClick={() => setDeleteId(program.workoutId)}
                  >
                    <Trash2 className="h-4 w-4" />
                  </Button>
                </CardFooter>
              </Card>
            ))}
          </div>
        )}
      </div>

      <AlertDialog open={!!deleteId} onOpenChange={() => setDeleteId(null)}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>¿Eliminar rutina?</AlertDialogTitle>
            <AlertDialogDescription>
              Esta acción no se puede deshacer. Se eliminará la rutina y todos sus ejercicios asociados.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={() => deleteId && handleDelete(deleteId)}>
              Eliminar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
