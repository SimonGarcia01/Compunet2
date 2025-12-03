import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { AppDispatch, RootState } from '@/store/index';
import { fetchPredesignedPrograms, adoptProgram } from '@/store/workoutPrograms/thunk';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import { Input } from '@/components/ui/input';
import { Search, Download, Calendar, User, Loader2, AlertCircle } from 'lucide-react';
import { toast } from 'sonner';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
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
import type { WorkoutProgram } from '@/store/workoutPrograms/workoutProgramsSlice';

export default function PredesignedRoutines() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { predesigned, predesignedLoading, saving, error } = useSelector(
    (state: RootState) => state.workoutPrograms
  );
  const [searchTerm, setSearchTerm] = useState('');
  const [adoptingId, setAdoptingId] = useState<string | null>(null);
  const [confirmDialogOpen, setConfirmDialogOpen] = useState(false);
  const [programToAdopt, setProgramToAdopt] = useState<WorkoutProgram | null>(null);

  useEffect(() => {
    dispatch(fetchPredesignedPrograms());
  }, [dispatch]);

  useEffect(() => {
    if (error) {
      toast.error(error);
    }
  }, [error]);

  const handleAdoptClick = (program: WorkoutProgram) => {
    setProgramToAdopt(program);
    setConfirmDialogOpen(true);
  };

  const handleConfirmAdopt = async () => {
    if (!programToAdopt) return;

    setAdoptingId(programToAdopt.workoutId);
    try {
      const result = await dispatch(adoptProgram({ programId: programToAdopt.workoutId })).unwrap();
      toast.success('Rutina adoptada exitosamente. Puedes editarla en "Mis Rutinas"');
      setConfirmDialogOpen(false);
      setProgramToAdopt(null);
      // Navegar a la rutina editada
      navigate(`/app/user/routines/${result.workoutId}/edit`);
    } catch (error: any) {
      toast.error(error || 'Error al adoptar la rutina');
    } finally {
      setAdoptingId(null);
    }
  };

  const filteredPrograms = predesigned.filter((program) =>
    program.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    program.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
    program.creator?.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    program.creator?.email?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 mb-8">
          <div>
            <h1 className="text-4xl font-black mb-2">Rutinas Prediseñadas</h1>
            <p className="text-muted-foreground">
              Explora rutinas creadas por nuestros entrenadores y adóptalas a tu cuenta
            </p>
          </div>
        </div>

        {/* Buscador */}
        <div className="mb-6">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
            <Input
              placeholder="Buscar por nombre, descripción o entrenador..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10"
            />
          </div>
        </div>

        {predesignedLoading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {[...Array(6)].map((_, i) => (
              <Skeleton key={i} className="h-96 rounded-lg" />
            ))}
          </div>
        ) : filteredPrograms.length === 0 ? (
          <div className="text-center py-20">
            <div className="mb-6">
              <div className="w-20 h-20 rounded-full bg-muted mx-auto flex items-center justify-center mb-4">
                <AlertCircle className="h-10 w-10 text-muted-foreground" />
              </div>
              <h3 className="text-xl font-bold mb-2">
                {searchTerm ? 'No se encontraron rutinas' : 'No hay rutinas prediseñadas disponibles'}
              </h3>
              <p className="text-muted-foreground">
                {searchTerm
                  ? 'Intenta con otros términos de búsqueda'
                  : 'Los entrenadores aún no han creado rutinas prediseñadas'}
              </p>
            </div>
            {searchTerm && (
              <Button variant="outline" onClick={() => setSearchTerm('')}>
                Limpiar búsqueda
              </Button>
            )}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredPrograms.map((program) => (
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
                  <Badge className="absolute top-3 left-3 gap-1 bg-primary text-primary-foreground border-0">
                    <Download className="h-3 w-3" />
                    Prediseñada
                  </Badge>
                </div>

                <CardHeader>
                  <div className="flex items-start justify-between gap-2">
                    <div className="flex-1 min-w-0">
                      <h3 className="font-black text-xl line-clamp-1 mb-1">
                        {program.name}
                      </h3>
                      <p className="text-sm text-muted-foreground flex items-center gap-1">
                        <Calendar className="h-3 w-3" />
                        {format(new Date(program.creationDate), 'PPP', { locale: es })}
                      </p>
                      {program.creator && (
                        <p className="text-sm text-muted-foreground flex items-center gap-1 mt-1">
                          <User className="h-3 w-3" />
                          {program.creator.name || program.creator.email}
                        </p>
                      )}
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
                    onClick={() => handleAdoptClick(program)}
                    disabled={saving && adoptingId === program.workoutId}
                  >
                    {saving && adoptingId === program.workoutId ? (
                      <>
                        <Loader2 className="h-4 w-4 animate-spin" />
                        Adoptando...
                      </>
                    ) : (
                      <>
                        <Download className="h-4 w-4" />
                        Adoptar Rutina
                      </>
                    )}
                  </Button>
                </CardFooter>
              </Card>
            ))}
          </div>
        )}
      </div>

      <AlertDialog open={confirmDialogOpen} onOpenChange={setConfirmDialogOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>¿Adoptar esta rutina?</AlertDialogTitle>
            <AlertDialogDescription>
              Se creará una copia de la rutina "{programToAdopt?.name}" en tu cuenta.
              Podrás editarla y personalizarla según tus necesidades.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setProgramToAdopt(null)}>
              Cancelar
            </AlertDialogCancel>
            <AlertDialogAction onClick={handleConfirmAdopt}>
              Adoptar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}

