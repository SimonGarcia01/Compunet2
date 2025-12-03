import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { fetchExercises } from '@/store/exercises/thunk';
import { addExerciseToProgram } from '../../store/workoutPrograms/workoutProgramsSlice';
import { Exercise } from '../../store/exercises/exercisesSlice';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Plus, Play } from 'lucide-react';
import { Skeleton } from '@/components/ui/skeleton';
import { toast } from '@/hooks/use-toast';

export function ExercisePicker() {
  const dispatch = useDispatch<AppDispatch>();
  const { filteredItems, loading } = useSelector((state: RootState) => state.exercises);

  useEffect(() => {
    dispatch(fetchExercises());
  }, [dispatch]);

  const handleAddExercise = (exercise: Exercise) => {
    const defaultValues = {
      exerciseId: exercise.exerciseId,
      exerciseName: exercise.name,
      exerciseType: exercise.type,
      series: exercise.type === 'fuerza' ? 3 : 1,
      amount: exercise.progressUnit === 'reps' ? 10 : exercise.progressUnit === 'min' ? 5 : 1,
      session: 1,
      notes: '',
      rpe: 5,
    };

    dispatch(addExerciseToProgram(defaultValues));
    
    toast({
      title: 'Ejercicio agregado',
      description: `${exercise.name} ha sido añadido a tu rutina`,
    });
  };

  const typeColors = {
    cardio: 'bg-red-500/10 text-red-500',
    fuerza: 'bg-blue-500/10 text-blue-500',
    movilidad: 'bg-green-500/10 text-green-500',
  };

  return (
    <ScrollArea className="h-[600px] pr-4">
      <div className="space-y-3">
        {loading ? (
          <>
            {[...Array(5)].map((_, i) => (
              <Skeleton key={i} className="h-32 rounded-lg" />
            ))}
          </>
        ) : filteredItems.length === 0 ? (
          <div className="text-center py-8 text-muted-foreground">
            <p>No hay ejercicios disponibles</p>
          </div>
        ) : (
          filteredItems.map((exercise) => (
            <div
              key={exercise.exerciseId}
              className="bg-card border-2 border-border hover:border-primary/50 rounded-lg p-4 transition-all duration-200 group"
            >
              <div className="flex items-start gap-3">
                {/* Thumbnail o ícono */}
                <div className="w-16 h-16 rounded-md bg-muted flex items-center justify-center shrink-0 overflow-hidden">
                  {exercise.videoUrl ? (
                    <div className="relative w-full h-full">
                      <Play className="absolute inset-0 m-auto h-6 w-6 text-primary z-10" />
                      <div className="w-full h-full bg-gradient-to-br from-primary/20 to-primary/5" />
                    </div>
                  ) : (
                    <div className="text-muted-foreground text-xs">Sin video</div>
                  )}
                </div>

                <div className="flex-1 min-w-0">
                  <h4 className="font-bold text-sm line-clamp-1 mb-1">
                    {exercise.name}
                  </h4>
                  <p className="text-xs text-muted-foreground line-clamp-2 mb-2">
                    {exercise.description}
                  </p>
                  <div className="flex flex-wrap gap-1">
                    <Badge variant="secondary" className={`text-xs ${typeColors[exercise.type]}`}>
                      {exercise.type}
                    </Badge>
                    <Badge variant="outline" className="text-xs">
                      {exercise.progressUnit}
                    </Badge>
                  </div>
                </div>
              </div>

              <Button
                size="sm"
                className="w-full mt-3 gap-2"
                onClick={() => handleAddExercise(exercise)}
              >
                <Plus className="h-3 w-3" />
                Agregar
              </Button>
            </div>
          ))
        )}
      </div>
    </ScrollArea>
  );
}
