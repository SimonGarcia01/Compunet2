
//Function to get the student list from the backend
const getAllStudents = async () => {
    let token = localStorage.getItem("accessToken");
    let response = await fetch('http://localhost:8080/api/v1/auth/login/')
}

export default getAllStudents;