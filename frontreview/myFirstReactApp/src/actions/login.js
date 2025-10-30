//This will make the logic for the login action

const login = async ({username, password}) => {

    let obj = {username: username, password: password};

    //Convert a OBJ to a String
    let json = JSON.stringify(obj);

    let response = await fetch('http://localhost:8080/api/v1/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: json
    });

    console.log(response);
}

login({username: "profesor1@gmail.com", password: "123456"});

//Example of an object explenation
// let object = {
//     "name": "Person",
//     age: 21,
//     nationality: ["Country1", "Country2"]
// }
