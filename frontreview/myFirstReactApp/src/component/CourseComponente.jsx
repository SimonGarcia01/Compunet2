import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

//Made this for the list of courses
const CourseComponent = ({course}) => {
    return(
        <Stack>
            <Typography variant="h5">{course.name}</Typography>
            <Typography>{course.credits}</Typography>
        </Stack>
    );
}

export default CourseComponent;