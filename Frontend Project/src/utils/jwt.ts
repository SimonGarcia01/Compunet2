// src/utils/jwt.ts
export type DecodedJWT = {
    sub?: string;
    email?: string;
    authorities?: string[] | string; // admite array o string separados por coma
    role?: string | string[];
    roles?: string[];                // por si viene como "roles"
    iat?: number;
    exp?: number;
    [k: string]: unknown;
  };
  
  export function decodeJwt(token: string): DecodedJWT | null {
    try {
      const [, payload] = token.split(".");
      if (!payload) return null;
      const decoded = JSON.parse(atob(payload.replace(/-/g, "+").replace(/_/g, "/")));
      return decoded as DecodedJWT;
    } catch {
      return null;
    }
  }
  
  // Normaliza authorities/roles a nuestros 3 roles canónicos
  const ROLE_MAP: Record<string, "Usuario" | "Entrenador" | "Administrador" | undefined> = {
    "ROLE_USUARIO": "Usuario",
    "ROLE_ENTRENADOR": "Entrenador",
    "ROLE_ADMINISTRADOR": "Administrador",
    "USUARIO": "Usuario",
    "ENTRENADOR": "Entrenador",
    "ADMINISTRADOR": "Administrador",
    "USER": "Usuario",
    "TRAINER": "Entrenador",
    "ADMIN": "Administrador",
    "ROLE_USER": "Usuario",
    "ROLE_TRAINER": "Entrenador",
    "ROLE_ADMIN": "Administrador"
  };
  
  function toArray(val?: string[] | string): string[] {
    if (!val) return [];
    return Array.isArray(val) ? val : val.split(",").map(s => s.trim()).filter(Boolean);
  }
  
  export function extractIdentity(token: string): {
    email: string | null;
    roles: ("Usuario" | "Entrenador" | "Administrador")[];
    exp: number | null;
  } {
    const d = decodeJwt(token);
    if (!d) return { email: null, roles: [], exp: null };
  
    const rawRoles = [
      ...toArray(d.authorities as any),
      ...toArray(d.roles as any),
      ...toArray(d.role as any),
    ];
  
    const mapped = Array.from(new Set(
      rawRoles
        .map(r => ROLE_MAP[r] ?? ROLE_MAP[r?.toUpperCase?.()] )
        .filter(Boolean) as Array<"Usuario" | "Entrenador" | "Administrador">
    ));
  
    const email = (d.email ?? d.sub ?? null) as string | null;
    const exp = typeof d.exp === "number" ? d.exp : null;
    return { email, roles: mapped, exp };
  }