import type { ThunkAction } from "redux-thunk";
import type { UnknownAction } from "redux";
import type { RootState } from "@/store/auth/thunk";
import { apiFetch } from "@/services/http";

export type RegisterPayload = {
  email: string;
  fullName: string;
  personalId: string;
  password: string;
  roleIds: number[];
};

export const registerThunk =
  (payload: RegisterPayload): ThunkAction<Promise<{ ok: boolean; message?: string }>, RootState, unknown, UnknownAction> =>
  async () => {
    const res = await apiFetch("/api/v1/auth/register", {
      method: "POST",
      body: JSON.stringify({
        email: payload.email.trim().toLowerCase(),
        fullName: payload.fullName?.trim() || null,
        personalId: payload.personalId?.trim() || null,
        password: payload.password,
        roleIds: payload.roleIds,
      }),
    });

    if (res.ok) return { ok: true };

    let msg = "No se pudo crear el usuario.";
    try {
      const data = await res.json();
      msg = data?.message ?? msg;
    } catch {
      // For debuggin
      console.log("Error");
    }
    return { ok: false, message: msg };
  };