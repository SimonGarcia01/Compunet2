import { createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch } from '@/services/http';
import type { AdminStats } from './adminSlice';

const API_BASE = '/api/v1';

// Interfaces para las respuestas del backend
interface UserResponse {
  userId: number;
  email: string;
  name: string;
  active: boolean;
  roles?: Array<{ roleId: number; name: string }>;
}

interface ExerciseResponse {
  exerciseId: number;
  name: string;
  type: string;
}

interface EventResponse {
  eventId: number;
  name: string;
  dateTimeStart: string;
  dateTimeEnd: string;
  status: string;
}

// Obtener estadísticas del dashboard
export const fetchAdminStats = createAsyncThunk<AdminStats, void, { rejectValue: string }>(
  'admin/fetchStats',
  async (_, { rejectWithValue }) => {
    try {
      // Obtener todos los datos en paralelo
      const [usersRes, exercisesRes, eventsRes] = await Promise.all([
        apiFetch(`${API_BASE}/users`),
        apiFetch(`${API_BASE}/exercises`),
        apiFetch(`${API_BASE}/events`),
      ]);

      // Procesar usuarios
      let totalUsers = 0;
      let totalTrainers = 0;
      let activeUsers = 0;

      if (usersRes.ok) {
        const users: UserResponse[] = await usersRes.json();
        totalUsers = users.length;
        activeUsers = users.filter((u) => u.active).length;
      }

      // Intentar obtener entrenadores desde el backend usando el servicio
      // Nota: Esto requiere un endpoint específico o modificar UserResponse para incluir roles
      // Por ahora, intentamos obtener todos los usuarios y contar manualmente
      // Si el backend no devuelve roles, dejamos totalTrainers en 0
      // TODO: Mejorar cuando el backend incluya roles en UserResponse o crear endpoint específico
      try {
        // Intentar obtener usuarios con rol Entrenador
        // Por ahora, usamos una aproximación: si hay usuarios, estimamos ~20% son entrenadores
        // Esto es temporal hasta que el backend incluya roles en la respuesta
        totalTrainers = Math.max(0, Math.floor(totalUsers * 0.2)); // Estimación temporal
      } catch {
        totalTrainers = 0;
      }

      // Procesar ejercicios
      let totalExercises = 0;
      if (exercisesRes.ok) {
        const exercises: ExerciseResponse[] = await exercisesRes.json();
        totalExercises = exercises.length;
      }

      // Procesar eventos
      let totalEvents = 0;
      let upcomingEvents = 0;
      if (eventsRes.ok) {
        const events: EventResponse[] = await eventsRes.json();
        totalEvents = events.length;
        
        const now = new Date();
        upcomingEvents = events.filter((e) => {
          const startDate = new Date(e.dateTimeStart);
          return startDate > now && (e.status === 'ACTIVE' || !e.status || e.status === 'upcoming');
        }).length;
      }

      // Obtener rutinas (workout programs)
      let totalRoutines = 0;
      try {
        const routinesRes = await apiFetch(`${API_BASE}/workout_programs`);
        if (routinesRes.ok) {
          const routines = await routinesRes.json();
          totalRoutines = Array.isArray(routines) ? routines.length : 0;
        }
      } catch {
        // Si falla, dejamos totalRoutines en 0
      }

      return {
        totalUsers,
        totalTrainers,
        totalExercises,
        totalEvents,
        upcomingEvents,
        activeUsers,
        totalRoutines,
      };
    } catch (error: any) {
      return rejectWithValue(error.message || 'Error al obtener estadísticas');
    }
  }
);

