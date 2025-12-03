// src/store/exercises/thunk.ts
import { createAsyncThunk } from "@reduxjs/toolkit";
import { apiFetch } from "@/services/http"; // <-- usa el wrapper centralizado
import type { Exercise } from "./exercisesSlice";

/**
 * Importante: asegúrense de que apiFetch ya lee el token desde auth_state_v1:
 *   const raw = localStorage.getItem("auth_state_v1");
 *   const { token } = JSON.parse(raw ?? "{}");
 *   headers.set("Authorization", `Bearer ${token}`);
 */

export const fetchExercises = createAsyncThunk<
  Exercise[],
  void,
  { rejectValue: string }
>("exercises/fetchAll", async (_, { rejectWithValue }) => {
  try {
    const res = await apiFetch("/api/v1/exercises");
    console.log(res);
    if (!res.ok) return rejectWithValue("Error al cargar ejercicios");
    return (await res.json()) as Exercise[];
  } catch {
    return rejectWithValue("Error de conexión");
  }
});

export const createExercise = createAsyncThunk<
  Exercise,
  Omit<Exercise, "exerciseId">,
  { rejectValue: string }
>("exercises/create", async (exerciseData, { rejectWithValue }) => {
  try {
    const res = await apiFetch("/api/v1/exercises", {
      method: "POST",
      body: JSON.stringify(exerciseData),
    });

    if (!res.ok) {
      // Backend puede responder 409 para nombre duplicado
      if (res.status === 409) {
        return rejectWithValue("Ya existe un ejercicio con ese nombre");
      }
      let msg = "Error al crear ejercicio";
      try {
        const data = await res.json();
        msg = data?.message ?? msg;
      } catch {
        // For debuggin
        console.log("Error");
      }
      return rejectWithValue(msg);
    }

    return (await res.json()) as Exercise;
  } catch {
    return rejectWithValue("Error de conexión");
  }
});

export const updateExercise = createAsyncThunk<
  Exercise,
  Exercise,
  { rejectValue: string }
>("exercises/update", async (exerciseData, { rejectWithValue }) => {
  try {
    if (!exerciseData.exerciseId) {
      return rejectWithValue("Ejercicio inválido (sin ID)");
    }
    const res = await apiFetch(`/api/v1/exercises/${exerciseData.exerciseId}`, {
      method: "PUT",
      body: JSON.stringify(exerciseData),
    });

    if (!res.ok) {
      let msg = "Error al actualizar ejercicio";
      try {
        const data = await res.json();
        msg = data?.message ?? msg;
      } catch {
        // For debuggin
        console.log("Error");
      }
      return rejectWithValue(msg);
    }

    return (await res.json()) as Exercise;
  } catch {
    return rejectWithValue("Error de conexión");
  }
});

export const deleteExercise = createAsyncThunk<
  string,
  string,
  { rejectValue: string }
>("exercises/delete", async (exerciseId, { rejectWithValue }) => {
  try {
    const res = await apiFetch(`/api/v1/exercises/${exerciseId}`, {
      method: "DELETE",
    });

    // Muchos DELETE responden 204 sin body
    if (!res.ok) {
      let msg = "Error al eliminar ejercicio";
      try {
        const data = await res.json();
        msg = data?.message ?? msg;
      } catch {
        // For debuggin
        console.log("Error");
      }
      return rejectWithValue(msg);
    }

    return exerciseId;
  } catch {
    return rejectWithValue("Error de conexión");
  }
});