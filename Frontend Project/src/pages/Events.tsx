import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Plus, Calendar as CalendarIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";
import { EventCard } from "@/components/events/EventCard";
import { EventFilters } from "@/components/events/EventFilter";
import { EventDialog, type EventFormData } from "@/components/events/EventDialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useToast } from "@/hooks/use-toast";
import type { RootState, AppDispatch } from "@/store/index";
import { fetchAllEvents, createEvent, joinEvent, leaveEvent, deleteEvent } from "@/store/events/thunk";
import { setFilters } from "@/store/events/eventsSlice";

export default function Events() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { toast } = useToast();
  const { user } = useSelector((s: RootState) => s.auth);
  const { items: events, loading, filters, creating } = useSelector((s: RootState) => s.events);

  const [dialogOpen, setDialogOpen] = useState(false);
  const [activeTab, setActiveTab] = useState<"upcoming" | "ongoing" | "all">("upcoming");

  const isTrainerOrAdmin = user?.roles?.some(
    (role) => role === "Entrenador" || role === "Administrador"
  );

  useEffect(() => {
    if (!user) {
      navigate("/login");
      return;
    }
    dispatch(fetchAllEvents(filters));
  }, [dispatch, user, navigate, filters]);

  const handleFiltersChange = (newFilters: typeof filters) => {
    dispatch(setFilters(newFilters));
  };

  const handleCreateEvent = async (data: EventFormData) => {
    if (!user) return;
    
    const result = await dispatch(
      createEvent({
        ...data,
        userEmail: user.email,
        userName: user.email.split("@")[0],
      })
    );

    if (createEvent.fulfilled.match(result)) {
      toast({
        title: "Evento creado",
        description: "El evento se ha creado exitosamente",
      });
      setDialogOpen(false);
    } else {
      toast({
        title: "Error",
        description: result.payload as string,
        variant: "destructive",
      });
    }
  };

  const handleJoinEvent = async (eventId: string) => {
    if (!user) return;

    const result = await dispatch(joinEvent({ eventId, userEmail: user.email }));

    if (joinEvent.fulfilled.match(result)) {
      toast({
        title: "Te has unido al evento",
        description: "Ahora formas parte de este evento",
      });
    } else {
      toast({
        title: "Error",
        description: result.payload as string,
        variant: "destructive",
      });
    }
  };

  const handleLeaveEvent = async (eventId: string) => {
    if (!user) return;

    const result = await dispatch(leaveEvent({ eventId, userEmail: user.email }));

    if (leaveEvent.fulfilled.match(result)) {
      toast({
        title: "Has salido del evento",
        description: "Ya no formas parte de este evento",
      });
    } else {
      toast({
        title: "Error",
        description: result.payload as string,
        variant: "destructive",
      });
    }
  };

  const handleDeleteEvent = async (eventId: string) => {
    const result = await dispatch(deleteEvent(eventId));

    if (deleteEvent.fulfilled.match(result)) {
      toast({
        title: "Evento eliminado",
        description: "El evento se ha eliminado exitosamente",
      });
    } else {
      toast({
        title: "Error",
        description: result.payload as string,
        variant: "destructive",
      });
    }
  };

  const filteredEvents = events.filter((event) => {
    if (activeTab === "all") return true;
    return event.status === activeTab;
  });

  const upcomingCount = events.filter((e) => e.status === "upcoming").length;
  const ongoingCount = events.filter((e) => e.status === "ongoing").length;

  return (
    <div className="min-h-screen bg-background">

      <div className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-8">
          <div>
            <h1 className="text-4xl font-bold flex items-center gap-3">
              <CalendarIcon className="h-8 w-8 text-primary" />
              Eventos y Espacios
            </h1>
            <p className="text-muted-foreground mt-2">
              Explora clases, torneos y actividades del gimnasio
            </p>
          </div>

          {isTrainerOrAdmin && (
            <Button onClick={() => setDialogOpen(true)} size="lg">
              <Plus className="h-5 w-5 mr-2" />
              Crear Evento
            </Button>
          )}
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          {/* Filters Sidebar */}
          <aside className="lg:col-span-1">
            <EventFilters filters={filters} onFiltersChange={handleFiltersChange} />
          </aside>

          {/* Events List */}
          <div className="lg:col-span-3">
            <Tabs value={activeTab} onValueChange={(v) => setActiveTab(v as any)} className="space-y-6">
              <TabsList className="grid w-full grid-cols-3">
                <TabsTrigger value="upcoming" className="relative">
                  Próximos
                  {upcomingCount > 0 && (
                    <span className="ml-2 bg-primary text-primary-foreground rounded-full w-5 h-5 text-xs flex items-center justify-center">
                      {upcomingCount}
                    </span>
                  )}
                </TabsTrigger>
                <TabsTrigger value="ongoing" className="relative">
                  En Curso
                  {ongoingCount > 0 && (
                    <span className="ml-2 bg-green-500 text-white rounded-full w-5 h-5 text-xs flex items-center justify-center">
                      {ongoingCount}
                    </span>
                  )}
                </TabsTrigger>
                <TabsTrigger value="all">Todos</TabsTrigger>
              </TabsList>

              <TabsContent value={activeTab} className="space-y-6">
                {loading ? (
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {[...Array(4)].map((_, i) => (
                      <Skeleton key={i} className="h-96 rounded-lg" />
                    ))}
                  </div>
                ) : filteredEvents.length === 0 ? (
                  <div className="text-center py-12">
                    <CalendarIcon className="h-16 w-16 text-muted-foreground mx-auto mb-4" />
                    <h3 className="text-xl font-semibold mb-2">No hay eventos</h3>
                    <p className="text-muted-foreground">
                      {activeTab === "upcoming" && "No hay eventos próximos en este momento"}
                      {activeTab === "ongoing" && "No hay eventos en curso en este momento"}
                      {activeTab === "all" && "No se encontraron eventos"}
                    </p>
                  </div>
                ) : (
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {filteredEvents.map((event) => (
                      <EventCard
                        key={event.eventId}
                        event={event}
                        isAttending={event.attendees.includes(user?.email || "")}
                        canManage={
                          isTrainerOrAdmin &&
                          (event.organizer.email === user?.email ||
                            user?.roles?.includes("Administrador"))
                        }
                        onJoin={() => handleJoinEvent(event.eventId)}
                        onLeave={() => handleLeaveEvent(event.eventId)}
                        onDelete={() => handleDeleteEvent(event.eventId)}
                      />
                    ))}
                  </div>
                )}
              </TabsContent>
            </Tabs>
          </div>
        </div>
      </div>

      <EventDialog
        open={dialogOpen}
        onOpenChange={setDialogOpen}
        onSave={handleCreateEvent}
        isLoading={creating}
      />
    </div>
  );
}
