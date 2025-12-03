import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { Event, EventType } from "@/store/events/eventsSlice";
import type { AppDispatch, RootState } from "@/store/index";
import { fetchAvailableSpaces } from "@/store/events/thunk";

interface EventDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onSave: (data: EventFormData) => void;
  event?: Event;
  isLoading?: boolean;
}

export interface EventFormData {
  title: string;
  description: string;
  type: EventType;
  startDate: string;
  endDate: string;
  location: string;
  capacity?: number;
  imageUrl?: string;
}

const eventTypes: { value: EventType; label: string }[] = [
  { value: "class", label: "Clase" },
  { value: "tournament", label: "Torneo" },
  { value: "schedule", label: "Horario" },
  { value: "social", label: "Evento Social" },
  { value: "open_call", label: "Convocatoria Abierta" },
];

export function EventDialog({ open, onOpenChange, onSave, event, isLoading }: EventDialogProps) {
  const dispatch = useDispatch<AppDispatch>();
  const [formData, setFormData] = useState<EventFormData>({
    title: "",
    description: "",
    type: "class",
    startDate: "",
    endDate: "",
    location: "",
    capacity: undefined,
    imageUrl: "",
  });

  const [errors, setErrors] = useState<Partial<Record<keyof EventFormData, string>>>({});
  const [availableSpaces, setAvailableSpaces] = useState<Array<{ spaceId: number; name: string; location: string }>>([]);
  const [loadingSpaces, setLoadingSpaces] = useState(false);

  // Cargar espacios disponibles cuando se abre el diálogo
  useEffect(() => {
    if (open) {
      setLoadingSpaces(true);
      dispatch(fetchAvailableSpaces() as any)
        .then((result: any) => {
          if (result.type === 'events/fetchAvailableSpaces/fulfilled') {
            const spaces = result.payload || [];
            setAvailableSpaces(spaces.map((s: any) => ({
              spaceId: s.spaceId,
              name: s.name,
              location: s.location,
            })));
          }
        })
        .catch(() => {
          // Silenciar errores
        })
        .finally(() => {
          setLoadingSpaces(false);
        });
    }
  }, [open, dispatch]);

  useEffect(() => {
    if (event) {
      setFormData({
        title: event.title,
        description: event.description,
        type: event.type,
        startDate: event.startDate.slice(0, 16),
        endDate: event.endDate.slice(0, 16),
        location: event.location,
        capacity: event.capacity,
        imageUrl: event.imageUrl || "",
      });
    } else {
      // Reset form for new event
      const now = new Date();
      const oneHourLater = new Date(now.getTime() + 60 * 60 * 1000);
      setFormData({
        title: "",
        description: "",
        type: "class",
        startDate: now.toISOString().slice(0, 16),
        endDate: oneHourLater.toISOString().slice(0, 16),
        location: "",
        capacity: undefined,
        imageUrl: "",
      });
    }
    setErrors({});
  }, [event, open]);

  const validate = (): boolean => {
    const newErrors: Partial<Record<keyof EventFormData, string>> = {};

    if (!formData.title.trim()) newErrors.title = "El título es requerido";
    if (!formData.description.trim()) newErrors.description = "La descripción es requerida";
    if (!formData.location.trim()) newErrors.location = "La ubicación es requerida";
    if (!formData.startDate) newErrors.startDate = "La fecha de inicio es requerida";
    if (!formData.endDate) newErrors.endDate = "La fecha de fin es requerida";
    
    if (formData.startDate && formData.endDate) {
      if (new Date(formData.startDate) >= new Date(formData.endDate)) {
        newErrors.endDate = "La fecha de fin debe ser posterior a la de inicio";
      }
    }

    if (formData.capacity !== undefined && formData.capacity < 1) {
      newErrors.capacity = "La capacidad debe ser al menos 1";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (validate()) {
      onSave({
        ...formData,
        startDate: new Date(formData.startDate).toISOString(),
        endDate: new Date(formData.endDate).toISOString(),
      });
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-2xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>{event ? "Editar Evento" : "Crear Nuevo Evento"}</DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="title">Título *</Label>
            <Input
              id="title"
              value={formData.title}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
              placeholder="Ej: Clase de Yoga Matutino"
              aria-invalid={!!errors.title}
            />
            {errors.title && <p className="text-sm text-destructive">{errors.title}</p>}
          </div>

          <div className="space-y-2">
            <Label htmlFor="type">Tipo de Evento *</Label>
            <Select value={formData.type} onValueChange={(value: EventType) => setFormData({ ...formData, type: value })}>
              <SelectTrigger id="type">
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                {eventTypes.map((type) => (
                  <SelectItem key={type.value} value={type.value}>
                    {type.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label htmlFor="description">Descripción *</Label>
            <Textarea
              id="description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              placeholder="Describe el evento..."
              rows={4}
              aria-invalid={!!errors.description}
            />
            {errors.description && <p className="text-sm text-destructive">{errors.description}</p>}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="startDate">Fecha y Hora de Inicio *</Label>
              <Input
                id="startDate"
                type="datetime-local"
                value={formData.startDate}
                onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
                aria-invalid={!!errors.startDate}
              />
              {errors.startDate && <p className="text-sm text-destructive">{errors.startDate}</p>}
            </div>

            <div className="space-y-2">
              <Label htmlFor="endDate">Fecha y Hora de Fin *</Label>
              <Input
                id="endDate"
                type="datetime-local"
                value={formData.endDate}
                onChange={(e) => setFormData({ ...formData, endDate: e.target.value })}
                aria-invalid={!!errors.endDate}
              />
              {errors.endDate && <p className="text-sm text-destructive">{errors.endDate}</p>}
            </div>
          </div>

          <div className="space-y-2">
            <Label htmlFor="location">Ubicación *</Label>
            <Select
              value={formData.location}
              onValueChange={(value) => setFormData({ ...formData, location: value })}
              disabled={loadingSpaces}
            >
              <SelectTrigger id="location" aria-invalid={!!errors.location}>
                <SelectValue placeholder={loadingSpaces ? "Cargando espacios..." : "Selecciona un espacio"} />
              </SelectTrigger>
              <SelectContent>
                {availableSpaces.map((space) => (
                  <SelectItem key={space.spaceId} value={space.location}>
                    {space.name} - {space.location}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            {errors.location && <p className="text-sm text-destructive">{errors.location}</p>}
          </div>

          <div className="space-y-2">
            <Label htmlFor="capacity">Capacidad (opcional)</Label>
            <Input
              id="capacity"
              type="number"
              min="1"
              value={formData.capacity || ""}
              onChange={(e) => setFormData({ ...formData, capacity: e.target.value ? parseInt(e.target.value) : undefined })}
              placeholder="Dejar vacío para capacidad ilimitada"
              aria-invalid={!!errors.capacity}
            />
            {errors.capacity && <p className="text-sm text-destructive">{errors.capacity}</p>}
          </div>

          <div className="space-y-2">
            <Label htmlFor="imageUrl">URL de Imagen (opcional)</Label>
            <Input
              id="imageUrl"
              value={formData.imageUrl}
              onChange={(e) => setFormData({ ...formData, imageUrl: e.target.value })}
              placeholder="https://ejemplo.com/imagen.jpg"
            />
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={() => onOpenChange(false)} disabled={isLoading}>
              Cancelar
            </Button>
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Guardando..." : event ? "Actualizar" : "Crear Evento"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
