import { createAsyncThunk } from '@reduxjs/toolkit';
import type { RootState } from '../index';
import type { ExerciseProgress, ProgressFormData } from './userProgressSlice';
import { apiFetch } from '@/services/http';

const API_BASE = '/api/v1';

// Función para mapear datos del backend al frontend
function mapBackendProgressToFrontend(backendProgress: any): ExerciseProgress {
  return {
    progressId: String(backendProgress.progressId || ''),
    user: backendProgress.user ? {
      email: backendProgress.user.email || '',
      name: backendProgress.user.name,
    } : undefined,
    workoutProgram: backendProgress.workoutProgram ? {
      workoutId: String(backendProgress.workoutProgram.workoutId || ''),
      name: backendProgress.workoutProgram.name || '',
    } : undefined,
    exercise: backendProgress.exercise ? {
      exerciseId: String(backendProgress.exercise.exerciseId || ''),
      name: backendProgress.exercise.name || '',
      type: backendProgress.exercise.type || '',
    } : undefined,
    recordDate: backendProgress.recordDate || new Date().toISOString().split('T')[0],
    periodType: (backendProgress.periodType === 'DAILY' || backendProgress.periodType === 'WEEKLY') 
      ? backendProgress.periodType 
      : 'DAILY',
    repetitions: backendProgress.repetitions,
    timeMinutes: backendProgress.timeMinutes,
    distanceKm: backendProgress.distanceKm ? Number(backendProgress.distanceKm) : undefined,
    rpe: backendProgress.rpe,
    notes: backendProgress.notes,
    estimatedCaloriesBurnt: backendProgress.estimatedCaloriesBurnt ? Number(backendProgress.estimatedCaloriesBurnt) : undefined,
  };
}

// Fetch my progress
export const fetchMyProgress = createAsyncThunk<
  ExerciseProgress[],
  void,
  { state: RootState; rejectValue: string }
>(
  'userProgress/fetchMy',
  async (_, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/exercise_progress/my`);

      if (!res.ok) {
        if (res.status === 0 || res.status === 404 || (res.status >= 300 && res.status < 400)) {
          // Si no hay progreso o el endpoint no está disponible, retornar array vacío
          console.log('No se encontró progreso o endpoint no disponible');
          return [];
        }
        const error = await res.json().catch(() => ({ message: res.statusText }));
        console.error('Error al obtener progreso:', error);
        return rejectWithValue(error.message || 'Error al obtener progreso');
      }

      const data = await res.json();
      console.log('Datos de progreso recibidos:', data);
      
      // Asegurar que siempre retornamos un array
      if (!Array.isArray(data)) {
        console.error('Expected array but got:', data);
        return [];
      }
      
      // Mapear cada elemento del backend al formato del frontend
      return data.map(mapBackendProgressToFrontend);
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener progreso');
    }
  }
);

// Create progress
export const createProgress = createAsyncThunk<
  ExerciseProgress | null,
  ProgressFormData,
  { state: RootState; rejectValue: string }
>(
  'userProgress/create',
  async (formData, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const userEmail = state.auth.user?.email;

      if (!userEmail) {
        return rejectWithValue('No se encontró el email del usuario');
      }

      // Construir el payload según ExerciseProgressRequest
      const payload: any = {
        recordDate: formData.recordDate,
        periodType: formData.periodType,
      };

      // Agregar workoutId si está presente
      if (formData.workoutId) {
        payload.workoutId = parseInt(formData.workoutId, 10);
      }

      // Agregar exerciseId si está presente
      if (formData.exerciseId) {
        payload.exerciseId = parseInt(formData.exerciseId, 10);
      }

      // Agregar campos opcionales
      if (formData.repetitions !== undefined && formData.repetitions !== null) {
        payload.repetitions = formData.repetitions;
      }
      if (formData.timeMinutes !== undefined && formData.timeMinutes !== null) {
        payload.timeMinutes = formData.timeMinutes;
      }
      if (formData.distanceKm !== undefined && formData.distanceKm !== null) {
        payload.distanceKm = formData.distanceKm;
      }
      if (formData.rpe !== undefined && formData.rpe !== null) {
        payload.rpe = formData.rpe;
      }
      if (formData.notes) {
        payload.notes = formData.notes.trim();
      }
      if (formData.estimatedCaloriesBurnt !== undefined && formData.estimatedCaloriesBurnt !== null) {
        payload.estimatedCaloriesBurnt = formData.estimatedCaloriesBurnt;
      }

      const res = await apiFetch(`${API_BASE}/exercise_progress`, {
        method: 'POST',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al crear registro de progreso');
      }

      // El backend retorna MsgResp, pero necesitamos refrescar la lista
      // Retornamos null y el componente deberá refrescar la lista
      return null;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al crear registro de progreso');
    }
  }
);

// Update progress
export const updateProgress = createAsyncThunk<
  ExerciseProgress,
  { progressId: string; formData: ProgressFormData },
  { state: RootState; rejectValue: string }
>(
  'userProgress/update',
  async ({ progressId, formData }, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const userEmail = state.auth.user?.email;

      if (!userEmail) {
        return rejectWithValue('No se encontró el email del usuario');
      }

      // Construir el payload igual que en create
      const payload: any = {
        recordDate: formData.recordDate,
        periodType: formData.periodType,
      };

      if (formData.workoutId) {
        payload.workoutId = parseInt(formData.workoutId, 10);
      }
      if (formData.exerciseId) {
        payload.exerciseId = parseInt(formData.exerciseId, 10);
      }
      if (formData.repetitions !== undefined && formData.repetitions !== null) {
        payload.repetitions = formData.repetitions;
      }
      if (formData.timeMinutes !== undefined && formData.timeMinutes !== null) {
        payload.timeMinutes = formData.timeMinutes;
      }
      if (formData.distanceKm !== undefined && formData.distanceKm !== null) {
        payload.distanceKm = formData.distanceKm;
      }
      if (formData.rpe !== undefined && formData.rpe !== null) {
        payload.rpe = formData.rpe;
      }
      if (formData.notes) {
        payload.notes = formData.notes.trim();
      }
      if (formData.estimatedCaloriesBurnt !== undefined && formData.estimatedCaloriesBurnt !== null) {
        payload.estimatedCaloriesBurnt = formData.estimatedCaloriesBurnt;
      }

      const res = await apiFetch(`${API_BASE}/exercise_progress/${progressId}`, {
        method: 'PUT',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al actualizar registro de progreso');
      }

      // El backend retorna MsgResp, necesitamos obtener el registro actualizado
      // Por ahora retornamos un objeto temporal que se actualizará cuando se recargue la lista
      return {
        progressId,
        recordDate: formData.recordDate,
        periodType: formData.periodType,
        repetitions: formData.repetitions,
        timeMinutes: formData.timeMinutes,
        distanceKm: formData.distanceKm,
        rpe: formData.rpe,
        notes: formData.notes,
        estimatedCaloriesBurnt: formData.estimatedCaloriesBurnt,
      } as ExerciseProgress;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al actualizar registro de progreso');
    }
  }
);

// Delete progress
export const deleteProgress = createAsyncThunk<
  string,
  string,
  { rejectValue: string }
>(
  'userProgress/delete',
  async (progressId, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/exercise_progress/${progressId}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al eliminar registro de progreso');
      }

      return progressId;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al eliminar registro de progreso');
    }
  }
);

