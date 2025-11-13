//This will make the logic for the login action

import {baseurl} from '../utils/constant';

const login = async ({username, password}) => {

    let obj = {username: username, password: password};

    //Convert a OBJ to a String
    let json = JSON.stringify(obj);

    let response = await fetch(`${baseurl}/auth/login/`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: json
    });

    //Check if the login actually worked
    if(response.status === 200) {
            let data = await response.json();
            //Print the accessToken
            console.log(data.accessToken);
            //This is a preloaded variable from js
            localStorage.setItem("accessToken", data.accessToken);
            //This is a promise string that returns the access token
            return data.accessToken;
    } else {
        throw new Error("The login couldn't be done correctly.")
    }
}

//This makes the module, but we need to consume it somewhere
export default login;
//login({username: "profesor1@gmail.com", password: "123456"});

//Example of an object explenation
// let object = {
//     "name": "Person",
//     age: 21,
//     nationality: ["Country1", "Country2"]
// }
