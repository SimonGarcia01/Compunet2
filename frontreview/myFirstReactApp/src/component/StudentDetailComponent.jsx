import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import CourseComponent from "./CourseComponente";

const StudentDetailComponent = ({student, onClicked})  => {

    const [student, setStudent] = useState(null);
    const [courses, setCourses] = useState([]);

    const getStudent = async (code) => {
        l
    }

    useEffect(()=>{
        console.log(student);

    })

    return(
        <Stack>
            {student !== null && <StudentComponent student={student}/>}
            <Stack>
                {courses.length !== 0 && courses.map(
                    (course) => (<CourseComponent key={course.id} course={course}></CourseComponent>)
                )}
            </Stack>

            {/* <Typography variant="h4">Student Details</Typography>
            <Typography variant="h5">{student.name}</Typography>
            <Typography>{student.id}</Typography>
            <Typography>{student.code}</Typography>
            <Typography>{student.program}</Typography>
            <Stack>
                {student.courses.length > 0 && (
                    (obj) => (<CourseComponent key={obj.id} course={obj}></CourseComponent>)
                )}
            </Stack>
            <Button onClick={onClicked}>Go Back</Button> */}
        </Stack>
    );
}