import {baseurl} from '../utils/constant';

const getStudentByCode = async ({studentCode}) => {
    //Use the token for the request
    let token = localStorage.getItem("accessToken");
    //Get the data from the backend
    let response = await fetch(`${baseurl}/students/${studentCode}`, {
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

export default getStudentByCode;