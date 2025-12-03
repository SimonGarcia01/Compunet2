import { createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch } from '@/services/http';
import { WorkoutProgram, WorkoutExercise } from './workoutProgramsSlice';

// Función para mapear ejercicios del backend al frontend
function mapBackendExerciseToFrontend(backendExercise: any): WorkoutExercise {
  return {
    exerciseId: String(backendExercise.exercise?.exerciseId || backendExercise.id?.exerciseId || backendExercise.exerciseId),
    exerciseName: backendExercise.exercise?.name || '',
    exerciseType: backendExercise.exercise?.type || '',
    series: backendExercise.series || 0,
    amount: backendExercise.amount || 0,
    session: backendExercise.session || 1,
    notes: backendExercise.notes || '',
    rpe: backendExercise.rpe || 5,
  };
}

// Función para mapear programa del backend al frontend
function mapBackendProgramToFrontend(backendProgram: any): WorkoutProgram {
  return {
    workoutId: String(backendProgram.workoutId),
    name: backendProgram.name || '',
    description: backendProgram.description || '',
    photoUrl: backendProgram.photoUrl || '',
    creationDate: backendProgram.creationDate || new Date().toISOString(),
    completed: backendProgram.completed || false,
    userId: String(backendProgram.user?.userId || backendProgram.user?.email || ''),
    exercises: (backendProgram.exercises || []).map(mapBackendExerciseToFrontend),
    creator: backendProgram.user ? {
      email: backendProgram.user.email || '',
      name: backendProgram.user.name,
      roles: backendProgram.user.roles,
    } : undefined,
  };
}

export const fetchMyPrograms = createAsyncThunk<
  WorkoutProgram[],
  void,
  { rejectValue: string }
>(
  'workoutPrograms/fetchMy',
  async (userId, { rejectWithValue }) => {
    try {
      // por ahora trae TODOS los programas
      const res = await apiFetch('/api/v1/workout_programs');

      if (!res.ok) {
        return rejectWithValue('Error al cargar rutinas');
      }

      const backendPrograms = await res.json();
      
      // Asegurar que siempre retornamos un array
      if (!Array.isArray(backendPrograms)) {
        console.error('Expected array but got:', backendPrograms);
        return [];
      }
      
      return backendPrograms.map(mapBackendProgramToFrontend);
    } catch {
      return rejectWithValue('Error de conexión');
    }
  }
);

export const fetchProgramById = createAsyncThunk<WorkoutProgram, string, { rejectValue: string }>(
  'workoutPrograms/fetchById',
  async (programId, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`/api/v1/workout_programs/${programId}`);

      if (!res.ok) {
        return rejectWithValue('Error al cargar la rutina');
      }

      const backendProgram = await res.json();
      return mapBackendProgramToFrontend(backendProgram);
    } catch (error) {
      return rejectWithValue('Error de conexión');
    }
  }
);

export const createProgram = createAsyncThunk<
  WorkoutProgram, 
  Partial<WorkoutProgram> & { userEmail?: string }, 
  { rejectValue: string }
>(
  'workoutPrograms/create',
  async (programData, { rejectWithValue, getState }) => {
    try {
      // Obtener el email del usuario desde el store
      const state = getState() as any;
      const userEmail = programData.userEmail || state?.auth?.user?.email;
      
      if (!userEmail) {
        return rejectWithValue('No se pudo obtener la información del usuario');
      }

      // Obtener la fecha actual en formato LocalDate (YYYY-MM-DD)
      const today = new Date();
      const creationDate = today.toISOString().split('T')[0]; // Formato YYYY-MM-DD

      // Limpiar el payload: enviar todos los campos que el backend espera
      // NOTA: El backend tiene un bug - debería buscar el usuario por email en lugar de crear uno nuevo
      // Por ahora enviamos solo el email y esperamos que el backend lo maneje correctamente
      const payload: any = {
        name: programData.name?.trim() || '',
        description: programData.description?.trim() || '',
        photoUrl: programData.photoUrl?.trim() || '', // Backend requiere string, no null
        creationDate: creationDate, // Formato LocalDate (YYYY-MM-DD)
        completed: false, // Debe ser false según la validación del backend
        user: {
          email: userEmail,
        },
        // Incluir ejercicios en el payload
        exercises: (programData.exercises || []).map((ex: any) => ({
          exerciseId: parseInt(ex.exerciseId) || 0,
          series: ex.series || 1,
          session: ex.session || 1,
          amount: ex.amount || 1,
        })),
      };

      // Validar que los campos requeridos estén presentes
      if (!payload.name || !payload.description) {
        return rejectWithValue('El nombre y la descripción son requeridos');
      }

      if (!payload.photoUrl) {
        payload.photoUrl = ''; // Asegurar que photoUrl no sea null
      }

      console.log('Enviando petición para crear rutina:', payload);

      const res = await apiFetch('/api/v1/workout_programs', {
        method: 'POST',
        body: JSON.stringify(payload),
      });

      console.log('Respuesta del servidor:', {
        status: res.status,
        statusText: res.statusText,
        ok: res.ok,
      });

      if (!res.ok) {
        let msg = 'Error al crear rutina';
        try {
          const error = await res.json();
          console.error('Error del servidor:', error);
          msg = error.message || error.error || msg;
          // Log para debugging
          console.error('Error al crear rutina:', {
            status: res.status,
            statusText: res.statusText,
            error: error,
            payload: payload,
          });
        } catch (parseError) {
          const errorText = await res.text();
          console.error('Error al crear rutina (sin respuesta JSON):', {
            status: res.status,
            statusText: res.statusText,
            errorText: errorText,
            payload: payload,
          });
          msg = `Error ${res.status}: ${res.statusText || errorText || 'Error desconocido'}`;
        }
        return rejectWithValue(msg);
      }

      const backendProgram = await res.json();
      console.log('Rutina creada exitosamente:', backendProgram);
      return mapBackendProgramToFrontend(backendProgram);
    } catch (error) {
      console.error('Error de conexión al crear rutina:', error);
      return rejectWithValue(error instanceof Error ? error.message : 'Error de conexión');
    }
  }
);

export const updateProgram = createAsyncThunk<WorkoutProgram, WorkoutProgram, { rejectValue: string }>(
  'workoutPrograms/update',
  async (programData, { rejectWithValue }) => {
    try {
      if (!programData.workoutId) {
        return rejectWithValue('Rutina inválida (sin ID)');
      }

      // Preparar payload para actualización
      const payload: any = {
        name: programData.name?.trim() || '',
        description: programData.description?.trim() || '',
        photoUrl: programData.photoUrl?.trim() || '',
        creationDate: programData.creationDate || new Date().toISOString().split('T')[0],
        completed: programData.completed || false,
        user: {
          email: programData.userId || '',
        },
        // Incluir ejercicios en el payload
        exercises: (programData.exercises || []).map((ex: any) => ({
          exerciseId: parseInt(ex.exerciseId) || 0,
          series: ex.series || 1,
          session: ex.session || 1,
          amount: ex.amount || 1,
        })),
      };

      const res = await apiFetch(`/api/v1/workout_programs/${programData.workoutId}`, {
        method: 'PUT',
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        let msg = 'Error al actualizar rutina';
        try {
          const error = await res.json();
          msg = error.message || msg;
        } catch {
          // For debuggin
          console.log("Error");
        }
        return rejectWithValue(msg);
      }

      const backendProgram = await res.json();
      return mapBackendProgramToFrontend(backendProgram);
    } catch (error) {
      return rejectWithValue('Error de conexión');
    }
  }
);

export const deleteProgram = createAsyncThunk<string, string, { rejectValue: string }>(
  'workoutPrograms/delete',
  async (programId, { rejectWithValue }) => {
    try {
      const res = await apiFetch(`/api/v1/workout_programs/${programId}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        let msg = 'Error al eliminar rutina';
        try {
          const error = await res.json();
          msg = error.message || msg;
        } catch {
          // For debuggin
          console.log("Error");
        }
        return rejectWithValue(msg);
      }

      return programId;
    } catch (error) {
      return rejectWithValue('Error de conexión');
    }
  }
);

export const fetchPredesignedPrograms = createAsyncThunk<
  WorkoutProgram[],
  void,
  { rejectValue: string }
>(
  'workoutPrograms/fetchPredesigned',
  async (_, { rejectWithValue, getState }) => {
    try {
      const res = await apiFetch('/api/v1/workout_programs');

      if (!res.ok) {
        return rejectWithValue('Error al cargar rutinas prediseñadas');
      }

      const allPrograms: any[] = await res.json();
      const state = getState() as any;
      const currentUserEmail = state?.auth?.user?.email;
      
      // Filtrar rutinas creadas por entrenadores
      // Verificamos si el usuario creador tiene rol de Entrenador
      const predesignedPrograms = allPrograms
        .filter((program: any) => {
          // Excluir rutinas del usuario actual
          if (program.user?.email === currentUserEmail) {
            return false;
          }
          
          // Si el programa tiene información del usuario creador con roles
          if (program.user?.roles) {
            const roles = Array.isArray(program.user.roles) 
              ? program.user.roles 
              : [program.user.roles];
            
            return roles.some((role: any) => {
              const roleName = typeof role === 'string' ? role : role.name || role.authority;
              return roleName === 'Entrenador' || roleName === 'ROLE_Entrenador';
            });
          }
          
          // Si no hay información de roles, asumimos que es prediseñada si no es del usuario actual
          return true;
        })
        .map(mapBackendProgramToFrontend);

      return predesignedPrograms;
    } catch {
      return rejectWithValue('Error de conexión');
    }
  }
);

export const adoptProgram = createAsyncThunk<
  WorkoutProgram,
  { programId: string },
  { rejectValue: string }
>(
  'workoutPrograms/adopt',
  async ({ programId }, { rejectWithValue, getState, dispatch }) => {
    try {
      // Primero obtener la rutina original
      const fetchRes = await dispatch(fetchProgramById(programId));
      
      if (fetchProgramById.rejected.match(fetchRes)) {
        return rejectWithValue('No se pudo obtener la rutina');
      }

      const originalProgram = fetchRes.payload;
      const state = getState() as any;
      const userEmail = state?.auth?.user?.email;

      if (!userEmail) {
        return rejectWithValue('No se pudo obtener la información del usuario');
      }

      // Crear una copia de la rutina para el usuario
      const today = new Date();
      const creationDate = today.toISOString().split('T')[0];

      const newProgram = {
        name: `${originalProgram.name} (Copiada)`,
        description: originalProgram.description,
        photoUrl: originalProgram.photoUrl || '',
        creationDate: creationDate,
        completed: false,
        userEmail: userEmail,
      };

      const createRes = await dispatch(createProgram(newProgram));
      
      if (createProgram.rejected.match(createRes)) {
        return rejectWithValue(createRes.payload || 'Error al copiar la rutina');
      }

      return createRes.payload;
    } catch (error) {
      return rejectWithValue('Error de conexión');
    }
  }
);