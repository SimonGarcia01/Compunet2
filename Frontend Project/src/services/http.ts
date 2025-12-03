// src/services/http.ts
const baseURL = "http://192.168.131.37:8080/borojo-backend";
// Normaliza para permitir tanto con como sin /api al llamar
function join(u: string) {
  if (!u.startsWith("/")) return `/${u}`;
  return u;
}

export async function apiFetch(input: string, init?: RequestInit): Promise<Response> {
  // Leer el token desde auth_state_v1 (formato JSON)
  let token: string | null = null;
  try {
    const raw = localStorage.getItem("auth_state_v1");
    if (raw) {
      const parsed = JSON.parse(raw);
      token = parsed?.token ?? null;
      console.log(token);
    }
  } catch {
    console.log("Entra al catch")
    // Si falla el parseo, token queda null
    token = null;
  }

  const headers = new Headers(init?.headers ?? {});
  if (token) headers.set("Authorization", `Bearer ${token}`);
  if (!headers.has("Content-Type") && init?.body) headers.set("Content-Type", "application/json");

  const url = `${baseURL}${join(input)}`;
  
  // Usar redirect: 'manual' para detectar redirecciones antes de que causen errores de CORS
  // Esto permite que el código maneje redirecciones (3xx) sin que el navegador intente
  // hacer un preflight request a la URL de redirección
  return fetch(url, { 
    ...init, 
    headers,
    redirect: 'manual' as RequestRedirect
  });
}

// Reemplaza el storage sencillo por uno que guarda JSON del estado mínimo
export const authStorage = {
    save(payload: string) {
      try { 
        localStorage.setItem("auth_state_v1", payload); 
      } catch {
        // For debuggin
        console.log("Error");
      }
    },
    clear() {
      try { 
        localStorage.removeItem("auth_state_v1"); 
        // Daniela
        localStorage.removeItem("accessToken"); 
        localStorage.removeItem("auth"); 
      } catch {
        // For debuggin
        console.log("Error");
      }
    },
    get(): string | null {
      try { return localStorage.getItem("auth_state_v1"); } catch { return null; }
    }
  };
  
  // apiFetch sigue igual: solo lee Authorization del store si ya lo estabas haciendo,
  // o bien parsea auth_state_v1 si necesitas token fuera del store.