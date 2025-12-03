import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { fetchMyPrograms, createProgram, updateProgram, deleteProgram, fetchProgramById, fetchPredesignedPrograms, adoptProgram } from './thunk';

export interface WorkoutExercise {
  workoutExerciseId?: string;
  exerciseId: string;
  exerciseName?: string;
  exerciseType?: string;
  series: number;
  amount: number;
  session: number;
  notes?: string;
  rpe?: number; // Rate of Perceived Exertion (1-10)
}

export interface WorkoutProgram {
  workoutId: string;
  name: string;
  description: string;
  photoUrl?: string;
  creationDate: string;
  completed: boolean;
  userId: string;
  exercises: WorkoutExercise[];
  creator?: {
    email: string;
    name?: string;
    roles?: any[];
  };
}

interface WorkoutProgramsState {
  items: WorkoutProgram[];
  predesigned: WorkoutProgram[];
  current: WorkoutProgram | null;
  loading: boolean;
  predesignedLoading: boolean;
  saving: boolean;
  error: string | null;
}

const initialState: WorkoutProgramsState = {
  items: [],
  predesigned: [],
  current: null,
  loading: false,
  predesignedLoading: false,
  saving: false,
  error: null,
};

const workoutProgramsSlice = createSlice({
  name: 'workoutPrograms',
  initialState,
  reducers: {
    setCurrentProgram: (state, action: PayloadAction<WorkoutProgram | null>) => {
      state.current = action.payload;
    },
    addExerciseToProgram: (state, action: PayloadAction<WorkoutExercise>) => {
      if (state.current) {
        state.current.exercises.push(action.payload);
      }
    },
    updateExerciseInProgram: (state, action: PayloadAction<{ index: number; exercise: WorkoutExercise }>) => {
      if (state.current) {
        state.current.exercises[action.payload.index] = action.payload.exercise;
      }
    },
    removeExerciseFromProgram: (state, action: PayloadAction<number>) => {
      if (state.current) {
        state.current.exercises.splice(action.payload, 1);
      }
    },
    reorderExercises: (state, action: PayloadAction<WorkoutExercise[]>) => {
      if (state.current) {
        state.current.exercises = action.payload;
      }
    },
    clearCurrentProgram: (state) => {
      state.current = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchMyPrograms.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchMyPrograms.fulfilled, (state, action) => {
        state.loading = false;
        // Asegurar que siempre sea un array
        state.items = Array.isArray(action.payload) ? action.payload : [];
      })
      .addCase(fetchMyPrograms.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(fetchProgramById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProgramById.fulfilled, (state, action) => {
        state.loading = false;
        state.current = action.payload;
      })
      .addCase(fetchProgramById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(createProgram.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(createProgram.fulfilled, (state, action) => {
        state.saving = false;
        state.items.push(action.payload);
        state.current = action.payload;
      })
      .addCase(createProgram.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload as string;
      })
      .addCase(updateProgram.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(updateProgram.fulfilled, (state, action) => {
        state.saving = false;
        const index = state.items.findIndex(p => p.workoutId === action.payload.workoutId);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
        state.current = action.payload;
      })
      .addCase(updateProgram.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload as string;
      })
      .addCase(deleteProgram.fulfilled, (state, action) => {
        state.items = state.items.filter(p => p.workoutId !== action.payload);
        if (state.current?.workoutId === action.payload) {
          state.current = null;
        }
      })
      .addCase(fetchPredesignedPrograms.pending, (state) => {
        state.predesignedLoading = true;
        state.error = null;
      })
      .addCase(fetchPredesignedPrograms.fulfilled, (state, action) => {
        state.predesignedLoading = false;
        state.predesigned = action.payload;
      })
      .addCase(fetchPredesignedPrograms.rejected, (state, action) => {
        state.predesignedLoading = false;
        state.error = action.payload as string;
      })
      .addCase(adoptProgram.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(adoptProgram.fulfilled, (state, action) => {
        state.saving = false;
        state.items.push(action.payload);
        state.current = action.payload;
      })
      .addCase(adoptProgram.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload as string;
      });
  },
});

export const {
  setCurrentProgram,
  addExerciseToProgram,
  updateExerciseInProgram,
  removeExerciseFromProgram,
  reorderExercises,
  clearCurrentProgram,
} = workoutProgramsSlice.actions;

export default workoutProgramsSlice.reducer;
