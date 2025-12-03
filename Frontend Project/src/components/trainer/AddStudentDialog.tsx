import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { createTrainerTrainee, fetchMyStudents, fetchAllStudents } from '@/store/trainer/thunk';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Label } from '@/components/ui/label';
import { Calendar } from '@/components/ui/calendar';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { CalendarIcon, Loader2 } from 'lucide-react';
import { toast } from '@/hooks/use-toast';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

interface AddStudentDialogProps {
  open: boolean;
  onClose: () => void;
}

export function AddStudentDialog({ open, onClose }: AddStudentDialogProps) {
  const dispatch = useDispatch<AppDispatch>();
  const { availableStudents, availableStudentsLoading, students } = useSelector((state: RootState) => state.trainer);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    traineeEmail: '',
    startDate: undefined as Date | undefined,
    endDate: undefined as Date | undefined,
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  // Cargar estudiantes disponibles cuando se abre el diálogo
  useEffect(() => {
    if (open) {
      dispatch(fetchAllStudents());
    }
  }, [open, dispatch]);

  // Filtrar estudiantes que ya están asignados
  const assignedStudentEmails = new Set(students.map(s => s.email));
  const availableOptions = availableStudents.filter(
    student => !assignedStudentEmails.has(student.email)
  );

  const validate = () => {
    const newErrors: Record<string, string> = {};

    if (!formData.traineeEmail) {
      newErrors.traineeEmail = 'Debes seleccionar un estudiante';
    }

    if (!formData.startDate) {
      newErrors.startDate = 'La fecha de inicio es requerida';
    }

    if (formData.endDate && formData.startDate && formData.endDate < formData.startDate) {
      newErrors.endDate = 'La fecha de fin debe ser posterior a la fecha de inicio';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setLoading(true);

    try {
      const startDateStr = formData.startDate ? format(formData.startDate, 'yyyy-MM-dd') : '';
      const endDateStr = formData.endDate ? format(formData.endDate, 'yyyy-MM-dd') : undefined;

      const selectedStudent = availableStudents.find(s => s.email === formData.traineeEmail);
      const studentName = selectedStudent?.name || formData.traineeEmail;

      const result = await dispatch(
        createTrainerTrainee({
          traineeEmail: formData.traineeEmail,
          startDate: startDateStr,
          endDate: endDateStr,
        })
      ).unwrap();

      // Refrescar la lista de estudiantes
      await dispatch(fetchMyStudents());

      toast({
        title: 'Estudiante agregado',
        description: `${studentName} ha sido agregado exitosamente.`,
      });

      // Limpiar formulario y cerrar
      setFormData({
        traineeEmail: '',
        startDate: undefined,
        endDate: undefined,
      });
      setErrors({});
      onClose();
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error || 'No se pudo agregar el estudiante. Verifica que tengas permisos de Administrador.',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    if (!loading) {
      setFormData({
        traineeEmail: '',
        startDate: undefined,
        endDate: undefined,
      });
      setErrors({});
      onClose();
    }
  };

  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>Agregar Estudiante</DialogTitle>
          <DialogDescription>
            Matricula un nuevo estudiante asignándolo a tu cuenta de entrenador.
            <br />
            <span className="text-xs text-muted-foreground mt-2 block">
              Nota: Requiere permisos de Administrador.
            </span>
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="traineeEmail">
              Estudiante <span className="text-destructive">*</span>
            </Label>
            {availableStudentsLoading ? (
              <div className="flex items-center justify-center py-8">
                <Loader2 className="h-5 w-5 animate-spin text-muted-foreground" />
                <span className="ml-2 text-sm text-muted-foreground">Cargando estudiantes...</span>
              </div>
            ) : availableOptions.length === 0 ? (
              <div className="p-4 border rounded-md bg-muted/50">
                <p className="text-sm text-muted-foreground text-center">
                  {availableStudents.length === 0
                    ? 'No se pudieron cargar los estudiantes. Verifica tus permisos.'
                    : 'Todos los estudiantes disponibles ya están asignados.'}
                </p>
              </div>
            ) : (
              <Select
                value={formData.traineeEmail}
                onValueChange={(value) =>
                  setFormData({ ...formData, traineeEmail: value })
                }
                disabled={loading}
              >
                <SelectTrigger
                  id="traineeEmail"
                  className={errors.traineeEmail ? 'border-destructive' : ''}
                >
                  <SelectValue placeholder="Selecciona un estudiante" />
                </SelectTrigger>
                <SelectContent>
                  {availableOptions.map((student) => (
                    <SelectItem key={student.email} value={student.email}>
                      <div className="flex items-center gap-2">
                        {student.name ? (
                          <>
                            <span className="font-medium">{student.name}</span>
                            <span className="text-muted-foreground text-sm">
                              ({student.email})
                            </span>
                          </>
                        ) : (
                          <span>{student.email}</span>
                        )}
                      </div>
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
            {errors.traineeEmail && (
              <p className="text-sm text-destructive">{errors.traineeEmail}</p>
            )}
          </div>

          <div className="space-y-2">
            <Label>
              Fecha de Inicio <span className="text-destructive">*</span>
            </Label>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={`w-full justify-start text-left font-normal ${
                    !formData.startDate ? 'text-muted-foreground' : ''
                  } ${errors.startDate ? 'border-destructive' : ''}`}
                  disabled={loading}
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {formData.startDate ? (
                    format(formData.startDate, 'PPP', { locale: es })
                  ) : (
                    <span>Seleccionar fecha</span>
                  )}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <Calendar
                  mode="single"
                  selected={formData.startDate}
                  onSelect={(date) => setFormData({ ...formData, startDate: date })}
                  locale={es}
                  disabled={(date) => {
                    const today = new Date();
                    today.setHours(0, 0, 0, 0);
                    return date < today;
                  }}
                />
              </PopoverContent>
            </Popover>
            {errors.startDate && (
              <p className="text-sm text-destructive">{errors.startDate}</p>
            )}
          </div>

          <div className="space-y-2">
            <Label>Fecha de Fin (Opcional)</Label>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={`w-full justify-start text-left font-normal ${
                    !formData.endDate ? 'text-muted-foreground' : ''
                  } ${errors.endDate ? 'border-destructive' : ''}`}
                  disabled={loading}
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {formData.endDate ? (
                    format(formData.endDate, 'PPP', { locale: es })
                  ) : (
                    <span>Seleccionar fecha (opcional)</span>
                  )}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <Calendar
                  mode="single"
                  selected={formData.endDate}
                  onSelect={(date) => setFormData({ ...formData, endDate: date })}
                  locale={es}
                  disabled={(date) => {
                    const today = new Date();
                    today.setHours(0, 0, 0, 0);
                    const minDate = formData.startDate || today;
                    return date < minDate;
                  }}
                />
              </PopoverContent>
            </Popover>
            {errors.endDate && (
              <p className="text-sm text-destructive">{errors.endDate}</p>
            )}
          </div>

          <DialogFooter>
            <Button
              type="button"
              variant="outline"
              onClick={handleClose}
              disabled={loading}
            >
              Cancelar
            </Button>
            <Button type="submit" disabled={loading}>
              {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Agregar Estudiante
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

