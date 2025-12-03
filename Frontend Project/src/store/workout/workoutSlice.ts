import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { completeExercise } from './thunk';

interface WorkoutState {
  loading: boolean;
  error: string | null;
}

const initialState: WorkoutState = {
  loading: false,
  error: null,
};

const workoutSlice = createSlice({
  name: 'workout',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(completeExercise.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(completeExercise.fulfilled, (state) => {
        state.loading = false;
      })
      .addCase(completeExercise.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearError } = workoutSlice.actions;
export default workoutSlice.reducer;

