import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import getAllStudents from "../actions/GetAllStudents";

//Did all the same for the StudentListScreen
const StudentListScreen = ()=>{

    const [list , setList] = useState([]);

    useEffect(()=>{
        const handleStudentList = async ()=>{
            let list = await getAllStudents();
            console.log(list);
            setList(list);
        };
        handleStudentList();
    }, []);

    return (
        <Stack direction="column">
            <Typography variant="h2">Student List</Typography>
            {list.length !== 0 && list.map( (obj)=> (<Typography key={obj.code}>{obj.name}</Typography>) )}
        </Stack>
    );
}

export default StudentListScreen;