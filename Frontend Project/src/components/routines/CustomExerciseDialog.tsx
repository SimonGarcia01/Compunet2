import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/store/index';
import { createExercise } from '@/store/exercises/thunk';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { toast } from '@/hooks/use-toast';
import { Loader2 } from 'lucide-react';

interface CustomExerciseDialogProps {
  open: boolean;
  onClose: () => void;
}

export function CustomExerciseDialog({ open, onClose }: CustomExerciseDialogProps) {
  const dispatch = useDispatch<AppDispatch>();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    type: 'fuerza' as 'cardio' | 'fuerza' | 'movilidad',
    description: '',
    difficulty: 'media' as 'baja' | 'media' | 'alta',
    videoUrl: '',
    progressUnit: 'reps' as 'reps' | 'min' | 'km',
    estimatedUnitaryCaloriesBurnt: 0,
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const validate = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'El nombre es requerido';
    }
    if (!formData.description.trim()) {
      newErrors.description = 'La descripción es requerida';
    }
    if (formData.estimatedUnitaryCaloriesBurnt < 0) {
      newErrors.estimatedUnitaryCaloriesBurnt = 'Las calorías no pueden ser negativas';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validate()) return;

    setLoading(true);
    try {
      await dispatch(createExercise({ ...formData, isCustom: true })).unwrap();
      
      toast({
        title: 'Ejercicio creado',
        description: 'Tu ejercicio personalizado ha sido creado exitosamente',
      });
      
      handleClose();
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error || 'No se pudo crear el ejercicio',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setFormData({
      name: '',
      type: 'fuerza',
      description: '',
      difficulty: 'media',
      videoUrl: '',
      progressUnit: 'reps',
      estimatedUnitaryCaloriesBurnt: 0,
    });
    setErrors({});
    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="max-w-2xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>Nuevo ejercicio personalizado</DialogTitle>
          <DialogDescription>
            Crea un ejercicio personalizado para tus rutinas
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <Label htmlFor="name">
              Nombre <span className="text-destructive">*</span>
            </Label>
            <Input
              id="name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              placeholder="Ej: Sentadilla búlgara"
              aria-invalid={!!errors.name}
              aria-describedby={errors.name ? 'name-error' : undefined}
            />
            {errors.name && (
              <p id="name-error" className="text-sm text-destructive mt-1">
                {errors.name}
              </p>
            )}
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <Label htmlFor="type">
                Tipo <span className="text-destructive">*</span>
              </Label>
              <Select
                value={formData.type}
                onValueChange={(value: any) => setFormData({ ...formData, type: value })}
              >
                <SelectTrigger id="type">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="cardio">Cardio</SelectItem>
                  <SelectItem value="fuerza">Fuerza</SelectItem>
                  <SelectItem value="movilidad">Movilidad</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label htmlFor="difficulty">
                Dificultad <span className="text-destructive">*</span>
              </Label>
              <Select
                value={formData.difficulty}
                onValueChange={(value: any) => setFormData({ ...formData, difficulty: value })}
              >
                <SelectTrigger id="difficulty">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="baja">Baja</SelectItem>
                  <SelectItem value="media">Media</SelectItem>
                  <SelectItem value="alta">Alta</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>

          <div>
            <Label htmlFor="description">
              Descripción <span className="text-destructive">*</span>
            </Label>
            <Textarea
              id="description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              placeholder="Describe el ejercicio, técnica, músculos involucrados..."
              rows={4}
              aria-invalid={!!errors.description}
              aria-describedby={errors.description ? 'description-error' : undefined}
            />
            {errors.description && (
              <p id="description-error" className="text-sm text-destructive mt-1">
                {errors.description}
              </p>
            )}
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <Label htmlFor="progressUnit">
                Unidad de progreso <span className="text-destructive">*</span>
              </Label>
              <Select
                value={formData.progressUnit}
                onValueChange={(value: any) => setFormData({ ...formData, progressUnit: value })}
              >
                <SelectTrigger id="progressUnit">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="reps">Repeticiones</SelectItem>
                  <SelectItem value="min">Minutos</SelectItem>
                  <SelectItem value="km">Kilómetros</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label htmlFor="calories">Calorías estimadas (por unidad)</Label>
              <Input
                id="calories"
                type="number"
                min="0"
                step="0.1"
                value={formData.estimatedUnitaryCaloriesBurnt}
                onChange={(e) => setFormData({ ...formData, estimatedUnitaryCaloriesBurnt: parseFloat(e.target.value) || 0 })}
                placeholder="0"
                aria-invalid={!!errors.estimatedUnitaryCaloriesBurnt}
              />
              {errors.estimatedUnitaryCaloriesBurnt && (
                <p className="text-sm text-destructive mt-1">
                  {errors.estimatedUnitaryCaloriesBurnt}
                </p>
              )}
            </div>
          </div>

          <div>
            <Label htmlFor="videoUrl">URL del video (opcional)</Label>
            <Input
              id="videoUrl"
              type="url"
              value={formData.videoUrl}
              onChange={(e) => setFormData({ ...formData, videoUrl: e.target.value })}
              placeholder="https://youtube.com/watch?v=..."
            />
            <p className="text-xs text-muted-foreground mt-1">
              Soporta YouTube y Vimeo
            </p>
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={handleClose} disabled={loading}>
              Cancelar
            </Button>
            <Button type="submit" disabled={loading}>
              {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Crear ejercicio
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
