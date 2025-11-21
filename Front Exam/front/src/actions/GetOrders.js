import { baseurl } from "../utils/constants";

const getOrders = async() => {
    let token = localStorage.getItem("accessToken");
    console.log(token);
    let response = await fetch(`${baseurl}/domicilios`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    let data = await response.json()
    return data;
}

export default getOrders;