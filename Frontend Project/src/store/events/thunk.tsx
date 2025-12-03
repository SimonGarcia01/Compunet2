import { createAsyncThunk } from '@reduxjs/toolkit';
import type { RootState } from '../index';
import type { Event, EventType, EventFilters } from './eventsSlice';
import { apiFetch } from '@/services/http';

const API_BASE = '/api/v1';

// Interfaces para los DTOs del backend
interface BackendEventResponse {
  eventId: number;
  name: string;
  description: string;
  dateTimeStart: string;
  dateTimeEnd: string;
  creationDate: string;
  maxAttendees: number;
  status: string;
  estimatedBurntCalories?: number;
  eventType: {
    eventTypeId: number;
    name: string;
    description?: string;
  };
  availableSpace: {
    spaceId: number;
    name: string;
    location: string;
    locationMaxAttendees: number;
  };
  user: {
    userId: number;
    email: string;
    name?: string;
  };
}

interface BackendEventTypeResponse {
  eventTypeId: number;
  name: string;
  description?: string;
}

interface BackendAvailableSpaceResponse {
  spaceId: number;
  name: string;
  location: string;
  locationMaxAttendees: number;
}

interface BackendEventAttendanceResponse {
  userId: number;
  eventId: number;
  user?: {
    email: string;
    name?: string;
  };
}

// Mapeo de tipos de evento del frontend al backend
const eventTypeMapping: Record<string, string> = {
  'class': 'Clase',
  'tournament': 'Torneo',
  'schedule': 'Horario',
  'social': 'Social',
  'open_call': 'Convocatoria',
};

const reverseEventTypeMapping: Record<string, EventType> = {
  'Clase': 'class',
  'Torneo': 'tournament',
  'Horario': 'schedule',
  'Social': 'social',
  'Convocatoria': 'open_call',
};

// Mapear evento del backend al frontend
function mapBackendEventToFrontend(backendEvent: BackendEventResponse, attendees: string[]): Event {
  const now = new Date();
  const startDate = new Date(backendEvent.dateTimeStart);
  const endDate = new Date(backendEvent.dateTimeEnd);
  
  let status: 'upcoming' | 'ongoing' | 'completed' | 'cancelled' = 'upcoming';
  if (backendEvent.status === 'CANCELLED' || backendEvent.status === 'cancelled') {
    status = 'cancelled';
  } else if (backendEvent.status === 'COMPLETED' || backendEvent.status === 'completed') {
    status = 'completed';
  } else if (now >= startDate && now <= endDate) {
    status = 'ongoing';
  } else if (now > endDate) {
    status = 'completed';
  }

  const eventType = reverseEventTypeMapping[backendEvent.eventType.name] || 'class';

  return {
    eventId: backendEvent.eventId ? String(backendEvent.eventId) : '',
    title: backendEvent.name,
    description: backendEvent.description || '',
    type: eventType,
    startDate: backendEvent.dateTimeStart,
    endDate: backendEvent.dateTimeEnd,
    location: backendEvent.availableSpace.location || backendEvent.availableSpace.name,
    capacity: backendEvent.maxAttendees > 0 ? backendEvent.maxAttendees : undefined,
    organizer: {
      email: backendEvent.user.email,
      name: backendEvent.user.name || backendEvent.user.email.split('@')[0],
    },
    attendees,
    status,
    createdAt: backendEvent.creationDate,
    imageUrl: undefined, // El backend no tiene este campo
  };
}

// Obtener tipos de eventos
export const fetchEventTypes = createAsyncThunk<
  BackendEventTypeResponse[],
  void,
  { rejectValue: string }
>('events/fetchEventTypes', async (_, { rejectWithValue }) => {
  try {
    const res = await apiFetch(`${API_BASE}/event_types`);
    if (!res.ok) {
      const error = await res.json().catch(() => ({ message: res.statusText }));
      return rejectWithValue(error.message || 'Error al obtener tipos de eventos');
    }
    return await res.json();
  } catch (error) {
    return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener tipos de eventos');
  }
});

// Obtener espacios disponibles
export const fetchAvailableSpaces = createAsyncThunk<
  BackendAvailableSpaceResponse[],
  void,
  { rejectValue: string }
>('events/fetchAvailableSpaces', async (_, { rejectWithValue }) => {
  try {
    const res = await apiFetch(`${API_BASE}/available_spaces`);
    if (!res.ok) {
      const error = await res.json().catch(() => ({ message: res.statusText }));
      return rejectWithValue(error.message || 'Error al obtener espacios disponibles');
    }
    return await res.json();
  } catch (error) {
    return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener espacios disponibles');
  }
});

// Obtener todas las asistencias (para mapear attendees)
export const fetchEventAttendances = createAsyncThunk<
  BackendEventAttendanceResponse[],
  void,
  { rejectValue: string }
>('events/fetchAttendances', async (_, { rejectWithValue }) => {
  try {
    const res = await apiFetch(`${API_BASE}/event_attendances`);
    if (!res.ok) {
      // Si falla, retornar array vacío (no es crítico)
      return [];
    }
    return await res.json();
  } catch (error) {
    // Si falla, retornar array vacío
    return [];
  }
});

// Obtener todos los eventos
export const fetchAllEvents = createAsyncThunk<Event[], EventFilters | undefined>(
  'events/fetchAll',
  async (filters = {}, { rejectWithValue, dispatch }) => {
    try {
      // Obtener eventos, tipos, espacios y asistencias
      const [eventsRes, attendancesRes] = await Promise.all([
        apiFetch(`${API_BASE}/events`),
        dispatch(fetchEventAttendances() as any),
      ]);

      if (!eventsRes.ok) {
        const error = await eventsRes.json().catch(() => ({ message: eventsRes.statusText }));
        return rejectWithValue(error.message || 'Error al cargar eventos');
      }

      const backendEvents: BackendEventResponse[] = await eventsRes.json();
      const attendances: BackendEventAttendanceResponse[] = attendancesRes.payload || [];

      // Crear un mapa de eventId -> emails de asistentes
      const attendeesMap = new Map<number, string[]>();
      attendances.forEach((attendance) => {
        if (!attendeesMap.has(attendance.eventId)) {
          attendeesMap.set(attendance.eventId, []);
        }
        const email = attendance.user?.email || '';
        if (email) {
          attendeesMap.get(attendance.eventId)!.push(email);
        }
      });

      // Mapear eventos del backend al frontend
      let events = backendEvents.map((event) => 
        mapBackendEventToFrontend(event, attendeesMap.get(event.eventId) || [])
      );

      // Aplicar filtros del frontend
  if (filters.type) {
        events = events.filter((e) => e.type === filters.type);
  }

  if (filters.status) {
        events = events.filter((e) => e.status === filters.status);
  }

  if (filters.search) {
    const search = filters.search.toLowerCase();
        events = events.filter(
      (e) =>
        e.title.toLowerCase().includes(search) ||
        e.description.toLowerCase().includes(search) ||
        e.location.toLowerCase().includes(search)
    );
  }

  if (filters.dateFrom) {
        events = events.filter((e) => new Date(e.startDate) >= new Date(filters.dateFrom!));
  }

  if (filters.dateTo) {
        events = events.filter((e) => new Date(e.startDate) <= new Date(filters.dateTo!));
      }

      return events.sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al cargar eventos');
    }
  }
);

// Obtener evento por ID
export const fetchEventById = createAsyncThunk<Event, string>(
  'events/fetchById',
  async (eventId, { rejectWithValue, dispatch }) => {
    try {
      const [eventRes, attendancesRes] = await Promise.all([
        apiFetch(`${API_BASE}/events/${eventId}`),
        dispatch(fetchEventAttendances() as any),
      ]);

      if (!eventRes.ok) {
        const error = await eventRes.json().catch(() => ({ message: eventRes.statusText }));
        return rejectWithValue(error.message || 'Evento no encontrado');
      }

      const backendEvent: BackendEventResponse = await eventRes.json();
      const attendances: BackendEventAttendanceResponse[] = attendancesRes.payload || [];

      const attendees = attendances
        .filter((att) => att.eventId === Number(eventId))
        .map((att) => att.user?.email || '')
        .filter(Boolean);

      return mapBackendEventToFrontend(backendEvent, attendees);
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al cargar evento');
    }
  }
);

// Obtener userId del usuario autenticado
async function getCurrentUserId(userEmail: string): Promise<number | null> {
  try {
    // Intentar obtener el usuario desde el endpoint de usuarios
    // Nota: Esto requiere que el backend tenga un endpoint para obtener usuario por email
    // Por ahora, vamos a intentar obtener todos los usuarios y filtrar (no ideal, pero funcional)
    const res = await apiFetch(`${API_BASE}/users`);
    if (res.ok) {
      const users: any[] = await res.json();
      const user = users.find((u) => u.email === userEmail);
      return user?.userId || null;
    }
    return null;
  } catch {
    return null;
  }
}

interface CreateEventPayload {
  title: string;
  description: string;
  type: EventType;
  startDate: string;
  endDate: string;
  location: string;
  capacity?: number;
  imageUrl?: string;
  userEmail: string;
  userName: string;
}

// Crear evento
export const createEvent = createAsyncThunk<Event, CreateEventPayload>(
  'events/create',
  async (payload, { getState, rejectWithValue, dispatch }) => {
    try {
      const state = getState() as RootState;
      const userEmail = payload.userEmail;

      // Obtener tipos de eventos y espacios
      const [eventTypesRes, spacesRes] = await Promise.all([
        dispatch(fetchEventTypes() as any),
        dispatch(fetchAvailableSpaces() as any),
      ]);

      const eventTypes: BackendEventTypeResponse[] = eventTypesRes.payload || [];
      const spaces: BackendAvailableSpaceResponse[] = spacesRes.payload || [];

      // Mapear tipo de evento
      const eventTypeName = eventTypeMapping[payload.type] || 'Clase';
      const eventType = eventTypes.find((et) => et.name === eventTypeName);
      if (!eventType) {
        return rejectWithValue('Tipo de evento no encontrado');
      }

      // Mapear espacio disponible (buscar por location o name)
      const space = spaces.find(
        (s) => s.location === payload.location || s.name === payload.location
      );
      if (!space) {
        return rejectWithValue('Espacio no encontrado');
      }

      // El backend obtiene el userId del SecurityContext automáticamente
      // Construir payload para el backend
      const backendPayload = {
        name: payload.title,
        description: payload.description,
        dateTimeStart: payload.startDate,
        dateTimeEnd: payload.endDate,
        maxAttendees: payload.capacity || 0,
        estimatedBurntCalories: null,
        userId: null, // El backend lo obtendrá del SecurityContext
        availableSpaceId: space.spaceId,
        eventTypeId: eventType.eventTypeId,
      };

      const res = await apiFetch(`${API_BASE}/events`, {
        method: 'POST',
        body: JSON.stringify(backendPayload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al crear evento');
      }

      // El backend retorna MsgResp, necesitamos recargar la lista
      await dispatch(fetchAllEvents(undefined) as any);
      
      // Retornar un evento temporal (se actualizará con la lista)
      return {
        eventId: 'temp',
        title: payload.title,
        description: payload.description,
        type: payload.type,
        startDate: payload.startDate,
        endDate: payload.endDate,
        location: payload.location,
        capacity: payload.capacity,
        organizer: { email: userEmail, name: payload.userName },
        attendees: [],
        status: 'upcoming',
        createdAt: new Date().toISOString(),
        imageUrl: payload.imageUrl,
      };
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al crear evento');
    }
  }
);

interface UpdateEventPayload {
  eventId: string;
  updates: Partial<CreateEventPayload>;
}

// Actualizar evento
export const updateEvent = createAsyncThunk<Event, UpdateEventPayload>(
  'events/update',
  async ({ eventId, updates }, { getState, rejectWithValue, dispatch }) => {
    try {
      const state = getState() as RootState;
      const userEmail = state.auth.user?.email;
      if (!userEmail) {
        return rejectWithValue('No se encontró el email del usuario');
      }

      // Obtener tipos y espacios si se necesitan
      let eventTypeId: number | undefined;
      let availableSpaceId: number | undefined;

      if (updates.type) {
        const eventTypesRes = await dispatch(fetchEventTypes() as any);
        const eventTypes: BackendEventTypeResponse[] = eventTypesRes.payload || [];
        const eventTypeName = eventTypeMapping[updates.type] || 'Clase';
        const eventType = eventTypes.find((et) => et.name === eventTypeName);
        if (eventType) {
          eventTypeId = eventType.eventTypeId;
        }
      }

      if (updates.location) {
        const spacesRes = await dispatch(fetchAvailableSpaces() as any);
        const spaces: BackendAvailableSpaceResponse[] = spacesRes.payload || [];
        const space = spaces.find(
          (s) => s.location === updates.location || s.name === updates.location
        );
        if (space) {
          availableSpaceId = space.spaceId;
        }
      }

      // El backend obtiene el userId del SecurityContext automáticamente
      const backendPayload: any = {};
      if (updates.title) backendPayload.name = updates.title;
      if (updates.description) backendPayload.description = updates.description;
      if (updates.startDate) backendPayload.dateTimeStart = updates.startDate;
      if (updates.endDate) backendPayload.dateTimeEnd = updates.endDate;
      if (updates.capacity !== undefined) backendPayload.maxAttendees = updates.capacity || 0;
      if (eventTypeId) backendPayload.eventTypeId = eventTypeId;
      if (availableSpaceId) backendPayload.availableSpaceId = availableSpaceId;
      backendPayload.userId = null; // El backend lo obtendrá del SecurityContext

      const res = await apiFetch(`${API_BASE}/events/${eventId}`, {
        method: 'PUT',
        body: JSON.stringify(backendPayload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al actualizar evento');
      }

      // Recargar la lista
      await dispatch(fetchAllEvents(undefined) as any);
      
      // Retornar evento temporal
      return {
        eventId,
        title: updates.title || '',
        description: updates.description || '',
        type: updates.type || 'class',
        startDate: updates.startDate || '',
        endDate: updates.endDate || '',
        location: updates.location || '',
        capacity: updates.capacity,
        organizer: { email: userEmail, name: userEmail.split('@')[0] },
        attendees: [],
        status: 'upcoming',
        createdAt: new Date().toISOString(),
        imageUrl: updates.imageUrl,
      };
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al actualizar evento');
    }
  }
);

// Eliminar evento
export const deleteEvent = createAsyncThunk<string, string>(
  'events/delete',
  async (eventId, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/events/${eventId}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al eliminar evento');
      }

      return eventId;
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al eliminar evento');
    }
  }
);

interface JoinEventPayload {
  eventId: string;
  userEmail: string;
}

// Unirse a evento
export const joinEvent = createAsyncThunk<Event, JoinEventPayload>(
  'events/join',
  async ({ eventId, userEmail }, { getState, rejectWithValue, dispatch }) => {
    try {
      // Validar que eventId sea válido
      const eventIdNum = Number(eventId);
      if (isNaN(eventIdNum) || eventIdNum <= 0) {
        return rejectWithValue('ID de evento inválido');
      }

      // El backend obtiene el userId del SecurityContext, solo enviamos eventId
      const payload = {
        userId: null, // El backend lo obtendrá del SecurityContext
        eventId: eventIdNum,
      };

      const res = await apiFetch(`${API_BASE}/event_attendances`, {
        method: 'POST',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al unirse al evento');
      }

      // Recargar el evento actualizado
      const updatedEvent = await dispatch(fetchEventById(eventId) as any);
      if (updatedEvent.type === 'rejected') {
        return rejectWithValue('Error al obtener evento actualizado');
      }

      return updatedEvent.payload as Event;
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al unirse al evento');
    }
  }
);

// Salir de evento
export const leaveEvent = createAsyncThunk<Event, JoinEventPayload>(
  'events/leave',
  async ({ eventId, userEmail }, { getState, rejectWithValue, dispatch }) => {
    try {
      // El backend obtiene el userId del SecurityContext, solo enviamos eventId
      const res = await apiFetch(`${API_BASE}/event_attendances/${eventId}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al salir del evento');
      }

      // Recargar el evento actualizado
      const updatedEvent = await dispatch(fetchEventById(eventId) as any);
      if (updatedEvent.type === 'rejected') {
        return rejectWithValue('Error al obtener evento actualizado');
      }

      return updatedEvent.payload as Event;
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al salir del evento');
    }
  }
);
