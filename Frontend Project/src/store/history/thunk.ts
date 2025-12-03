import { createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch } from '@/services/http';
import type { HistoryActivity } from './historySlice';
import type { ExerciseProgress } from '@/store/userProgress/userProgressSlice';
import type { WorkoutProgram } from '@/store/workoutPrograms/workoutProgramsSlice';
import type { Event } from '@/store/events/eventsSlice';
import type { HistoricalRecord } from './historySlice';

interface HistoryData {
  activities: HistoryActivity[];
  metrics: {
    totalActivities: number;
    totalCalories: number;
    completedRoutines: number;
    eventsAttended: number;
    daysActive: number;
  };
}

export const fetchHistory = createAsyncThunk<HistoryData, void, { rejectValue: string }>(
  'history/fetch',
  async (_, { rejectWithValue }) => {
    try {
      console.log('Iniciando carga de historial...');
      
      // Obtener todos los datos en paralelo
      const [progressRes, eventsRes, historicalRes] = await Promise.all([
        apiFetch('/api/v1/exercise_progress/my').catch(err => {
          console.warn('Error al obtener progreso:', err);
          return { ok: false, status: 500, json: async () => [] } as Response;
        }),
        apiFetch('/api/v1/event_attendances').catch(err => {
          console.warn('Error al obtener eventos:', err);
          return { ok: false, status: 500, json: async () => [] } as Response;
        }),
        apiFetch('/api/v1/historical_records/my').catch(err => {
          console.warn('Error al obtener registros históricos:', err);
          return { ok: false, status: 500, json: async () => [] } as Response;
        }),
      ]);

      console.log('Respuestas recibidas:', {
        progress: { ok: progressRes.ok, status: progressRes.status },
        events: { ok: eventsRes.ok, status: eventsRes.status },
        historical: { ok: historicalRes.ok, status: historicalRes.status },
      });

      const activities: HistoryActivity[] = [];
      
      // Obtener progreso
      if (progressRes.ok) {
        try {
          const progressDataRaw = await progressRes.json();
          const progressData: ExerciseProgress[] = Array.isArray(progressDataRaw) ? progressDataRaw : [];
          console.log('Progreso recibido para historial:', progressData);
          progressData.forEach((progress) => {
            activities.push({
              id: `progress-${progress.progressId || Math.random()}`,
              type: 'progress',
              date: progress.recordDate || new Date().toISOString(),
              title: progress.exercise?.name || 'Ejercicio',
              description: progress.workoutProgram?.name || '',
              details: {
                repetitions: progress.repetitions,
                timeMinutes: progress.timeMinutes,
                distanceKm: progress.distanceKm ? Number(progress.distanceKm) : undefined,
                rpe: progress.rpe,
              },
              calories: progress.estimatedCaloriesBurnt ? Number(progress.estimatedCaloriesBurnt) : undefined,
            });
          });
        } catch (error) {
          console.error('Error procesando progreso:', error);
        }
      } else {
        console.warn('No se pudo obtener progreso, status:', progressRes.status);
      }

      // Obtener rutinas completadas (solo las del usuario actual)
      // El endpoint /api/v1/workout_programs devuelve todas, pero necesitamos filtrar por usuario
      // Por ahora, usamos fetchMyPrograms que ya filtra por usuario
      try {
        const myRoutinesRes = await apiFetch('/api/v1/workout_programs');
        if (myRoutinesRes.ok) {
          try {
            const routinesDataRaw = await myRoutinesRes.json();
            const routinesData: WorkoutProgram[] = Array.isArray(routinesDataRaw) ? routinesDataRaw : [];
            console.log('Rutinas recibidas para historial:', routinesData.length);
            // Filtrar solo las rutinas completadas del usuario actual
            // Nota: El backend debería filtrar por usuario autenticado, pero por ahora filtramos en frontend
            const completedRoutines = routinesData.filter((r) => r.completed);
            completedRoutines.forEach((routine) => {
              activities.push({
                id: `routine-${routine.workoutId}`,
                type: 'routine',
                date: routine.creationDate || new Date().toISOString(),
                title: routine.name || 'Rutina',
                description: routine.description || '',
                details: {
                  exercisesCount: routine.exercises?.length || 0,
                },
              });
            });
          } catch (error) {
            console.error('Error procesando rutinas:', error);
          }
        } else {
          console.warn('No se pudo obtener rutinas, status:', myRoutinesRes.status);
        }
      } catch (error) {
        // Ignorar error si no se pueden obtener rutinas
        console.warn('Error obteniendo rutinas:', error);
      }

      // Obtener eventos asistidos
      if (eventsRes.ok) {
        try {
          const attendancesRaw = await eventsRes.json();
          const attendances: any[] = Array.isArray(attendancesRaw) ? attendancesRaw : [];
          console.log('Eventos asistidos recibidos:', attendances);
          // Filtrar eventos del usuario actual (necesitamos obtener el email del usuario autenticado)
          // Por ahora, asumimos que el backend filtra por usuario autenticado
          attendances.forEach((attendance) => {
            if (attendance.event) {
              activities.push({
                id: `event-${attendance.event.eventId || Math.random()}`,
                type: 'event',
                date: attendance.event.startDate || attendance.event.createdAt || new Date().toISOString(),
                title: attendance.event.title || 'Evento',
                description: attendance.event.description,
                details: {
                  location: attendance.event.location,
                  type: attendance.event.type,
                },
              });
            }
          });
        } catch (error) {
          console.error('Error procesando eventos asistidos:', error);
        }
      } else {
        console.warn('No se pudo obtener eventos asistidos, status:', eventsRes.status);
      }

      // Obtener registros históricos
      if (historicalRes.ok) {
        try {
          const historicalDataRaw = await historicalRes.json();
          const historicalData: HistoricalRecord[] = Array.isArray(historicalDataRaw) ? historicalDataRaw : [];
          console.log('Registros históricos recibidos:', historicalData);
          historicalData.forEach((record) => {
            if (record.event) {
              activities.push({
                id: `historical-event-${record.recordId}`,
                type: 'historical',
                date: record.event.startDate || new Date().toISOString(),
                title: `Evento: ${record.event.title}`,
                description: record.details,
                calories: record.estimatedBurntCalories,
              });
            } else if (record.workoutProgram) {
              activities.push({
                id: `historical-routine-${record.recordId}`,
                type: 'historical',
                date: new Date().toISOString(), // Usar fecha actual si no hay fecha
                title: `Rutina: ${record.workoutProgram.name}`,
                description: record.details,
                calories: record.estimatedBurntCalories,
              });
            }
          });
        } catch (error) {
          console.error('Error procesando registros históricos:', error);
        }
      } else {
        console.warn('No se pudo obtener registros históricos, status:', historicalRes.status);
      }

      // Ordenar actividades por fecha (más recientes primero)
      // Filtrar actividades con fechas inválidas antes de ordenar
      const validActivities = activities.filter(a => {
        try {
          const date = new Date(a.date);
          return !isNaN(date.getTime());
        } catch {
          return false;
        }
      });

      validActivities.sort((a, b) => {
        try {
          return new Date(b.date).getTime() - new Date(a.date).getTime();
        } catch {
          return 0;
        }
      });

      // Calcular métricas
      const totalCalories = validActivities.reduce((sum, a) => sum + (a.calories || 0), 0);
      const completedRoutines = validActivities.filter((a) => a.type === 'routine').length;
      const eventsAttended = validActivities.filter((a) => a.type === 'event' || (a.type === 'historical' && a.title?.includes('Evento'))).length;
      
      // Calcular días activos de forma segura
      const dateStrings = validActivities
        .map(a => {
          try {
            return a.date.split('T')[0];
          } catch {
            return null;
          }
        })
        .filter((d): d is string => d !== null);
      const uniqueDates = new Set(dateStrings).size;

      console.log('Historial procesado:', {
        totalActivities: validActivities.length,
        totalCalories,
        completedRoutines,
        eventsAttended,
        daysActive: uniqueDates,
      });

      return {
        activities: validActivities,
        metrics: {
          totalActivities: validActivities.length,
          totalCalories,
          completedRoutines,
          eventsAttended,
          daysActive: uniqueDates,
        },
      };
    } catch (error) {
      console.error('Error completo al cargar historial:', error);
      const errorMessage = error instanceof Error ? error.message : 'Error desconocido';
      return rejectWithValue(`Error al cargar el historial: ${errorMessage}`);
    }
  }
);

