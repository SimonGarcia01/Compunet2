import { useState } from 'react'
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography';
import '../App.css';
import login from '../actions/Login';
import { useNavigate } from 'react-router-dom';

//Prop / Property example where the title is passed to the component
const LoginForm =({title}) => {

    //Used to navigate to another page
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try{
            await login({username:username, password:password});
            navigate("/students");
        } catch(e) {
            setError(e.message);
        }
    }

    return (
        <Stack>
            <Typography variant='h2'>{title}</Typography>
            <Typography color="red">{error}</Typography>
            <TextField label='Username' value={username} onChange={(event)=>setUsername(event.target.value)}/>
            <TextField label='Password' value={password} onChange={(event)=>setPassword(event.target.value)}/>
            <Button onClick={handleLogin}>Log In</Button>
        </Stack>
    );
}

export default LoginForm;
