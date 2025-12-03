import { useSelector } from "react-redux";
import type { RootState } from "@/store/index";
import { AdminNavbar } from "./AdminNavbar.tsx";
import { TrainerNavbar } from "./TrainerNavbar.tsx";
import { UserNavbar } from "./UserNavbar.tsx";

export function RoleBasedNavbar() {
  const { user } = useSelector((s: RootState) => s.auth);
  const roles = user?.roles ?? [];

  // Priority: Admin > Trainer > User
  if (roles.includes("Administrador")) {
    return <AdminNavbar />;
  }

  if (roles.includes("Entrenador")) {
    return <TrainerNavbar />;
  }

  return <UserNavbar />;
}
