import { baseurl } from "../utils/constants";

const deleteOrder = async ({orderId}) => {
    let token = localStorage.getItem("accessToken");

    let response = await fetch(`${baseurl}/domicilios/${orderId}`, {
        method:"DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    console.log(response)
}

export default deleteOrder;