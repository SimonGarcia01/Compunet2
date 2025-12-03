import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { fetchHistory } from './thunk';

export interface HistoricalRecord {
  recordId: number;
  event?: {
    eventId: number;
    title: string;
    description: string;
    startDate: string;
    endDate: string;
  };
  workoutProgram?: {
    workoutId: number;
    name: string;
    description: string;
  };
  details?: string;
  estimatedBurntCalories?: number;
}

export interface HistoryActivity {
  id: string;
  type: 'progress' | 'routine' | 'event' | 'historical';
  date: string;
  title: string;
  description?: string;
  details?: any;
  calories?: number;
}

interface HistoryState {
  activities: HistoryActivity[];
  loading: boolean;
  error: string | null;
  metrics: {
    totalActivities: number;
    totalCalories: number;
    completedRoutines: number;
    eventsAttended: number;
    daysActive: number;
  };
}

const initialState: HistoryState = {
  activities: [],
  loading: false,
  error: null,
  metrics: {
    totalActivities: 0,
    totalCalories: 0,
    completedRoutines: 0,
    eventsAttended: 0,
    daysActive: 0,
  },
};

const historySlice = createSlice({
  name: 'history',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchHistory.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchHistory.fulfilled, (state, action) => {
        state.loading = false;
        state.activities = action.payload.activities;
        state.metrics = action.payload.metrics;
      })
      .addCase(fetchHistory.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearError } = historySlice.actions;
export default historySlice.reducer;


