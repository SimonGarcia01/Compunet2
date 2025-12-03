import { Calendar, MapPin, Users, Clock, Trophy, Dumbbell, MessageCircle, MoreVertical } from "lucide-react";
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import type { Event, EventType } from "@/store/events/eventsSlice";
import { format } from "date-fns";
import { es } from "date-fns/locale";

interface EventCardProps {
  event: Event;
  onJoin?: () => void;
  onLeave?: () => void;
  onEdit?: () => void;
  onDelete?: () => void;
  onViewDetails?: () => void;
  isAttending?: boolean;
  canManage?: boolean;
}

const eventTypeConfig: Record<
  EventType,
  { icon: any; label: string; variant: "default" | "secondary" | "destructive" | "outline" }
> = {
  class: { icon: Dumbbell, label: "Clase", variant: "default" },
  tournament: { icon: Trophy, label: "Torneo", variant: "destructive" },
  schedule: { icon: Calendar, label: "Horario", variant: "secondary" },
  social: { icon: Users, label: "Social", variant: "outline" },
  open_call: { icon: MessageCircle, label: "Convocatoria", variant: "outline" },
};

const statusConfig = {
  upcoming: { label: "Próximo", class: "bg-blue-500/10 text-blue-600 dark:text-blue-400" },
  ongoing: { label: "En Curso", class: "bg-green-500/10 text-green-600 dark:text-green-400" },
  completed: { label: "Completado", class: "bg-muted text-muted-foreground" },
  cancelled: { label: "Cancelado", class: "bg-destructive/10 text-destructive" },
};

export function EventCard({
  event,
  onJoin,
  onLeave,
  onEdit,
  onDelete,
  onViewDetails,
  isAttending,
  canManage,
}: EventCardProps) {
  const typeConfig = eventTypeConfig[event.type];
  const TypeIcon = typeConfig.icon;
  const statusInfo = statusConfig[event.status];

  const isFull = event.capacity && event.attendees.length >= event.capacity;
  const canJoin = !isAttending && !isFull && event.status === "upcoming";

  return (
    <Card className="group overflow-hidden hover:shadow-lg transition-all duration-300">
      {event.imageUrl && (
        <div className="relative h-48 overflow-hidden">
          <img
            src={event.imageUrl}
            alt={event.title}
            className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
          <div className="absolute top-3 right-3 flex gap-2">
            <Badge className={statusInfo.class}>{statusInfo.label}</Badge>
          </div>
        </div>
      )}

      <div className="p-5 space-y-4">
        <div className="flex items-start justify-between gap-3">
          <div className="flex-1 min-w-0">
            <div className="flex items-center gap-2 mb-2">
              <TypeIcon className="h-4 w-4 text-primary shrink-0" />
              <Badge variant={typeConfig.variant} className="text-xs">
                {typeConfig.label}
              </Badge>
            </div>
            <h3 className="font-semibold text-lg leading-tight mb-2 group-hover:text-primary transition-colors">
              {event.title}
            </h3>
            <p className="text-sm text-muted-foreground line-clamp-2">{event.description}</p>
          </div>

          {canManage && (
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon" className="shrink-0">
                  <MoreVertical className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={onEdit}>Editar</DropdownMenuItem>
                <DropdownMenuItem onClick={onDelete} className="text-destructive">
                  Eliminar
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          )}
        </div>

        <div className="space-y-2 text-sm">
          <div className="flex items-center gap-2 text-muted-foreground">
            <Calendar className="h-4 w-4 shrink-0" />
            <span className="truncate">{format(new Date(event.startDate), "PPP", { locale: es })}</span>
          </div>
          <div className="flex items-center gap-2 text-muted-foreground">
            <Clock className="h-4 w-4 shrink-0" />
            <span className="truncate">
              {format(new Date(event.startDate), "p", { locale: es })} -{" "}
              {format(new Date(event.endDate), "p", { locale: es })}
            </span>
          </div>
          <div className="flex items-center gap-2 text-muted-foreground">
            <MapPin className="h-4 w-4 shrink-0" />
            <span className="truncate">{event.location}</span>
          </div>
          {event.capacity && (
            <div className="flex items-center gap-2 text-muted-foreground">
              <Users className="h-4 w-4 shrink-0" />
              <span>
                {event.attendees.length} / {event.capacity} participantes
              </span>
            </div>
          )}
        </div>

        <div className="flex gap-2 pt-2">
          {onViewDetails && (
            <Button variant="outline" className="flex-1" onClick={onViewDetails}>
              Ver detalles
            </Button>
          )}
          {isAttending && onLeave && (
            <Button variant="outline" className="flex-1" onClick={onLeave}>
              Cancelar asistencia
            </Button>
          )}
          {canJoin && onJoin && (
            <Button className="flex-1" onClick={onJoin}>
              Unirme
            </Button>
          )}
        </div>
      </div>
    </Card>
  );
}
