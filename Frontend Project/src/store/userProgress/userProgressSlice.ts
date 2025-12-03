import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { fetchMyProgress, createProgress, updateProgress, deleteProgress } from './thunk';

export interface ExerciseProgress {
  progressId?: string;
  user?: { email: string; name?: string };
  workoutProgram?: { workoutId: string; name: string };
  exercise?: { exerciseId: string; name: string; type: string };
  recordDate: string;
  periodType: 'DAILY' | 'WEEKLY';
  repetitions?: number;
  timeMinutes?: number;
  distanceKm?: number;
  rpe?: number; // Rate of Perceived Exertion (1-10)
  notes?: string;
  estimatedCaloriesBurnt?: number;
}

export interface ProgressFormData {
  workoutId?: string;
  exerciseId?: string;
  recordDate: string;
  periodType: 'DAILY' | 'WEEKLY';
  repetitions?: number;
  timeMinutes?: number;
  distanceKm?: number;
  rpe?: number;
  notes?: string;
  estimatedCaloriesBurnt?: number;
}

interface UserProgressState {
  items: ExerciseProgress[];
  loading: boolean;
  saving: boolean;
  error: string | null;
  filters: {
    startDate?: string;
    endDate?: string;
    workoutId?: string;
    exerciseId?: string;
    periodType?: 'DAILY' | 'WEEKLY';
  };
}

const initialState: UserProgressState = {
  items: [],
  loading: false,
  saving: false,
  error: null,
  filters: {},
};

const userProgressSlice = createSlice({
  name: 'userProgress',
  initialState,
  reducers: {
    setFilters: (state, action: PayloadAction<Partial<UserProgressState['filters']>>) => {
      state.filters = { ...state.filters, ...action.payload };
    },
    clearFilters: (state) => {
      state.filters = {};
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch my progress
      .addCase(fetchMyProgress.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchMyProgress.fulfilled, (state, action) => {
        state.loading = false;
        // Asegurar que siempre sea un array
        state.items = Array.isArray(action.payload) ? action.payload : [];
        console.log('Progreso cargado en el store:', state.items.length, 'elementos');
      })
      .addCase(fetchMyProgress.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Create progress
      .addCase(createProgress.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(createProgress.fulfilled, (state, action) => {
        state.saving = false;
        if (action.payload) {
          state.items.push(action.payload);
        }
      })
      .addCase(createProgress.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload as string;
      })
      // Update progress
      .addCase(updateProgress.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(updateProgress.fulfilled, (state, action) => {
        state.saving = false;
        const index = state.items.findIndex(p => p.progressId === action.payload.progressId);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      })
      .addCase(updateProgress.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload as string;
      })
      // Delete progress
      .addCase(deleteProgress.fulfilled, (state, action) => {
        state.items = state.items.filter(p => p.progressId !== action.payload);
      });
  },
});

export const { setFilters, clearFilters, clearError } = userProgressSlice.actions;
export default userProgressSlice.reducer;

