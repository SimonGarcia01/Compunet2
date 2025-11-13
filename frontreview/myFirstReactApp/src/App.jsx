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
import StudentListScreen from './screens/StudentListScreen.jsx';
import StudentDetailScreen from './screens/StudentDetailScreen.jsx';
import HomeScreen from './screens/HomeScreen.jsx';
import ConfigScreen from './screens/ConfigScreen.jsx';

const router = createBrowserRouter([
  {
    //This will handle going first to the login
    path: "/",
    element: <LoginForm></LoginForm>
  },
  {
    path: "/home",
    element:<HomeScreen paths={[
      {path:"", text:"Students"},
      {path:"professors", text:"Professors"},
      {path:"config", text:"Configurations"}
    ]}/>,
    children: [
      {
        //This is the default route for home
        index: true,
        element: <StudentListScreen/>
      },
      {
        //This is for the professors screen
         path:"professors",
        element:<ProfessorListScreen/>
      },
      {
        path:"config",
        element: <ConfigScreen/>
      },
      {
        //Version within the home path
        //Must start without a slash
        path:"students/:studentCode",
        element:<StudentDetailScreen/>
      },
    ]
  },
  // {
  //   //This will re-route to the professors
  //   path:"/professors",
  //   element:<ProfessorListScreen></ProfessorListScreen>
  // },
  // {
  //   //This is for the students
  //   path:"/students",
  //   element:<StudentListScreen/>
  // },
  // {
  //   //This for the detail of a student
  //   //This will allow the project to use the useParams hook to get the ID
  //   path:"/students/:studentCode",
  //   element:<StudentDetailScreen/>
  // },
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
