import {baseurl} from "../utils/constants";

const login = async ({username, password})=>{

    let obj = {username: username, password:password};
    
    let json = JSON.stringify(obj);

    let response = await fetch(`${baseurl}/auth/login`, {
        method:"POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: json
    });

    console.log(response);
    if(response.status === 200){
        let data = await response.json();
        console.log(data.accessToken);
        localStorage.setItem("accessToken", data.accessToken);
    }else{
        throw new Error("The login couldn't be done correctly.");
    }
    
}

export default login;