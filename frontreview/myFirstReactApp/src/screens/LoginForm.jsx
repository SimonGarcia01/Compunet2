import { useContext, useState } from 'react'
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography';
import '../App.css';
import login from '../actions/Login';
import { useNavigate } from 'react-router-dom';
import { AppContext } from '../context/AppContext';

//Prop / Property example where the title is passed to the component
const LoginForm =({title}) => {

    //Used to navigate to another page
    const navigate = useNavigate();

    //Get the app context to store the user
    //On the left side you get the values destructured
    //On the right side you use the useContext hook with AppContext to get the values
    const {user, setUser} = useContext(AppContext);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try{
            let token = await login({username:username, password:password});
            navigate("/home");
            console.log("Login: "+token);
            //Now we change the user in the global state
            //This gives the user a username and accessToken property
            setUser({username:username, accessToken:token});
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
