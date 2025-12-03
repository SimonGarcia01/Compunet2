import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {
  fetchAllEvents,
  fetchEventById,
  createEvent,
  updateEvent,
  deleteEvent,
  joinEvent,
  leaveEvent,
} from './thunk';

export type EventType = 'class' | 'tournament' | 'schedule' | 'social' | 'open_call';
export type EventStatus = 'upcoming' | 'ongoing' | 'completed' | 'cancelled';

export interface Event {
  eventId: string;
  title: string;
  description: string;
  type: EventType;
  startDate: string;
  endDate: string;
  location: string;
  capacity?: number;
  organizer: {
    email: string;
    name: string;
  };
  attendees: string[]; // emails
  status: EventStatus;
  createdAt: string;
  imageUrl?: string;
}

export interface EventFilters {
  type?: EventType;
  status?: EventStatus;
  search?: string;
  dateFrom?: string;
  dateTo?: string;
}

interface EventsState {
  items: Event[];
  currentEvent: Event | null;
  filters: EventFilters;
  loading: boolean;
  error: string | null;
  creating: boolean;
  updating: boolean;
}

const initialState: EventsState = {
  items: [],
  currentEvent: null,
  filters: {},
  loading: false,
  error: null,
  creating: false,
  updating: false,
};

const eventsSlice = createSlice({
  name: 'events',
  initialState,
  reducers: {
    setFilters: (state, action: PayloadAction<EventFilters>) => {
      state.filters = action.payload;
    },
    clearFilters: (state) => {
      state.filters = {};
    },
    clearCurrentEvent: (state) => {
      state.currentEvent = null;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch all events
      .addCase(fetchAllEvents.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllEvents.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchAllEvents.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Fetch event by ID
      .addCase(fetchEventById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchEventById.fulfilled, (state, action) => {
        state.loading = false;
        state.currentEvent = action.payload;
      })
      .addCase(fetchEventById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Create event
      .addCase(createEvent.pending, (state) => {
        state.creating = true;
        state.error = null;
      })
      .addCase(createEvent.fulfilled, (state, action) => {
        state.creating = false;
        state.items.unshift(action.payload);
      })
      .addCase(createEvent.rejected, (state, action) => {
        state.creating = false;
        state.error = action.payload as string;
      })
      // Update event
      .addCase(updateEvent.pending, (state) => {
        state.updating = true;
        state.error = null;
      })
      .addCase(updateEvent.fulfilled, (state, action) => {
        state.updating = false;
        const index = state.items.findIndex((e) => e.eventId === action.payload.eventId);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
        if (state.currentEvent?.eventId === action.payload.eventId) {
          state.currentEvent = action.payload;
        }
      })
      .addCase(updateEvent.rejected, (state, action) => {
        state.updating = false;
        state.error = action.payload as string;
      })
      // Delete event
      .addCase(deleteEvent.fulfilled, (state, action) => {
        state.items = state.items.filter((e) => e.eventId !== action.payload);
        if (state.currentEvent?.eventId === action.payload) {
          state.currentEvent = null;
        }
      })
      // Join event
      .addCase(joinEvent.fulfilled, (state, action) => {
        const index = state.items.findIndex((e) => e.eventId === action.payload.eventId);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
        if (state.currentEvent?.eventId === action.payload.eventId) {
          state.currentEvent = action.payload;
        }
      })
      // Leave event
      .addCase(leaveEvent.fulfilled, (state, action) => {
        const index = state.items.findIndex((e) => e.eventId === action.payload.eventId);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
        if (state.currentEvent?.eventId === action.payload.eventId) {
          state.currentEvent = action.payload;
        }
      });
  },
});

export const { setFilters, clearFilters, clearCurrentEvent, clearError } = eventsSlice.actions;
export default eventsSlice.reducer;
