import {baseurl} from '../utils/constant';

const getStudentByCode = async (code) => {
    //Use the token for the request
    let token = localStorage.getItem("token");
    //Get the data from the backend
    let response = await fetch(`${baseurl}/students/${code}`, {
        method:"GET",
        headers:{
            "Authorization": `Bearer ${token}`
        }
    })
    //Get the json data
    let data = await response.json();
    console.log(data);
}

export default getStudentByCode;