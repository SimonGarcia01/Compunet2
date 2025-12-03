import { initialAuthState, type AuthState, type AppRole } from "./types";
import { LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT, type AuthAction } from "./actions";
import { authStorage } from "@/services/http";

type Persisted = { token: string | null; email: string | null; roles: AppRole[]; exp: number | null };

const hydrated: AuthState = (() => {
  const raw = authStorage.get();
  if (!raw) return { ...initialAuthState };
  try {
    const p = JSON.parse(raw) as Persisted;
    return {
      ...initialAuthState,
      token: p.token,
      user: p.token ? { email: p.email, roles: p.roles } : null,
      exp: p.exp,
    };
  } catch {
    return { ...initialAuthState };
  }
})();

export function authReducer(state: AuthState = hydrated, action: AuthAction): AuthState {
  switch (action.type) {
    case LOGIN_REQUEST:
      return { ...state, loading: true, error: null };
    case LOGIN_SUCCESS: {
      const next = {
        ...state,
        loading: false,
        error: null,
        token: action.payload.token,
        user: action.payload.user,
        exp: action.payload.exp,
      };
      authStorage.save(JSON.stringify({
        token: next.token,
        email: next.user?.email ?? null,
        roles: next.user?.roles ?? [],
        exp: next.exp,
      }));
      return next;
    }
    case LOGIN_FAILURE:
      return { ...state, loading: false, error: action.error };
    case LOGOUT:
      authStorage.clear();
      return { ...initialAuthState };
    default:
      return state;
  }
}