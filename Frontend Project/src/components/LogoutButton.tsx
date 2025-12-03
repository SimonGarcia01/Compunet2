import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "@/store/auth/actions";
import { Button } from "@/components/ui/button";

export default function LogoutButton() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
  };

  return (
    <Button onClick={handleLogout} variant="outline" size="sm">
      Cerrar Sesión
    </Button>
  );
}

