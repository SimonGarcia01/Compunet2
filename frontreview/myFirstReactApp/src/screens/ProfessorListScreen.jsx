import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

import { useState } from "react";
import {useEffect} from "react";
import getAllProfessors from '../actions/GetAllProfessor';

const ProfessorListScreen = () => {

    //The list that will be shown in
    const [list, setList] = useState([]);

    //The function is not async
    //Everything inside this will be executed only after the component is loaded completely
    useEffect(()=>{

        //Interior functions can be async
        //Function to get the professros
        const handleProfessorList = async () => {
            let list = await getAllProfessors();
            console.log(list);
            //This causes renderization since it used the graphical image
            setList(list);
        }

        //Actually use the function to get the professor list
        handleProfessorList();
    }, []);

    //Organizing the graphical part here
    return (
        <Stack direction="column">
            <Typography>Professor List:</Typography>
            {list.length !== 0 && list.map((obj) => (<Typography key={obj.id}>{obj.name}</Typography>))}
        </Stack>
    );
}

export default ProfessorListScreen;