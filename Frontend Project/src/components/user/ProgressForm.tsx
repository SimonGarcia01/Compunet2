import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { createProgress, updateProgress } from '@/store/userProgress/thunk';
import { fetchMyPrograms } from '@/store/workoutPrograms/thunk';
import { fetchExercises } from '@/store/exercises/thunk';
import type { ProgressFormData, ExerciseProgress } from '@/store/userProgress/userProgressSlice';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Calendar } from '@/components/ui/calendar';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { CalendarIcon, Loader2 } from 'lucide-react';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { toast } from 'sonner';
import { cn } from '@/lib/utils';

interface ProgressFormProps {
  open: boolean;
  onClose: () => void;
  progressToEdit?: ExerciseProgress | null;
}

export function ProgressForm({ open, onClose, progressToEdit }: ProgressFormProps) {
  const dispatch = useDispatch<AppDispatch>();
  const { saving } = useSelector((state: RootState) => state.userProgress);
  const { items: workouts } = useSelector((state: RootState) => state.workoutPrograms);
  const { items: exercises } = useSelector((state: RootState) => state.exercises);

  const [formData, setFormData] = useState<ProgressFormData>({
    workoutId: '',
    exerciseId: '',
    recordDate: new Date().toISOString().split('T')[0],
    periodType: 'DAILY',
    repetitions: undefined,
    timeMinutes: undefined,
    distanceKm: undefined,
    rpe: undefined,
    notes: '',
    estimatedCaloriesBurnt: undefined,
  });

  const [datePickerOpen, setDatePickerOpen] = useState(false);
  const [errors, setErrors] = useState<Record<string, string>>({});

  const isEditing = !!progressToEdit;

  // Cargar datos cuando se abre el diálogo
  useEffect(() => {
    if (open) {
      dispatch(fetchMyPrograms());
      dispatch(fetchExercises());
    }
  }, [open, dispatch]);

  // Cargar datos del progreso a editar
  useEffect(() => {
    if (progressToEdit && open) {
      setFormData({
        workoutId: progressToEdit.workoutProgram?.workoutId || '',
        exerciseId: progressToEdit.exercise?.exerciseId || '',
        recordDate: progressToEdit.recordDate,
        periodType: progressToEdit.periodType,
        repetitions: progressToEdit.repetitions,
        timeMinutes: progressToEdit.timeMinutes,
        distanceKm: progressToEdit.distanceKm,
        rpe: progressToEdit.rpe,
        notes: progressToEdit.notes || '',
        estimatedCaloriesBurnt: progressToEdit.estimatedCaloriesBurnt,
      });
    } else if (!progressToEdit && open) {
      // Reset form when creating new
      setFormData({
        workoutId: '',
        exerciseId: '',
        recordDate: new Date().toISOString().split('T')[0],
        periodType: 'DAILY',
        repetitions: undefined,
        timeMinutes: undefined,
        distanceKm: undefined,
        rpe: undefined,
        notes: '',
        estimatedCaloriesBurnt: undefined,
      });
    }
    setErrors({});
  }, [progressToEdit, open]);

  const validate = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.recordDate) {
      newErrors.recordDate = 'La fecha es requerida';
    }

    if (!formData.periodType) {
      newErrors.periodType = 'El tipo de período es requerido';
    }

    // Al menos uno de estos campos debe estar presente
    if (
      formData.repetitions === undefined &&
      formData.timeMinutes === undefined &&
      formData.distanceKm === undefined
    ) {
      newErrors.metrics = 'Debes registrar al menos repeticiones, tiempo o distancia';
    }

    if (formData.rpe !== undefined && (formData.rpe < 1 || formData.rpe > 10)) {
      newErrors.rpe = 'El RPE debe estar entre 1 y 10';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    try {
      if (isEditing && progressToEdit?.progressId) {
        await dispatch(
          updateProgress({
            progressId: progressToEdit.progressId,
            formData,
          })
        ).unwrap();
        toast.success('Progreso actualizado exitosamente');
      } else {
        await dispatch(createProgress(formData)).unwrap();
        toast.success('Progreso registrado exitosamente');
      }
      onClose();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : 'Error al guardar el progreso');
    }
  };

  const selectedDate = formData.recordDate ? new Date(formData.recordDate) : new Date();

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[600px] max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{isEditing ? 'Editar Progreso' : 'Registrar Progreso'}</DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Fecha */}
          <div className="space-y-2">
            <Label htmlFor="recordDate">Fecha *</Label>
            <Popover open={datePickerOpen} onOpenChange={setDatePickerOpen}>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={cn(
                    'w-full justify-start text-left font-normal',
                    !formData.recordDate && 'text-muted-foreground'
                  )}
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {formData.recordDate ? (
                    format(selectedDate, 'PPP', { locale: es })
                  ) : (
                    <span>Selecciona una fecha</span>
                  )}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0" align="start">
                <Calendar
                  mode="single"
                  selected={selectedDate}
                  onSelect={(date) => {
                    if (date) {
                      setFormData({ ...formData, recordDate: date.toISOString().split('T')[0] });
                      setDatePickerOpen(false);
                    }
                  }}
                  disabled={(date) => date > new Date()}
                  initialFocus
                />
              </PopoverContent>
            </Popover>
            {errors.recordDate && (
              <p className="text-sm text-destructive">{errors.recordDate}</p>
            )}
          </div>

          {/* Tipo de período */}
          <div className="space-y-2">
            <Label htmlFor="periodType">Tipo de Período *</Label>
            <Select
              value={formData.periodType}
              onValueChange={(value: 'DAILY' | 'WEEKLY') =>
                setFormData({ ...formData, periodType: value })
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Selecciona el tipo de período" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="DAILY">Diario</SelectItem>
                <SelectItem value="WEEKLY">Semanal</SelectItem>
              </SelectContent>
            </Select>
            {errors.periodType && (
              <p className="text-sm text-destructive">{errors.periodType}</p>
            )}
          </div>

          {/* Rutina (opcional) */}
          <div className="space-y-2">
            <Label htmlFor="workoutId">Rutina (opcional)</Label>
            <Select
              value={formData.workoutId}
              onValueChange={(value) => setFormData({ ...formData, workoutId: value })}
            >
              <SelectTrigger>
                <SelectValue placeholder="Selecciona una rutina" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">Ninguna</SelectItem>
                {workouts.map((workout) => (
                  <SelectItem key={workout.workoutId} value={workout.workoutId}>
                    {workout.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* Ejercicio (opcional) */}
          <div className="space-y-2">
            <Label htmlFor="exerciseId">Ejercicio (opcional)</Label>
            <Select
              value={formData.exerciseId}
              onValueChange={(value) => setFormData({ ...formData, exerciseId: value })}
            >
              <SelectTrigger>
                <SelectValue placeholder="Selecciona un ejercicio" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">Ninguno</SelectItem>
                {exercises.map((exercise) => (
                  <SelectItem key={exercise.exerciseId} value={exercise.exerciseId}>
                    {exercise.name} ({exercise.type})
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* Métricas - Grid */}
          <div className="grid grid-cols-2 gap-4">
            {/* Repeticiones */}
            <div className="space-y-2">
              <Label htmlFor="repetitions">Repeticiones</Label>
              <Input
                id="repetitions"
                type="number"
                min="0"
                value={formData.repetitions || ''}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    repetitions: e.target.value ? parseInt(e.target.value, 10) : undefined,
                  })
                }
                placeholder="0"
              />
            </div>

            {/* Tiempo (minutos) */}
            <div className="space-y-2">
              <Label htmlFor="timeMinutes">Tiempo (minutos)</Label>
              <Input
                id="timeMinutes"
                type="number"
                min="0"
                value={formData.timeMinutes || ''}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    timeMinutes: e.target.value ? parseInt(e.target.value, 10) : undefined,
                  })
                }
                placeholder="0"
              />
            </div>

            {/* Distancia (km) */}
            <div className="space-y-2">
              <Label htmlFor="distanceKm">Distancia (km)</Label>
              <Input
                id="distanceKm"
                type="number"
                min="0"
                step="0.01"
                value={formData.distanceKm || ''}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    distanceKm: e.target.value ? parseFloat(e.target.value) : undefined,
                  })
                }
                placeholder="0.00"
              />
            </div>

            {/* RPE */}
            <div className="space-y-2">
              <Label htmlFor="rpe">RPE (1-10)</Label>
              <Input
                id="rpe"
                type="number"
                min="1"
                max="10"
                value={formData.rpe || ''}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    rpe: e.target.value ? parseInt(e.target.value, 10) : undefined,
                  })
                }
                placeholder="1-10"
              />
            </div>
          </div>
          {errors.metrics && (
            <p className="text-sm text-destructive">{errors.metrics}</p>
          )}
          {errors.rpe && (
            <p className="text-sm text-destructive">{errors.rpe}</p>
          )}

          {/* Calorías estimadas */}
          <div className="space-y-2">
            <Label htmlFor="estimatedCaloriesBurnt">Calorías Estimadas</Label>
            <Input
              id="estimatedCaloriesBurnt"
              type="number"
              min="0"
              step="0.01"
              value={formData.estimatedCaloriesBurnt || ''}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  estimatedCaloriesBurnt: e.target.value ? parseFloat(e.target.value) : undefined,
                })
              }
              placeholder="0.00"
            />
          </div>

          {/* Notas */}
          <div className="space-y-2">
            <Label htmlFor="notes">Notas</Label>
            <Textarea
              id="notes"
              value={formData.notes}
              onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
              placeholder="Agrega notas adicionales sobre tu entrenamiento..."
              rows={3}
              maxLength={500}
            />
            <p className="text-xs text-muted-foreground">
              {formData.notes.length}/500 caracteres
            </p>
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={onClose} disabled={saving}>
              Cancelar
            </Button>
            <Button type="submit" disabled={saving}>
              {saving && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              {isEditing ? 'Actualizar' : 'Registrar'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

