import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

//Get as parameter the entire student
//Also parametrize the method it used
const StudentComponent = ({student, onClicked}) => {
    return(
        <Stack>
            <Typography variant="h5">{student.name}</Typography>
            <Typography>{student.code}</Typography>
            <Button onClick={onClicked}>See Details</Button>
        </Stack>
    );
}

export default StudentComponent;

//To use this you call:
// <StudentComponent student={obj} onClicked={ ()=> navigate("/students/4")}/>