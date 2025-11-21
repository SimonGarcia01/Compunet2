import { useNavigate } from "react-router-dom";
import { useState } from 'react';
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import TextField from '@mui/material/TextField';
import Typography from "@mui/material/Typography";
import '../App.css';
import login from '../actions/Login';

const LoginForm = ({title}) => {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async ()=>{
        try{
            await login({username:username, password: password});   
            navigate("/orders");
        }catch(e){
            setError(e.message);
        }
        
    }

    return (
        <Stack>
            <Typography variant='h2'>{title}</Typography>
            <TextField label="Username" value={username} onChange={(event)=>setUsername(event.target.value)}/>
            <TextField label="Password" value={password} type='password' onChange={(event)=>setPassword(event.target.value)}/>
            <Button onClick={handleLogin}>Iniciar Sesión</Button>
            <Typography color="red">{error}</Typography>
        </Stack>
    );
}

export default LoginForm;