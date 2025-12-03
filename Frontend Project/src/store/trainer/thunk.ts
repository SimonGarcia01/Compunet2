import { createAsyncThunk } from '@reduxjs/toolkit';
import type { RootState } from '../index';
import type { Student, WorkoutProgram, ExerciseProgress, ProgressFilters } from './trainerSlice';
import { apiFetch } from '@/services/http';

const API_BASE = '/api/v1';

interface TrainerTraineeResponse {
  id: number; // Backend retorna Integer
  trainer: {
    email: string;
    name?: string;
  };
  trainee: {
    email: string;
    name?: string;
    photoUrl?: string;
  };
  startDate: string;
  endDate?: string;
}

export const fetchMyStudents = createAsyncThunk<
  Student[],
  void,
  { state: RootState; rejectValue: string }
>(
  'trainer/fetchMyStudents',
  async (_, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const trainerEmail = state.auth.user?.email;

      if (!trainerEmail) {
        return rejectWithValue('No se encontró el email del entrenador');
      }

      // Fetch all trainer-trainee relationships
      const res = await apiFetch(`${API_BASE}/trainers_trainees`);
      
      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener relaciones entrenador-estudiante');
      }

      const data: TrainerTraineeResponse[] = await res.json();

      // Filter by trainer email and map to Student interface
      const students: Student[] = data
        .filter((relation) => relation.trainer.email === trainerEmail)
        .map((relation) => ({
          id: String(relation.id), // Convertir a string para el frontend
          email: relation.trainee.email,
          name: relation.trainee.name,
          photoUrl: relation.trainee.photoUrl,
          startDate: relation.startDate,
          endDate: relation.endDate,
          isActive: !relation.endDate || new Date(relation.endDate) > new Date()
        }));

      return students;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener estudiantes');
    }
  }
);

export const fetchStudentWorkouts = createAsyncThunk<
  { studentEmail: string; workouts: WorkoutProgram[] },
  { studentEmail: string; filters?: { completed?: boolean; sortBy?: string } },
  { rejectValue: string }
>(
  'trainer/fetchStudentWorkouts',
  async ({ studentEmail, filters }, { rejectWithValue }) => {
    try {
      // Fetch all workout programs
      const res = await apiFetch(`${API_BASE}/workout_programs`);
      
      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener rutinas');
      }

      const data: WorkoutProgram[] = await res.json();

      // Filter by student email
      // Nota: El backend retorna workoutId como Integer, pero el frontend puede manejarlo como string
      let workouts = data
        .filter((workout) => workout.user.email === studentEmail)
        .map((workout) => ({
          ...workout,
          workoutId: String(workout.workoutId), // Convertir a string para consistencia en el frontend
        }));

      // Apply additional filters
      if (filters?.completed !== undefined) {
        workouts = workouts.filter((w) => w.completed === filters.completed);
      }

      // Apply sorting
      if (filters?.sortBy === 'date') {
        workouts.sort((a, b) => 
          new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime()
        );
      } else if (filters?.sortBy === 'name') {
        workouts.sort((a, b) => a.name.localeCompare(b.name));
      }

      return { studentEmail, workouts };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener rutinas');
    }
  }
);

export const fetchStudentWorkoutDetails = createAsyncThunk<
  WorkoutProgram,
  { workoutId: string; studentEmail: string },
  { rejectValue: string }
>(
  'trainer/fetchStudentWorkoutDetails',
  async ({ workoutId, studentEmail }, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/workout_programs/${workoutId}`);
      
      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener detalles de rutina');
      }

      const data: WorkoutProgram = await res.json();

      // Validate that the workout belongs to the student
      if (data.user.email !== studentEmail) {
        return rejectWithValue('Esta rutina no pertenece al estudiante seleccionado');
      }

      // Convertir workoutId a string para consistencia en el frontend
      return {
        ...data,
        workoutId: String(data.workoutId),
      };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener detalles de rutina');
    }
  }
);

// Fetch all available students (users with role "Usuario")
export const fetchAllStudents = createAsyncThunk<
  Array<{ email: string; name?: string; photoUrl?: string }>,
  void,
  { state: RootState; rejectValue: string }
>(
  'trainer/fetchAllStudents',
  async (_, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/users`);
      
      if (!res.ok) {
        // Si no tiene permisos, retornar array vacío en lugar de error
        if (res.status === 403 || res.status === 401) {
          console.warn('No se tienen permisos para obtener la lista de usuarios');
          return [];
        }
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener estudiantes');
      }

      const users: any[] = await res.json();
      
      // Retornar todos los usuarios con email, name, photoUrl
      // El backend validará el rol al crear la relación
      return users
        .filter((user: any) => user.email) // Solo usuarios con email válido
        .map((user: any) => ({
          email: user.email,
          name: user.name || user.email.split('@')[0], // Usar email como fallback si no hay name
          photoUrl: user.photoUrl,
        }))
        .sort((a, b) => {
          // Ordenar por nombre si existe, sino por email
          const nameA = a.name || a.email;
          const nameB = b.name || b.email;
          return nameA.localeCompare(nameB);
        });
    } catch (error) {
      console.error('Error al obtener estudiantes:', error);
      // Retornar array vacío en lugar de error para no bloquear la UI
      return [];
    }
  }
);

export const createTrainerTrainee = createAsyncThunk<
  Student,
  { traineeEmail: string; startDate: string; endDate?: string },
  { state: RootState; rejectValue: string }
>(
  'trainer/createTrainerTrainee',
  async ({ traineeEmail, startDate, endDate }, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const trainerEmail = state.auth.user?.email;

      if (!trainerEmail) {
        return rejectWithValue('No se encontró el email del entrenador');
      }

      // Construir el payload según TrainerTraineeRequest
      // El backend espera UserRequest para trainer y trainee, pero solo necesita email
      const payload = {
        trainer: {
          email: trainerEmail,
        },
        trainee: {
          email: traineeEmail,
        },
        startDate: startDate, // Formato YYYY-MM-DD (LocalDate)
        endDate: endDate || null, // Opcional
      };

      const res = await apiFetch(`${API_BASE}/trainers_trainees`, {
        method: 'POST',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al crear relación entrenador-estudiante');
      }

      // El backend retorna MsgResp, pero necesitamos refrescar la lista
      // Por ahora retornamos un Student temporal, luego se refrescará la lista
      const response = await res.json();
      
      // Recargar la lista de estudiantes después de crear
      // Retornamos un Student temporal que se actualizará cuando se recargue la lista
      return {
        id: 'temp',
        email: traineeEmail,
        startDate: startDate,
        endDate: endDate,
        isActive: !endDate || new Date(endDate) > new Date(),
      };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al crear relación entrenador-estudiante');
    }
  }
);

export const fetchStudentProgress = createAsyncThunk<
  { studentEmail: string; progress: ExerciseProgress[] },
  { studentEmail: string; filters?: ProgressFilters },
  { rejectValue: string }
>(
  'trainer/fetchStudentProgress',
  async ({ studentEmail, filters }, { rejectWithValue }) => {
    try {
      // Build query params
      const params = new URLSearchParams();
      if (filters?.startDate) params.append('startDate', filters.startDate);
      if (filters?.endDate) params.append('endDate', filters.endDate);
      if (filters?.workoutId) params.append('workoutId', filters.workoutId);

      // Fetch progress data
      // Usar endpoint /student/{email} para entrenadores que quieren ver progreso de estudiantes
      const url = `${API_BASE}/exercise_progress/student/${encodeURIComponent(studentEmail)}${params.toString() ? `?${params.toString()}` : ''}`;
      const res = await apiFetch(url);

      if (!res.ok) {
        // Si el endpoint no existe o hay error
        if (res.status === 404 || res.status === 0 || (res.status >= 300 && res.status < 400)) {
          console.warn('Endpoint /exercise_progress/student/{email} no disponible, retornando array vacío');
          return { studentEmail, progress: [] };
        }
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener progreso');
      }

      const data: ExerciseProgress[] = await res.json();

      // El endpoint /student/{email} ya retorna solo los registros del estudiante
      // No necesitamos filtrar por email, pero lo hacemos por seguridad
      let progress = data.filter((record) => record.user?.email === studentEmail);

      // Apply client-side filters
      if (filters?.exerciseId) {
        progress = progress.filter((p) => p.exercise?.exerciseId === filters.exerciseId);
      }
      if (filters?.periodType) {
        progress = progress.filter((p) => p.periodType === filters.periodType);
      }
      if (filters?.minRpe !== undefined) {
        progress = progress.filter((p) => (p.rpe ?? 0) >= filters.minRpe!);
      }
      if (filters?.maxRpe !== undefined) {
        progress = progress.filter((p) => (p.rpe ?? 0) <= filters.maxRpe!);
      }
      if (filters?.minCalories !== undefined) {
        progress = progress.filter((p) => (p.estimatedCaloriesBurnt ?? 0) >= filters.minCalories!);
      }

      // Sort by date descending
      progress.sort((a, b) => 
        new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime()
      );

      return { studentEmail, progress };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener progreso');
    }
  }
);
