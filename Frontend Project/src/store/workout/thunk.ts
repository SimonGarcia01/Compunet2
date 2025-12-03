import { createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch } from '@/services/http';

export interface CompleteExerciseRequest {
  workoutProgramId: number;
  exerciseId: number;
  dateCompletion?: string; // Optional, defaults to today
}

export interface CompleteExerciseResponse {
  workoutProgramId: number;
  exerciseId: number;
  generalProgressId: number;
  dateCompletion: string;
  workoutProgramName: string;
  exerciseName: string;
}

export const completeExercise = createAsyncThunk<
  CompleteExerciseResponse,
  CompleteExerciseRequest,
  { rejectValue: string }
>(
  'workout/completeExercise',
  async (request, { rejectWithValue }) => {
    try {
      // Validar que los IDs sean válidos
      if (!request.workoutProgramId || !request.exerciseId) {
        return rejectWithValue('Workout program ID y Exercise ID son requeridos');
      }

      const payload: any = {
        workoutProgramId: request.workoutProgramId,
        exerciseId: request.exerciseId,
      };

      if (request.dateCompletion) {
        payload.dateCompletion = request.dateCompletion;
      }

      console.log('Enviando petición para completar ejercicio:', payload);

      const res = await apiFetch('/api/v1/complete_exercises', {
        method: 'POST',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        let msg = 'Error al completar ejercicio';
        try {
          const error = await res.json();
          console.error('Error al completar ejercicio:', error);
          msg = error.message || error.error || msg;
        } catch (e) {
          console.error('Error al parsear respuesta de error:', e);
        }
        return rejectWithValue(msg);
      }

      return await res.json();
    } catch (error) {
      return rejectWithValue('Error de conexión');
    }
  }
);

