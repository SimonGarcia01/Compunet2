import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { AppDispatch, RootState } from '@/store/index';
import { fetchProgramById } from '@/store/workoutPrograms/thunk';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Progress } from '@/components/ui/progress';
import { CheckCircle2, Circle, Play, Pause, ArrowLeft, Check } from 'lucide-react';
import { Skeleton } from '@/components/ui/skeleton';
import { toast } from '@/hooks/use-toast';
import { WorkoutExercise } from '@/store/workoutPrograms/workoutProgramsSlice';
import { completeExercise } from '@/store/workout/thunk';

interface ExerciseStatus {
  exerciseId: string;
  completed: boolean;
  completedAt?: Date;
}

export default function WorkoutSession() {
  const { workoutId } = useParams<{ workoutId: string }>();
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { current, loading } = useSelector((state: RootState) => state.workoutPrograms);
  const [currentExerciseIndex, setCurrentExerciseIndex] = useState(0);
  const [exerciseStatuses, setExerciseStatuses] = useState<Map<string, ExerciseStatus>>(new Map());
  const [isPaused, setIsPaused] = useState(false);
  const [sessionStartTime] = useState(new Date());

  useEffect(() => {
    if (workoutId) {
      dispatch(fetchProgramById(workoutId));
    }
  }, [workoutId, dispatch]);

  const exercises = current?.exercises || [];
  const currentExercise = exercises[currentExerciseIndex];
  const completedCount = Array.from(exerciseStatuses.values()).filter(s => s.completed).length;
  const progress = exercises.length > 0 ? (completedCount / exercises.length) * 100 : 0;

  const handleCompleteExercise = async (exercise: WorkoutExercise) => {
    if (!workoutId || !exercise.exerciseId) {
      toast({
        title: 'Error',
        description: 'Faltan datos necesarios para completar el ejercicio',
        variant: 'destructive',
      });
      return;
    }

    const workoutProgramId = parseInt(workoutId);
    const exerciseId = parseInt(exercise.exerciseId);

    if (isNaN(workoutProgramId) || isNaN(exerciseId)) {
      toast({
        title: 'Error',
        description: 'IDs inválidos',
        variant: 'destructive',
      });
      return;
    }

    try {
      await dispatch(completeExercise({
        workoutProgramId,
        exerciseId,
      })).unwrap();

      setExerciseStatuses(prev => {
        const newMap = new Map(prev);
        newMap.set(exercise.exerciseId, {
          exerciseId: exercise.exerciseId,
          completed: true,
          completedAt: new Date(),
        });
        return newMap;
      });

      toast({
        title: '¡Ejercicio completado!',
        description: `${exercise.exerciseName || 'Ejercicio'} marcado como completado`,
      });

      // Avanzar al siguiente ejercicio si no está completado
      if (currentExerciseIndex < exercises.length - 1) {
        const nextIndex = exercises.findIndex((e, idx) => 
          idx > currentExerciseIndex && !exerciseStatuses.get(e.exerciseId)?.completed
        );
        if (nextIndex !== -1) {
          setCurrentExerciseIndex(nextIndex);
        }
      }
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error.message || 'No se pudo completar el ejercicio',
        variant: 'destructive',
      });
    }
  };

  const handleNext = () => {
    if (currentExerciseIndex < exercises.length - 1) {
      setCurrentExerciseIndex(currentExerciseIndex + 1);
    }
  };

  const handlePrevious = () => {
    if (currentExerciseIndex > 0) {
      setCurrentExerciseIndex(currentExerciseIndex - 1);
    }
  };

  const handleFinishWorkout = () => {
    const allCompleted = exercises.every(e => exerciseStatuses.get(e.exerciseId)?.completed);
    
    if (allCompleted) {
      toast({
        title: '¡Rutina completada!',
        description: 'Has completado todos los ejercicios de esta rutina',
      });
      navigate('/app/user/routines');
    } else {
      toast({
        title: 'Rutina en progreso',
        description: `Has completado ${completedCount} de ${exercises.length} ejercicios`,
      });
      navigate('/app/user/routines');
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-background">
        <div className="container mx-auto px-4 py-8">
          <Skeleton className="h-96 w-full" />
        </div>
      </div>
    );
  }

  if (!current) {
    return (
      <div className="min-h-screen bg-background">
        <div className="container mx-auto px-4 py-8">
          <Card>
            <CardContent className="pt-6">
              <p className="text-center text-muted-foreground">Rutina no encontrada</p>
              <Button onClick={() => navigate('/app/user/routines')} className="mt-4">
                Volver a mis rutinas
              </Button>
            </CardContent>
          </Card>
        </div>
      </div>
    );
  }

  if (exercises.length === 0) {
    return (
      <div className="min-h-screen bg-background">
        <div className="container mx-auto px-4 py-8">
          <Card>
            <CardContent className="pt-6">
              <p className="text-center text-muted-foreground">Esta rutina no tiene ejercicios</p>
              <Button onClick={() => navigate('/app/user/routines')} className="mt-4">
                Volver a mis rutinas
              </Button>
            </CardContent>
          </Card>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8 max-w-4xl">
        {/* Header */}
        <div className="mb-6">
          <Button
            variant="ghost"
            onClick={() => navigate('/app/user/routines')}
            className="mb-4"
          >
            <ArrowLeft className="h-4 w-4 mr-2" />
            Volver
          </Button>
          
          <div className="flex items-center justify-between mb-4">
            <div>
              <h1 className="text-3xl font-black mb-2">{current.name}</h1>
              <p className="text-muted-foreground">{current.description}</p>
            </div>
            <Badge variant="secondary" className="text-lg px-4 py-2">
              {completedCount} / {exercises.length}
            </Badge>
          </div>

          <Progress value={progress} className="h-3" />
        </div>

        {/* Exercise List Sidebar */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-1">
            <Card>
              <CardHeader>
                <h3 className="font-semibold">Ejercicios</h3>
              </CardHeader>
              <CardContent className="space-y-2 max-h-[600px] overflow-y-auto">
                {exercises.map((exercise, index) => {
                  const status = exerciseStatuses.get(exercise.exerciseId);
                  const isCurrent = index === currentExerciseIndex;
                  
                  return (
                    <div
                      key={exercise.exerciseId}
                      className={`flex items-center gap-3 p-3 rounded-lg cursor-pointer transition-colors ${
                        isCurrent ? 'bg-primary/10 border-2 border-primary' : 'hover:bg-secondary'
                      }`}
                      onClick={() => setCurrentExerciseIndex(index)}
                    >
                      <div className="flex-shrink-0">
                        {status?.completed ? (
                          <CheckCircle2 className="h-5 w-5 text-green-500" />
                        ) : (
                          <Circle className="h-5 w-5 text-muted-foreground" />
                        )}
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className={`font-medium ${isCurrent ? 'text-primary' : ''}`}>
                          {index + 1}. {exercise.exerciseName || `Ejercicio ${index + 1}`}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {exercise.series} series × {exercise.amount} {exercise.exerciseType === 'cardio' ? 'min' : 'rep'}
                        </p>
                      </div>
                    </div>
                  );
                })}
              </CardContent>
            </Card>
          </div>

          {/* Current Exercise Detail */}
          <div className="lg:col-span-2">
            {currentExercise && (
              <Card className="sticky top-4">
                <CardHeader>
                  <div className="flex items-center justify-between">
                    <div>
                      <h2 className="text-2xl font-black mb-2">
                        {currentExercise.exerciseName || `Ejercicio ${currentExerciseIndex + 1}`}
                      </h2>
                      <div className="flex gap-2 flex-wrap">
                        <Badge variant="outline">
                          {currentExercise.series} series
                        </Badge>
                        <Badge variant="outline">
                          {currentExercise.amount} {currentExercise.exerciseType === 'cardio' ? 'minutos' : 'repeticiones'}
                        </Badge>
                        {currentExercise.exerciseType && (
                          <Badge variant="secondary">
                            {currentExercise.exerciseType}
                          </Badge>
                        )}
                      </div>
                    </div>
                    {exerciseStatuses.get(currentExercise.exerciseId)?.completed && (
                      <CheckCircle2 className="h-8 w-8 text-green-500" />
                    )}
                  </div>
                </CardHeader>
                <CardContent className="space-y-6">
                  {currentExercise.notes && (
                    <div>
                      <h4 className="font-semibold mb-2">Notas</h4>
                      <p className="text-muted-foreground">{currentExercise.notes}</p>
                    </div>
                  )}

                  <div className="flex gap-4">
                    <Button
                      variant="outline"
                      onClick={handlePrevious}
                      disabled={currentExerciseIndex === 0}
                      className="flex-1"
                    >
                      Anterior
                    </Button>
                    <Button
                      onClick={() => handleCompleteExercise(currentExercise)}
                      disabled={exerciseStatuses.get(currentExercise.exerciseId)?.completed}
                      className="flex-1"
                    >
                      {exerciseStatuses.get(currentExercise.exerciseId)?.completed ? (
                        <>
                          <CheckCircle2 className="h-4 w-4 mr-2" />
                          Completado
                        </>
                      ) : (
                        <>
                          <Check className="h-4 w-4 mr-2" />
                          Marcar como completado
                        </>
                      )}
                    </Button>
                    <Button
                      variant="outline"
                      onClick={handleNext}
                      disabled={currentExerciseIndex === exercises.length - 1}
                      className="flex-1"
                    >
                      Siguiente
                    </Button>
                  </div>

                  {completedCount === exercises.length && (
                    <Button
                      onClick={handleFinishWorkout}
                      size="lg"
                      className="w-full"
                    >
                      Finalizar rutina
                    </Button>
                  )}
                </CardContent>
              </Card>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

