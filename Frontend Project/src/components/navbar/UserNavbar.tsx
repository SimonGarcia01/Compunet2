import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "@/store/auth/actions";
import type { RootState, AppDispatch } from "@/store/index";
import { NavbarBase, type NavLink, type UserMenuItem } from "./NavbarBase.tsx";
import { AIndyChatModal } from "@/components/chat/AIndyChatModal";

export function UserNavbar() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { user } = useSelector((s: RootState) => s.auth);
  const [aindyOpen, setAIndyOpen] = useState(false);

  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
  };

  const links: NavLink[] = [
    { label: "Mis Rutinas", to: "/app/user/routines" },
    { label: "Rutinas Prediseñadas", to: "/app/user/routines/predesigned" },
    { label: "Progreso", to: "/app/user/progress" },
    { label: "Historial", to: "/app/user/history" },
    { label: "Recomendaciones", to: "/app/user/recommendations" },
    { label: "Eventos", to: "/app/events" },
  ];

  const userMenu: UserMenuItem[] = [
    { label: "Mi Perfil", to: "/app/profile" },
    { label: "Reportes PDF", to: "/app/user/reports" },
    { label: "Cerrar Sesión", onClick: handleLogout, variant: "destructive" },
  ];

  return (
    <>
      <NavbarBase
        logo="Gym Icesi"
        links={links}
        userMenu={userMenu}
        userEmail={user?.email}
        userRole="Usuario"
        notifications
        onAIndyClick={() => setAIndyOpen(true)}
      />
      <AIndyChatModal open={aindyOpen} onOpenChange={setAIndyOpen} />
    </>
  );
}
