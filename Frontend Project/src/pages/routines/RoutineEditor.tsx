import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { AppDispatch, RootState } from '@/store/index';
import { fetchProgramById, createProgram, updateProgram } from '@/store/workoutPrograms/thunk';
import { setCurrentProgram, clearCurrentProgram } from '@/store/workoutPrograms/workoutProgramsSlice';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Label } from '@/components/ui/label';
import { Separator } from '@/components/ui/separator';
import { toast } from '@/hooks/use-toast';
import { ArrowLeft, Save, Loader2 } from 'lucide-react';
import { RoutineItemsBuilder } from '@/components/routines/RoutineItemsBuilder';
import { ExercisePicker } from '@/components/routines/ExercisePicker';

export default function RoutineEditor() {
  const { id } = useParams<{ id: string }>();
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { current, saving } = useSelector((state: RootState) => state.workoutPrograms);
  const user = useSelector((state: RootState) => state.auth.user);

  const [formData, setFormData] = useState({
    name: '',
    description: '',
    photoUrl: '',
  });

  const [errors, setErrors] = useState<Record<string, string>>({});
  const isEditing = !!id;

  useEffect(() => {
    if (id) {
      dispatch(fetchProgramById(id));
    } else {
      dispatch(setCurrentProgram({
        workoutId: '',
        name: '',
        description: '',
        photoUrl: '',
        creationDate: new Date().toISOString(),
        completed: false,
        userId: user?.email || '',
        exercises: [],
      }));
    }

    return () => {
      dispatch(clearCurrentProgram());
    };
  }, [id, dispatch, user]);

  useEffect(() => {
    if (current) {
      // Solo actualizar formData si los valores son diferentes y no están vacíos
      // Esto evita que se borren los datos cuando se agregan ejercicios
      setFormData(prev => ({
        name: current.name && current.name !== prev.name ? current.name : prev.name,
        description: current.description && current.description !== prev.description ? current.description : prev.description,
        photoUrl: current.photoUrl || prev.photoUrl || '',
      }));
    }
  }, [current?.name, current?.description, current?.photoUrl]);

  const validate = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'El nombre es requerido';
    }
    if (!formData.description.trim()) {
      newErrors.description = 'La descripción es requerida';
    }
    if (!current?.exercises || current.exercises.length === 0) {
      newErrors.exercises = 'Debes agregar al menos un ejercicio';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSave = async (markCompleted = false) => {
    if (!validate()) {
      toast({
        title: 'Formulario incompleto',
        description: 'Por favor completa todos los campos requeridos',
        variant: 'destructive',
      });
      return;
    }

    if (!current) return;

    const programData = {
      ...current,
      name: formData.name, // Usar formData en lugar de current para asegurar que tenga los valores actuales
      description: formData.description,
      photoUrl: formData.photoUrl,
      completed: markCompleted,
      userId: user?.email || '',
      creationDate: current.creationDate || new Date().toISOString(),
      exercises: current.exercises || [], // Asegurar que los ejercicios se incluyan
    };

    try {
      if (isEditing) {
        await dispatch(updateProgram(programData)).unwrap();
        toast({
          title: 'Rutina actualizada',
          description: 'Los cambios han sido guardados exitosamente',
        });
      } else {
        await dispatch(createProgram(programData)).unwrap();
        toast({
          title: 'Rutina creada',
          description: 'Tu rutina ha sido creada exitosamente',
        });
      }
      navigate('/app/user/routines');
    } catch (error: any) {
      console.error('Error al guardar rutina:', error);
      const errorMessage = typeof error === 'string' 
        ? error 
        : error?.message || error?.error || 'No se pudo guardar la rutina';
      toast({
        title: 'Error',
        description: errorMessage,
        variant: 'destructive',
      });
    }
  };

  if (!current) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <Loader2 className="h-8 w-8 animate-spin text-primary" />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8 max-w-7xl">
        {/* Header */}
        <div className="flex items-center gap-4 mb-8">
          <Button
            variant="ghost"
            size="icon"
            onClick={() => navigate('/app/user/routines')}
          >
            <ArrowLeft className="h-5 w-5" />
          </Button>
          <div className="flex-1">
            <h1 className="text-4xl font-black">
              {isEditing ? 'Editar rutina' : 'Nueva rutina'}
            </h1>
            <p className="text-muted-foreground">
              {isEditing ? 'Modifica tu programa de entrenamiento' : 'Crea tu programa de entrenamiento'}
            </p>
          </div>
          <div className="flex gap-2">
            <Button
              variant="outline"
              onClick={() => handleSave(false)}
              disabled={saving}
            >
              {saving && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Guardar borrador
            </Button>
            <Button onClick={() => handleSave(true)} disabled={saving}>
              {saving && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              <Save className="mr-2 h-4 w-4" />
              Guardar y completar
            </Button>
          </div>
        </div>

        {/* Información básica */}
        <div className="bg-muted/30 p-6 rounded-lg mb-6 space-y-4">
          <div>
            <Label htmlFor="name">
              Nombre de la rutina <span className="text-destructive">*</span>
            </Label>
            <Input
              id="name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              placeholder="Ej: Rutina de fuerza para principiantes"
              aria-invalid={!!errors.name}
            />
            {errors.name && (
              <p className="text-sm text-destructive mt-1">{errors.name}</p>
            )}
          </div>

          <div>
            <Label htmlFor="description">
              Descripción <span className="text-destructive">*</span>
            </Label>
            <Textarea
              id="description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              placeholder="Describe los objetivos y características de esta rutina..."
              rows={3}
              aria-invalid={!!errors.description}
            />
            {errors.description && (
              <p className="text-sm text-destructive mt-1">{errors.description}</p>
            )}
          </div>

          <div>
            <Label htmlFor="photoUrl">URL de la imagen (opcional)</Label>
            <Input
              id="photoUrl"
              type="url"
              value={formData.photoUrl}
              onChange={(e) => setFormData({ ...formData, photoUrl: e.target.value })}
              placeholder="https://ejemplo.com/imagen.jpg"
            />
          </div>
        </div>

        <Separator className="my-8" />

        {/* Constructor de ejercicios */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Catálogo (izquierda) */}
          <div className="lg:col-span-1">
            <div className="sticky top-4">
              <h2 className="text-2xl font-bold mb-4">Catálogo de ejercicios</h2>
              <p className="text-sm text-muted-foreground mb-4">
                Explora y agrega ejercicios a tu rutina
              </p>
              <ExercisePicker />
            </div>
          </div>

          {/* Constructor (derecha) */}
          <div className="lg:col-span-2">
            <h2 className="text-2xl font-bold mb-4">Constructor de rutina</h2>
            <p className="text-sm text-muted-foreground mb-4">
              Arrastra para reordenar y personaliza cada ejercicio
            </p>
            {errors.exercises && (
              <div className="bg-destructive/10 border border-destructive/20 text-destructive px-4 py-3 rounded-lg mb-4">
                {errors.exercises}
              </div>
            )}
            <RoutineItemsBuilder />
          </div>
        </div>
      </div>
    </div>
  );
}
