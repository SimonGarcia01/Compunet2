import Stack from "@mui/material/Stack";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import getStudentByCode from "../actions/GetStudentByCode";
import getCoursesByStudentId from "../actions/GetCoursesByStudentId";



const  StudentDetailScreen = ()=>{
    //This will get the parameter from the url that is sent with
    //EX: /students/3  -> studentCode= 3
    const { studentCode } = useParams(); 

    const [student, setStudent] = useState(null);
    const [courses, setCourses] = useState([]);

    const getStudent = async (studentCode) => {
        const student = await getStudentByCode({studentCode: studentCode});
        setStudent(student);
    }

    const getStudentCourses = async (studentCode) => {
        const courses = await getCoursesByStudentId({studentCode: studentCode});
        setCourses(courses);
    }

    useEffect(()=>{
        console.log(studentCode);
        getStudent(studentCode);
        getStudentCourses(studentCode);
    }, [])

    return(
        <Stack>
            {student !== null && <StudentComponent student={student}/>}
            <Stack>
                {courses.length !== 0 && courses.map(
                    (course) => (<CourseComponent key={course.id} course={course}></CourseComponent>)
                )}
            </Stack>
        </Stack>
    );

}
export default StudentDetailScreen;