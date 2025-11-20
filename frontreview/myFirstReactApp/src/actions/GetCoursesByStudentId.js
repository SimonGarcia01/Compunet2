import {baseurl} from '../utils/constant';

const getCoursesByStudentId = async ({studentCode}) => {
    //Use the token for the request
    let token = localStorage.getItem("accessToken");
    //Get the data from the backend
    let response = await fetch(`${baseurl}/students/${studentCode}/courses`, {
        method:"GET",
        headers:{
            "Authorization": `Bearer ${token}`
        }
    })
    //Get the json data
    let data = await response.json();
    console.log(data);
    return data;
}

export default getCoursesByStudentId;