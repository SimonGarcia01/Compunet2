import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { fetchExercises, createExercise, updateExercise, deleteExercise } from './thunk';

export interface Exercise {
  exerciseId: string;
  name: string;
  type: 'cardio' | 'fuerza' | 'movilidad';
  description: string;
  difficulty: 'baja' | 'media' | 'alta';
  videoUrl?: string;
  progressUnit: 'reps' | 'min' | 'km';
  estimatedUnitaryCaloriesBurnt: number;
  isCustom?: boolean;
}

interface ExercisesState {
  items: Exercise[];
  filteredItems: Exercise[];
  loading: boolean;
  creating: boolean;
  error: string | null;
  filters: {
    search: string;
    type: string | null;
    difficulty: string | null;
    progressUnit: string | null;
    hasVideo: boolean;
  };
  sortBy: 'relevance' | 'name' | 'difficulty';
}

const initialState: ExercisesState = {
  items: [],
  filteredItems: [],
  loading: false,
  creating: false,
  error: null,
  filters: {
    search: '',
    type: null,
    difficulty: null,
    progressUnit: null,
    hasVideo: false,
  },
  sortBy: 'relevance',
};

const exercisesSlice = createSlice({
  name: 'exercises',
  initialState,
  reducers: {
    setFilters: (state, action: PayloadAction<Partial<ExercisesState['filters']>>) => {
      state.filters = { ...state.filters, ...action.payload };
      applyFilters(state);
    },
    setSortBy: (state, action: PayloadAction<ExercisesState['sortBy']>) => {
      state.sortBy = action.payload;
      applySorting(state);
    },
    clearFilters: (state) => {
      state.filters = initialState.filters;
      state.filteredItems = state.items;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchExercises.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchExercises.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
        applyFilters(state);
      })
      .addCase(fetchExercises.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(createExercise.pending, (state) => {
        state.creating = true;
        state.error = null;
      })
      .addCase(createExercise.fulfilled, (state, action) => {
        state.creating = false;
        state.items.push(action.payload);
        applyFilters(state);
      })
      .addCase(createExercise.rejected, (state, action) => {
        state.creating = false;
        state.error = action.payload as string;
      })
      .addCase(updateExercise.fulfilled, (state, action) => {
        const index = state.items.findIndex(e => e.exerciseId === action.payload.exerciseId);
        if (index !== -1) {
          state.items[index] = action.payload;
          applyFilters(state);
        }
      })
      .addCase(deleteExercise.fulfilled, (state, action) => {
        state.items = state.items.filter(e => e.exerciseId !== action.payload);
        applyFilters(state);
      });
  },
});

function applyFilters(state: ExercisesState) {
  let filtered = [...state.items];

  if (state.filters.search) {
    const search = state.filters.search.toLowerCase();
    filtered = filtered.filter(e => 
      e.name.toLowerCase().includes(search) ||
      e.description.toLowerCase().includes(search)
    );
  }

  if (state.filters.type) {
    filtered = filtered.filter(e => e.type === state.filters.type);
  }

  if (state.filters.difficulty) {
    filtered = filtered.filter(e => e.difficulty === state.filters.difficulty);
  }

  if (state.filters.progressUnit) {
    filtered = filtered.filter(e => e.progressUnit === state.filters.progressUnit);
  }

  if (state.filters.hasVideo) {
    filtered = filtered.filter(e => e.videoUrl && e.videoUrl.trim() !== '');
  }

  state.filteredItems = filtered;
  applySorting(state);
}

function applySorting(state: ExercisesState) {
  if (state.sortBy === 'name') {
    state.filteredItems.sort((a, b) => a.name.localeCompare(b.name));
  } else if (state.sortBy === 'difficulty') {
    const diffOrder = { baja: 0, media: 1, alta: 2 };
    state.filteredItems.sort((a, b) => diffOrder[a.difficulty] - diffOrder[b.difficulty]);
  }
}

export const { setFilters, setSortBy, clearFilters } = exercisesSlice.actions;
export default exercisesSlice.reducer;
