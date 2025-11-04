const getAllProfessors = async () => {
    let token = localStorage.getItem("accessToken");
    let response = await fetch(
        "http://localhost:8080/api/v1/professors/",
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

export default getAllProfessors;