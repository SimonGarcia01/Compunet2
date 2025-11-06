
//Function to get the student list from the backend
import {baseurl} from '../utils/constant';

const getAllStudents = async () => {
    let token = localStorage.getItem("accessToken");
    let response = await fetch(
        `${baseurl}/auth/login/`,
        {
            method: "GET",
            headers:{
                "Authorization": `Bearer ${token}`
            }
        }
    );

    let data = await response.json();
    return data;
}

export default getAllStudents;