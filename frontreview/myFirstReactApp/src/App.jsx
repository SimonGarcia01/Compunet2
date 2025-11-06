import { useState } from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import './App.css';
import LoginForm from './screens/LoginForm.jsx';
import ProfessorListScreen from './screens/ProfessorListScreen';
import NotFound from './screens/ProfessorListScreen';

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

const router = createBrowserRouter([
  {
    //This will handle going first to the login
    path: "/",
    element: <LoginForm></LoginForm>
  },
  {
    //This will re-route to the professors
    path:"/professors",
    element:<ProfessorListScreen></ProfessorListScreen>
  },
  {
    //Any other route the user wants to go to
    path: "*",
    element: <NotFound/>
  }
]);

const App = () => {
  return(
    <RouterProvider router={router}></RouterProvider>
  );
}

export default App;




//ORIGINAL COUNTER APP

// import { useState } from 'react'
// import Stack from '@mui/material/Stack';
// import Button from '@mui/material/Button';
// import TextField from '@mui/material/TextField'
// import Typography from '@mui/material/Typography';
// import './App.css'

// const App =() => {

//   //Counter variable is defined in this type of tuple
//   //Then add a setCounter function to update that counter value
//   //This also sets the initial value of counter to 0
//   const [counter, setCounter] = useState(0);

//   return (
//     <Stack direction="column">
//       <Typography variant="h1">Counter: {counter}</Typography>
//       <Button onClick={()=> setCounter(counter + 1)}>Increment</Button>
//     </Stack>
//   );
// }

// export default App;
