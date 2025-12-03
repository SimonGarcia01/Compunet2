import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {
  fetchStudentRecommendations,
  fetchAllRecommendations,
  fetchMyRecommendations,
  createRecommendation,
  updateRecommendation,
  deleteRecommendation,
  analyzeStudentProgress
} from './thunk';

export interface GeneralProgress {
  progressId: number;
  type: string;
  percentage: number;
  daysOrWeeks: string;
}

export interface Recommendation {
  recommendationId: number;
  content: string;
  commentDate: string;
  generalProgress?: GeneralProgress;
  trainer?: {
    email: string;
    name?: string;
  };
}

export interface ProgressAnalysisResult {
  avgRpe: number;
  totalCalories: number;
  activeDays: number;
  mostUsedWorkout: string;
  lastActivity: string;
  trends: {
    rpeIncreasing: boolean;
    caloriesIncreasing: boolean;
    frequencyDecreasing: boolean;
  };
  recommendations: string[];
}

interface RecommendationsState {
  recommendations: Recommendation[];
  myRecommendations: Recommendation[];
  studentRecommendations: Record<string, Recommendation[]>;
  currentRecommendation: Recommendation | null;
  progressAnalysis: Record<string, ProgressAnalysisResult>;
  loading: boolean;
  error: string | null;
  filters: {
    startDate?: string;
    endDate?: string;
    searchTerm?: string;
  };
}

const initialState: RecommendationsState = {
  recommendations: [],
  myRecommendations: [],
  studentRecommendations: {},
  currentRecommendation: null,
  progressAnalysis: {},
  loading: false,
  error: null,
  filters: {}
};

const recommendationsSlice = createSlice({
  name: 'recommendations',
  initialState,
  reducers: {
    setFilters: (state, action: PayloadAction<{ startDate?: string; endDate?: string; searchTerm?: string }>) => {
      state.filters = { ...state.filters, ...action.payload };
    },
    clearFilters: (state) => {
      state.filters = {};
    },
    setCurrentRecommendation: (state, action: PayloadAction<Recommendation | null>) => {
      state.currentRecommendation = action.payload;
    }
  },
  extraReducers: (builder) => {
    // fetchStudentRecommendations
    builder
      .addCase(fetchStudentRecommendations.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchStudentRecommendations.fulfilled, (state, action) => {
        state.loading = false;
        const { studentEmail, recommendations } = action.payload;
        state.studentRecommendations[studentEmail] = recommendations;
      })
      .addCase(fetchStudentRecommendations.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchAllRecommendations
    builder
      .addCase(fetchAllRecommendations.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllRecommendations.fulfilled, (state, action) => {
        state.loading = false;
        state.recommendations = action.payload;
      })
      .addCase(fetchAllRecommendations.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchMyRecommendations
    builder
      .addCase(fetchMyRecommendations.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchMyRecommendations.fulfilled, (state, action) => {
        state.loading = false;
        state.myRecommendations = action.payload;
      })
      .addCase(fetchMyRecommendations.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // createRecommendation
    builder
      .addCase(createRecommendation.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createRecommendation.fulfilled, (state, action) => {
        state.loading = false;
        state.recommendations.push(action.payload);
      })
      .addCase(createRecommendation.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // updateRecommendation
    builder
      .addCase(updateRecommendation.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(updateRecommendation.fulfilled, (state, action) => {
        state.loading = false;
        const index = state.recommendations.findIndex(
          (r) => r.recommendationId === action.payload.recommendationId
        );
        if (index !== -1) {
          state.recommendations[index] = action.payload;
        }
      })
      .addCase(updateRecommendation.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // deleteRecommendation
    builder
      .addCase(deleteRecommendation.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(deleteRecommendation.fulfilled, (state, action) => {
        state.loading = false;
        state.recommendations = state.recommendations.filter(
          (r) => r.recommendationId !== action.payload
        );
      })
      .addCase(deleteRecommendation.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // analyzeStudentProgress
    builder
      .addCase(analyzeStudentProgress.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(analyzeStudentProgress.fulfilled, (state, action) => {
        state.loading = false;
        const { studentEmail, analysis } = action.payload;
        state.progressAnalysis[studentEmail] = analysis;
      })
      .addCase(analyzeStudentProgress.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  }
});

export const { setFilters, clearFilters, setCurrentRecommendation } = recommendationsSlice.actions;
export default recommendationsSlice.reducer;
