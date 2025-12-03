import { createAsyncThunk } from '@reduxjs/toolkit';
import type { RootState, AppDispatch } from '../index';
import type { Recommendation, ProgressAnalysisResult } from './recommendationsSlice';
import { apiFetch } from '@/services/http';

const API_BASE = '/api/v1';

// Obtener recomendaciones del usuario autenticado
export const fetchMyRecommendations = createAsyncThunk<
  Recommendation[],
  void,
  { state: RootState; rejectValue: string }
>(
  'recommendations/fetchMyRecommendations',
  async (_, { rejectWithValue }) => {
    try {
      console.log('Obteniendo recomendaciones del usuario...');
      const res = await apiFetch(`${API_BASE}/recommendations/my`);
      
      console.log('Respuesta de recomendaciones:', {
        status: res.status,
        ok: res.ok,
        statusText: res.statusText,
      });

      if (!res.ok) {
        const errorText = await res.text();
        let error;
        try {
          error = JSON.parse(errorText);
        } catch {
          error = { message: errorText || res.statusText };
        }
        console.error('Error al obtener recomendaciones:', error);
        return rejectWithValue(error.message || 'Error al obtener recomendaciones');
      }

      const data: Recommendation[] = await res.json();
      console.log('Recomendaciones recibidas:', data.length, 'recomendaciones');
      
      // Asegurar que siempre retornamos un array
      if (!Array.isArray(data)) {
        console.error('Expected array but got:', data);
        return [];
      }

      return data.sort((a, b) => {
        try {
          return new Date(b.commentDate).getTime() - new Date(a.commentDate).getTime();
        } catch {
          return 0;
        }
      });
    } catch (error) {
      console.error('Error completo al obtener recomendaciones:', error);
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener recomendaciones');
    }
  }
);

export const fetchStudentRecommendations = createAsyncThunk<
  { studentEmail: string; recommendations: Recommendation[] },
  { studentEmail: string },
  { state: RootState; rejectValue: string }
>(
  'recommendations/fetchStudentRecommendations',
  async ({ studentEmail }, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const trainerEmail = state.auth.user?.email;

      if (!trainerEmail) {
        return rejectWithValue('No se encontró el email del entrenador');
      }

      const res = await apiFetch(`${API_BASE}/recommendations`);
      
      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener recomendaciones');
      }

      const data: Recommendation[] = await res.json();

      // Filter recommendations by trainer
      const recommendations = data
        .filter((rec) => rec.trainer?.email === trainerEmail)
        .sort((a, b) => new Date(b.commentDate).getTime() - new Date(a.commentDate).getTime());

      return { studentEmail, recommendations };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener recomendaciones');
    }
  }
);

export const fetchAllRecommendations = createAsyncThunk<
  Recommendation[],
  void,
  { state: RootState; rejectValue: string }
>(
  'recommendations/fetchAllRecommendations',
  async (_, { getState, rejectWithValue }) => {
    try {
      const state = getState();
      const trainerEmail = state.auth.user?.email;

      if (!trainerEmail) {
        return rejectWithValue('No se encontró el email del entrenador');
      }

      const res = await apiFetch(`${API_BASE}/recommendations`);
      
      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al obtener recomendaciones');
      }

      const data: Recommendation[] = await res.json();

      // Filter by trainer email
      return data
        .filter((rec) => rec.trainer?.email === trainerEmail)
        .sort((a, b) => new Date(b.commentDate).getTime() - new Date(a.commentDate).getTime());
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al obtener recomendaciones');
    }
  }
);

export const createRecommendation = createAsyncThunk<
  Recommendation,
  { content: string; studentEmail: string },
  { state: RootState; rejectValue: string; dispatch: AppDispatch }
>(
  'recommendations/createRecommendation',
  async ({ content, studentEmail }, { getState, rejectWithValue, dispatch }) => {
    try {
      const state = getState();
      const trainerEmail = state.auth.user?.email;

      if (!trainerEmail) {
        return rejectWithValue('No se encontró el email del entrenador');
      }

      // Validate that student is assigned to trainer
      try {
        const relationsRes = await apiFetch(`${API_BASE}/trainers_trainees`);
        if (!relationsRes.ok) {
          // Si no se pueden obtener las relaciones, continuar de todas formas
          // El backend validará la relación al crear la recomendación
          console.warn('No se pudieron verificar las relaciones entrenador-estudiante');
        } else {
          const relations = await relationsRes.json();
          const isAssigned = relations.some(
            (rel: any) => rel.trainer?.email === trainerEmail && rel.trainee?.email === studentEmail
          );

          if (!isAssigned) {
            return rejectWithValue('Este estudiante no está asignado a tu cuenta. Por favor, verifica que el estudiante esté vinculado a tu perfil de entrenador.');
          }
        }
      } catch (error) {
        // Si falla la validación, continuar de todas formas
        // El backend validará la relación al crear la recomendación
        console.warn('Error al validar relación entrenador-estudiante:', error);
      }

      // Obtener el GeneralProgress del estudiante para vincular la recomendación
      // Primero intentamos obtener el progreso del estudiante
      let generalProgressId = null;
      try {
        const progressRes = await apiFetch(`${API_BASE}/exercise_progress/student/${encodeURIComponent(studentEmail)}`);
        if (progressRes.ok) {
          const progressData = await progressRes.json();
          // Si hay datos de progreso, obtener el GeneralProgressId del primer registro
          if (Array.isArray(progressData) && progressData.length > 0 && progressData[0].generalProgressId) {
            generalProgressId = progressData[0].generalProgressId;
          }
        }
      } catch (error) {
        console.warn('No se pudo obtener el GeneralProgress del estudiante, se usará traineeEmail');
      }

      const res = await apiFetch(`${API_BASE}/recommendations`, {
        method: 'POST',
        body: JSON.stringify({ 
          content, 
          generalProgressId: generalProgressId,
          traineeEmail: studentEmail // Enviar el email del estudiante para vinculación automática
        })
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al crear recomendación');
      }

      // El backend retorna MsgResp, pero necesitamos refrescar la lista
      // Recargar recomendaciones después de crear
      // Nota: El componente padre debería refrescar la lista después de crear

      // Retornar una recomendación temporal (se actualizará con la lista)
      return {
        recommendationId: 0,
        content: content.trim(),
        commentDate: new Date().toISOString().split('T')[0],
        trainer: {
          email: trainerEmail
        }
      };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al crear recomendación');
    }
  }
);

export const updateRecommendation = createAsyncThunk<
  Recommendation,
  { recommendationId: number; content: string },
  { rejectValue: string }
>(
  'recommendations/updateRecommendation',
  async ({ recommendationId, content }, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/recommendations/${recommendationId}`, {
        method: 'PUT',
        body: JSON.stringify({ content })
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al actualizar recomendación');
      }

      // El backend retorna MsgResp, necesitamos obtener la recomendación actualizada
      const updatedRes = await apiFetch(`${API_BASE}/recommendations/${recommendationId}`);
      if (!updatedRes.ok) {
        return rejectWithValue('Error al obtener recomendación actualizada');
      }
      const data = await updatedRes.json();
      return data;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al actualizar recomendación');
    }
  }
);

export const deleteRecommendation = createAsyncThunk<
  number,
  number,
  { rejectValue: string }
>(
  'recommendations/deleteRecommendation',
  async (recommendationId, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`${API_BASE}/recommendations/${recommendationId}`, {
        method: 'DELETE'
      });

      if (!res.ok) {
        const error = await res.json().catch(() => ({ message: res.statusText }));
        return rejectWithValue(error.message || 'Error al eliminar recomendación');
      }

      return recommendationId;
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al eliminar recomendación');
    }
  }
);

export const analyzeStudentProgress = createAsyncThunk<
  { studentEmail: string; analysis: ProgressAnalysisResult },
  { studentEmail: string },
  { rejectValue: string }
>(
  'recommendations/analyzeStudentProgress',
  async ({ studentEmail }, { rejectWithValue }) => {
    try {
      // Fetch student progress using the student-specific endpoint
      // This endpoint is for trainers to view their students' progress
      const res = await apiFetch(`${API_BASE}/exercise_progress/student/${encodeURIComponent(studentEmail)}`);
      
      if (!res.ok) {
        if (res.status === 0 || res.status === 404 || (res.status >= 300 && res.status < 400)) {
          console.warn('Endpoint /exercise_progress/student/{email} no disponible o estudiante no encontrado');
          return {
            studentEmail,
            analysis: {
              avgRpe: 0,
              totalCalories: 0,
              activeDays: 0,
              mostUsedWorkout: 'N/A',
              lastActivity: 'Sin actividad',
              trends: {
                rpeIncreasing: false,
                caloriesIncreasing: false,
                frequencyDecreasing: false
              },
              recommendations: ['El estudiante no ha registrado actividad aún']
            }
          };
        }
        // Si hay error, retornar análisis vacío en lugar de fallar
        return {
          studentEmail,
          analysis: {
            avgRpe: 0,
            totalCalories: 0,
            activeDays: 0,
            mostUsedWorkout: 'N/A',
            lastActivity: 'Sin actividad',
            trends: {
              rpeIncreasing: false,
              caloriesIncreasing: false,
              frequencyDecreasing: false
            },
            recommendations: ['No se pudo obtener datos de progreso']
          }
        };
      }

      const progressData: any[] = await res.json();
      // El endpoint ya retorna solo los registros del estudiante, no necesitamos filtrar
      const studentProgress = progressData;

      if (studentProgress.length === 0) {
        return {
          studentEmail,
          analysis: {
            avgRpe: 0,
            totalCalories: 0,
            activeDays: 0,
            mostUsedWorkout: 'N/A',
            lastActivity: 'Sin actividad',
            trends: {
              rpeIncreasing: false,
              caloriesIncreasing: false,
              frequencyDecreasing: false
            },
            recommendations: ['El estudiante no ha registrado actividad aún']
          }
        };
      }

      // Calculate metrics
      const avgRpe = studentProgress.reduce((sum: number, p: any) => sum + (p.rpe || 0), 0) / studentProgress.length;
      const totalCalories = studentProgress.reduce((sum: number, p: any) => sum + (p.estimatedCaloriesBurnt || 0), 0);
      const activeDays = new Set(studentProgress.map((p: any) => p.recordDate)).size;
      
      // Most used workout
      const workoutCounts: Record<string, number> = {};
      studentProgress.forEach((p: any) => {
        if (p.workoutProgram?.name) {
          workoutCounts[p.workoutProgram.name] = (workoutCounts[p.workoutProgram.name] || 0) + 1;
        }
      });
      const mostUsedWorkout = Object.keys(workoutCounts).length > 0
        ? Object.entries(workoutCounts).sort((a, b) => b[1] - a[1])[0][0]
        : 'N/A';

      // Last activity
      const sortedByDate = [...studentProgress].sort(
        (a: any, b: any) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime()
      );
      const lastActivity = sortedByDate[0]?.recordDate || 'Sin actividad';

      // Generate automatic recommendations
      const autoRecommendations: string[] = [];
      if (avgRpe < 5) {
        autoRecommendations.push('El RPE promedio es bajo. Considera aumentar la intensidad gradualmente.');
      }
      if (avgRpe > 8) {
        autoRecommendations.push('El RPE promedio es alto. Asegúrate de que el estudiante tenga suficiente recuperación.');
      }
      if (activeDays < 10) {
        autoRecommendations.push('Baja frecuencia de entrenamiento. Motiva al estudiante a ser más consistente.');
      }
      if (totalCalories < 2000) {
        autoRecommendations.push('Bajo gasto calórico total. Considera aumentar volumen o intensidad.');
      }

      return {
        studentEmail,
        analysis: {
          avgRpe: Math.round(avgRpe * 10) / 10,
          totalCalories,
          activeDays,
          mostUsedWorkout,
          lastActivity,
          trends: {
            rpeIncreasing: false,
            caloriesIncreasing: false,
            frequencyDecreasing: false
          },
          recommendations: autoRecommendations.length > 0 
            ? autoRecommendations 
            : ['El estudiante está progresando bien. Continúa monitoreando.']
        }
      };
    } catch (error) {
      return rejectWithValue(error instanceof Error ? error.message : 'Error al analizar progreso');
    }
  }
);
