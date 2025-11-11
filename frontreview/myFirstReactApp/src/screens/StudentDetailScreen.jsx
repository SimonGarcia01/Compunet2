import Stack from "@mui/material/Stack";
import { useEffect } from "react";
import { useParams } from "react-router-dom";


const  StudentDetailScreen = ()=>{
    //This will get the parameter from the url that is sent with
    //EX: /students/3  -> studentId = 3
    const { studentId } = useParams(); 

    useEffect(()=>{
        console.log(studentId);
    } , []);
    

    return(
        <Stack>
            
        </Stack>
    );

}
export default StudentDetailScreen;