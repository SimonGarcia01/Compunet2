export const LOGIN_REQUEST = "auth/LOGIN_REQUEST" as const;
export const LOGIN_SUCCESS = "auth/LOGIN_SUCCESS" as const;
export const LOGIN_FAILURE = "auth/LOGIN_FAILURE" as const;
export const LOGOUT        = "auth/LOGOUT"        as const;

export const loginRequest = () => ({ type: LOGIN_REQUEST });
export const loginSuccess = (payload: { token: string; user: any | null; exp: number | null }) =>
  ({ type: LOGIN_SUCCESS, payload });
export const loginFailure = (error: string) => ({ type: LOGIN_FAILURE, error });
export const logout = () => ({ type: LOGOUT });

export type AuthAction =
  | ReturnType<typeof loginRequest>
  | ReturnType<typeof loginSuccess>
  | ReturnType<typeof loginFailure>
  | ReturnType<typeof logout>;