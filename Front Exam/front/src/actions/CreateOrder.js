import { baseurl } from "../utils/constants";

const createOrder = async({nombreDomiciliario, estado, userId}) => {

    let order = {nombreDomiciliario:nombreDomiciliario, estado:estado, userId:userId};

    let json = JSON.stringify(order);

    let token = localStorage.getItem("accessToken");

    let response = await fetch(`${baseurl}/domicilios`,{
        method:"POST",
        headers: {
            "Content-Type":"application/json",
            "Authorization": `Bearer ${token}`
        },
        body: json
    });

    let data = await response.json();
    return data;

}

export default createOrder;