import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import {
  updateExerciseInProgram,
  removeExerciseFromProgram,
  reorderExercises,
} from '../../store/workoutPrograms/workoutProgramsSlice';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Badge } from '@/components/ui/badge';
import { GripVertical, Trash2, Play, ChevronDown, ChevronUp } from 'lucide-react';
import { Separator } from '@/components/ui/separator';

export function RoutineItemsBuilder() {
  const dispatch = useDispatch<AppDispatch>();
  const current = useSelector((state: RootState) => state.workoutPrograms.current);
  const [expandedIndex, setExpandedIndex] = useState<number | null>(null);

  if (!current || !current.exercises) {
    return (
      <div className="text-center py-20 bg-muted/30 rounded-lg">
        <p className="text-muted-foreground">
          Aún no has agregado ejercicios. <br />
          Usa el catálogo de la izquierda para comenzar.
        </p>
      </div>
    );
  }

  const exercises = current.exercises;

  const handleUpdate = (index: number, field: string, value: any) => {
    const updated = { ...exercises[index], [field]: value };
    dispatch(updateExerciseInProgram({ index, exercise: updated }));
  };

  const handleRemove = (index: number) => {
    dispatch(removeExerciseFromProgram(index));
  };

  const handleMoveUp = (index: number) => {
    if (index === 0) return;
    const newExercises = [...exercises];
    [newExercises[index - 1], newExercises[index]] = [newExercises[index], newExercises[index - 1]];
    dispatch(reorderExercises(newExercises));
  };

  const handleMoveDown = (index: number) => {
    if (index === exercises.length - 1) return;
    const newExercises = [...exercises];
    [newExercises[index], newExercises[index + 1]] = [newExercises[index + 1], newExercises[index]];
    dispatch(reorderExercises(newExercises));
  };

  const typeColors = {
    cardio: 'bg-red-500/10 text-red-500',
    fuerza: 'bg-blue-500/10 text-blue-500',
    movilidad: 'bg-green-500/10 text-green-500',
  };

  const totalExercises = exercises.length;
  const totalSeries = exercises.reduce((sum, ex) => sum + (ex.series || 0), 0);

  return (
    <div className="space-y-4">
      {/* Resumen */}
      <div className="bg-primary/5 border border-primary/20 rounded-lg p-4 flex items-center justify-between">
        <div>
          <p className="text-sm text-muted-foreground">Total de ejercicios</p>
          <p className="text-2xl font-black">{totalExercises}</p>
        </div>
        <div>
          <p className="text-sm text-muted-foreground">Series totales</p>
          <p className="text-2xl font-black">{totalSeries}</p>
        </div>
      </div>

      {/* Lista de ejercicios */}
      <div className="space-y-3">
        {exercises.map((item, index) => (
          <Card key={index} className="overflow-hidden border-2">
            <CardContent className="p-4">
              {/* Header del ejercicio */}
              <div className="flex items-start gap-3 mb-3">
                <div className="flex flex-col gap-1 shrink-0">
                  <Button
                    size="icon"
                    variant="ghost"
                    className="h-6 w-6 cursor-grab active:cursor-grabbing"
                  >
                    <GripVertical className="h-4 w-4" />
                  </Button>
                  <div className="text-xs font-bold text-center text-muted-foreground">
                    #{index + 1}
                  </div>
                </div>

                <div className="flex-1 min-w-0">
                  <div className="flex items-start justify-between gap-2 mb-2">
                    <div className="flex-1">
                      <h4 className="font-bold text-base line-clamp-1">
                        {item.exerciseName || 'Ejercicio sin nombre'}
                      </h4>
                      {item.exerciseType && (
                        <Badge
                          variant="secondary"
                          className={`text-xs mt-1 ${typeColors[item.exerciseType as keyof typeof typeColors]}`}
                        >
                          {item.exerciseType}
                        </Badge>
                      )}
                    </div>

                    <div className="flex gap-1 shrink-0">
                      <Button
                        size="icon"
                        variant="ghost"
                        className="h-8 w-8"
                        onClick={() => setExpandedIndex(expandedIndex === index ? null : index)}
                      >
                        {expandedIndex === index ? (
                          <ChevronUp className="h-4 w-4" />
                        ) : (
                          <ChevronDown className="h-4 w-4" />
                        )}
                      </Button>
                      <Button
                        size="icon"
                        variant="ghost"
                        className="h-8 w-8 text-destructive hover:text-destructive"
                        onClick={() => handleRemove(index)}
                      >
                        <Trash2 className="h-4 w-4" />
                      </Button>
                    </div>
                  </div>

                  {/* Campos inline (siempre visibles) */}
                  <div className="grid grid-cols-3 gap-2">
                    <div>
                      <Label className="text-xs">Series</Label>
                      <Input
                        type="number"
                        min="1"
                        value={item.series}
                        onChange={(e) => handleUpdate(index, 'series', parseInt(e.target.value) || 1)}
                        className="h-8 text-sm"
                      />
                    </div>
                    <div>
                      <Label className="text-xs">Cantidad</Label>
                      <Input
                        type="number"
                        min="1"
                        value={item.amount}
                        onChange={(e) => handleUpdate(index, 'amount', parseInt(e.target.value) || 1)}
                        className="h-8 text-sm"
                      />
                    </div>
                    <div>
                      <Label className="text-xs">Sesión</Label>
                      <Input
                        type="number"
                        min="1"
                        value={item.session}
                        onChange={(e) => handleUpdate(index, 'session', parseInt(e.target.value) || 1)}
                        className="h-8 text-sm"
                      />
                    </div>
                  </div>
                </div>
              </div>

              {/* Campos expandibles */}
              {expandedIndex === index && (
                <>
                  <Separator className="my-3" />
                  <div className="space-y-3 pl-9">
                    <div>
                      <Label className="text-xs">RPE (Esfuerzo percibido: 1-10)</Label>
                      <Input
                        type="number"
                        min="1"
                        max="10"
                        value={item.rpe || 5}
                        onChange={(e) => handleUpdate(index, 'rpe', parseInt(e.target.value) || 5)}
                        className="h-8 text-sm"
                      />
                    </div>
                    <div>
                      <Label className="text-xs">Notas (opcional)</Label>
                      <Textarea
                        value={item.notes || ''}
                        onChange={(e) => handleUpdate(index, 'notes', e.target.value)}
                        placeholder="Ej: Enfocarse en la forma, usar peso moderado..."
                        rows={2}
                        className="text-sm"
                      />
                    </div>
                    <div className="flex gap-2">
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleMoveUp(index)}
                        disabled={index === 0}
                      >
                        ↑ Subir
                      </Button>
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleMoveDown(index)}
                        disabled={index === exercises.length - 1}
                      >
                        ↓ Bajar
                      </Button>
                    </div>
                  </div>
                </>
              )}
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}