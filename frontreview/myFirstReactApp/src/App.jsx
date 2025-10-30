import { useState } from 'react'
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography';
import './App.css'

const App =() => {

  //Counter variable is defined in this type of tuple
  //Then add a setCounter function to update that counter value
  //This also sets the initial value of counter to 0
  const [counter, setCounter] = useState(0);

  return (
    <Stack direction="column">
      <Typography variant="h1">Counter: {counter}</Typography>
      <Button onClick={()=> setCounter(counter + 1)}>Increment</Button>
    </Stack>
  );
}

export default App;
