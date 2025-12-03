import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "@/store/auth/actions";
import type { RootState, AppDispatch } from "@/store/index";
import { NavbarBase, type NavLink, type UserMenuItem } from "./NavbarBase.tsx";
import { AIndyChatModal } from "@/components/chat/AIndyChatModal";

export function AdminNavbar() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { user } = useSelector((s: RootState) => s.auth);
  const [aindyOpen, setAIndyOpen] = useState(false);

  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
  };

  const links: NavLink[] = [
    { label: "Dashboard", to: "/app/admin/dashboard" },
    { label: "Ejercicios", to: "/app/user/exercises" },
    { label: "Eventos", to: "/app/events" },
  ];

  const userMenu: UserMenuItem[] = [
    { label: "Cerrar Sesión", onClick: handleLogout, variant: "destructive" },
  ];

  return (
    <>
      <NavbarBase
        logo="Gym Icesi"
        links={links}
        userMenu={userMenu}
        userEmail={user?.email}
        userRole="Administrador"
        notifications
        onAIndyClick={() => setAIndyOpen(true)}
      />
      <AIndyChatModal open={aindyOpen} onOpenChange={setAIndyOpen} />
    </>
  );
}
