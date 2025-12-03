import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {
  fetchMyStudents,
  fetchStudentWorkouts,
  fetchStudentProgress,
  fetchStudentWorkoutDetails,
  createTrainerTrainee,
  fetchAllStudents
} from './thunk';

export interface Student {
  id: string;
  email: string;
  name?: string;
  photoUrl?: string;
  startDate: string;
  endDate?: string;
  isActive: boolean;
}

export interface WorkoutProgram {
  workoutId: number | string; // Backend retorna Integer, frontend puede usar string
  name: string;
  description: string;
  photoUrl?: string;
  creationDate: string; // Backend retorna LocalDate (YYYY-MM-DD)
  completed: boolean;
  user: {
    email: string;
    name?: string;
    photoUrl?: string; // Backend incluye photoUrl en UserResponse
  };
  exercises?: WorkoutExercise[];
}

export interface WorkoutExercise {
  workoutProgramId: string;
  exerciseId: string;
  series: number;
  session: number;
  amount: number;
  exercise?: {
    exerciseId: string;
    name: string;
    type: string;
    difficulty: string;
    progressUnit: string;
  };
}

export interface ExerciseProgress {
  progressId: string;
  user: { email: string; name?: string };
  workoutProgram?: { workoutId: string; name: string };
  exercise?: { exerciseId: string; name: string; type: string };
  recordDate: string;
  periodType: 'DAILY' | 'WEEKLY';
  repetitions?: number;
  timeMinutes?: number;
  distanceKm?: number;
  rpe?: number;
  notes?: string;
  estimatedCaloriesBurnt?: number;
}

export interface ProgressFilters {
  startDate?: string;
  endDate?: string;
  workoutId?: string;
  exerciseId?: string;
  periodType?: 'DAILY' | 'WEEKLY';
  minRpe?: number;
  maxRpe?: number;
  minCalories?: number;
}

interface TrainerState {
  students: Student[];
  availableStudents: Array<{ email: string; name?: string; photoUrl?: string }>;
  availableStudentsLoading: boolean;
  selectedStudent: Student | null;
  studentWorkouts: Record<string, WorkoutProgram[]>;
  currentWorkout: WorkoutProgram | null;
  studentProgress: Record<string, ExerciseProgress[]>;
  filters: ProgressFilters;
  loading: boolean;
  error: string | null;
  selectedStudentsForComparison: string[];
}

const initialState: TrainerState = {
  students: [],
  availableStudents: [],
  availableStudentsLoading: false,
  selectedStudent: null,
  studentWorkouts: {},
  currentWorkout: null,
  studentProgress: {},
  filters: {},
  loading: false,
  error: null,
  selectedStudentsForComparison: []
};

const trainerSlice = createSlice({
  name: 'trainer',
  initialState,
  reducers: {
    setSelectedStudent: (state, action: PayloadAction<Student | null>) => {
      state.selectedStudent = action.payload;
    },
    setFilters: (state, action: PayloadAction<ProgressFilters>) => {
      state.filters = action.payload;
    },
    clearFilters: (state) => {
      state.filters = {};
    },
    toggleStudentForComparison: (state, action: PayloadAction<string>) => {
      const email = action.payload;
      const index = state.selectedStudentsForComparison.indexOf(email);
      if (index > -1) {
        state.selectedStudentsForComparison.splice(index, 1);
      } else {
        state.selectedStudentsForComparison.push(email);
      }
    },
    clearStudentComparison: (state) => {
      state.selectedStudentsForComparison = [];
    }
  },
  extraReducers: (builder) => {
    // fetchMyStudents
    builder
      .addCase(fetchMyStudents.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchMyStudents.fulfilled, (state, action) => {
        state.loading = false;
        state.students = action.payload;
      })
      .addCase(fetchMyStudents.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchStudentWorkouts
    builder
      .addCase(fetchStudentWorkouts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchStudentWorkouts.fulfilled, (state, action) => {
        state.loading = false;
        const { studentEmail, workouts } = action.payload;
        state.studentWorkouts[studentEmail] = workouts;
      })
      .addCase(fetchStudentWorkouts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchStudentWorkoutDetails
    builder
      .addCase(fetchStudentWorkoutDetails.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchStudentWorkoutDetails.fulfilled, (state, action) => {
        state.loading = false;
        state.currentWorkout = action.payload;
      })
      .addCase(fetchStudentWorkoutDetails.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchStudentProgress
    builder
      .addCase(fetchStudentProgress.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchStudentProgress.fulfilled, (state, action) => {
        state.loading = false;
        const { studentEmail, progress } = action.payload;
        state.studentProgress[studentEmail] = progress;
      })
      .addCase(fetchStudentProgress.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // createTrainerTrainee
    builder
      .addCase(createTrainerTrainee.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createTrainerTrainee.fulfilled, (state, action) => {
        state.loading = false;
        // No agregamos directamente, mejor refrescar la lista completa
        // Esto se manejará en el componente llamando fetchMyStudents después
      })
      .addCase(createTrainerTrainee.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // fetchAllStudents
    builder
      .addCase(fetchAllStudents.pending, (state) => {
        state.availableStudentsLoading = true;
      })
      .addCase(fetchAllStudents.fulfilled, (state, action) => {
        state.availableStudentsLoading = false;
        state.availableStudents = action.payload;
      })
      .addCase(fetchAllStudents.rejected, (state) => {
        state.availableStudentsLoading = false;
        state.availableStudents = [];
      });
  }
});

export const {
  setSelectedStudent,
  setFilters,
  clearFilters,
  toggleStudentForComparison,
  clearStudentComparison
} = trainerSlice.actions;

export default trainerSlice.reducer;
