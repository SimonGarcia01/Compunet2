import type { ThunkAction } from "redux-thunk";
import type { UnknownAction } from "redux";
import { loginFailure, loginRequest, loginSuccess } from "./actions";
import type { AuthState, User, AppRole } from "./types";
import { apiFetch } from "@/services/http";
import { extractIdentity } from "../../utils/jwt";

export type RootState = { auth: AuthState };
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType, RootState, unknown, UnknownAction>;

type LoginBody = { username: string; password: string };
type AuthResponse = { accessToken?: string; token?: string };

export const loginThunk = (body: LoginBody): AppThunk<Promise<boolean>> => async (dispatch) => {
  try {
    dispatch(loginRequest());
    const payload = { ...body, username: body.username.trim().toLowerCase() };
    const res = await apiFetch("/api/v1/auth/login", {
      method: "POST",
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      let msg = "Credenciales inválidas";
      try { 
        msg = (await res.json())?.message ?? msg; 
      } catch {
        // For debuggin
        console.log("Error");
      }
      dispatch(loginFailure(msg));
      return false;
    }

    const data = (await res.json()) as AuthResponse;
    const token = data.accessToken ?? data.token;
    if (!token) {
      dispatch(loginFailure("Respuesta inválida del servidor (sin token)."));
      return false;
    }

    // 🔎 Decodificamos el JWT para extraer email, roles y exp:
    const { email, roles, exp } = extractIdentity(token);
    const user: User = { email: email ?? payload.username, roles: (roles as AppRole[]) ?? [] };

    dispatch(loginSuccess({ token, user, exp: exp ?? null }));
    return true;
  } catch (e: any) {
    dispatch(loginFailure(e?.message ?? "Error de red"));
    return false;
  }
};