import { Button, Stack, Typography } from "@mui/material";
import { useContext } from "react";
import { AppContext } from "../context/AppContext";
import { useNavigate } from "react-router-dom";

const ConfigScreen = () => {
    //Get the app context to get the user again
    const {user, setUser} = useContext(AppContext);
    const navigate = useNavigate();
    return(
        <Stack>
            <Typography variant="h2">Configuration Screen</Typography>
            <Typography>Logged in as: {user.username}</Typography>
            {/* <Typography>Logged in as: {user ? user.username : "No user logged in"}</Typography> */}
            <Button onClick={() => {
                localStorage.removeItem("accessToken");
            }}>Log Out</Button>
        </Stack>
    );
}

export default ConfigScreen;