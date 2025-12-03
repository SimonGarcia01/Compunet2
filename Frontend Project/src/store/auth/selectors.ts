import type { RootState } from "./thunk";
import type { AppRole } from "./types";

export const selectToken = (s: RootState) => s.auth.token;
export const selectEmail = (s: RootState) => s.auth.user?.email ?? null;
export const selectRoles = (s: RootState) => (s.auth.user?.roles ?? []) as AppRole[];
export const selectExp   = (s: RootState) => s.auth.exp;