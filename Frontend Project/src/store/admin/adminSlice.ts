import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { fetchAdminStats } from './thunk';

export interface AdminStats {
  totalUsers: number;
  totalTrainers: number;
  totalExercises: number;
  totalEvents: number;
  upcomingEvents: number;
  activeUsers: number;
  totalRoutines: number;
}

interface AdminState {
  stats: AdminStats | null;
  loading: boolean;
  error: string | null;
}

const initialState: AdminState = {
  stats: null,
  loading: false,
  error: null,
};

const adminSlice = createSlice({
  name: 'admin',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchAdminStats.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAdminStats.fulfilled, (state, action) => {
        state.loading = false;
        state.stats = action.payload;
      })
      .addCase(fetchAdminStats.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearError } = adminSlice.actions;
export default adminSlice.reducer;

