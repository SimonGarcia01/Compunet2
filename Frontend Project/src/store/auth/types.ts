export type AppRole = "Usuario" | "Entrenador" | "Administrador";

export interface User {
  email: string | null;
  roles: AppRole[];
}

export type AuthState = {
    loading: boolean;
    token: string | null;
    user: {
      email: string;
      roles: ("Usuario" | "Entrenador" | "Administrador")[];
    } | null;
    exp: number | null;
    error: string | null;
  };

export const initialAuthState: AuthState = {
  loading: false,
  token: null,
  user: null,
  exp: null,
  error: null,
};