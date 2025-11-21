import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import TextField from '@mui/material/TextField';
import Button from "@mui/material/Button";

import OrderComponent from "../components/OrderComponent";
import getOrders from "../actions/GetOrders";
import createOrder from "../actions/CreateOrder";
import deleteOrder from "../actions/DeleteOrder";

const OrderListScreen = () => {

    const navigate = useNavigate();
    const [list, setList] = useState([]);

    const [nombreDomiciliario, setNombreDomiciliario] = useState("");
    const [estado, setEstado] = useState("");
    const [userId, setUserId] = useState("");

    const makeOrder = async() => {
        await createOrder({nombreDomiciliario:nombreDomiciliario, estado:estado, userId:userId});
        navigate("/orders");
    }

    const removeOrder = async(orderId) => {
        await deleteOrder({orderId:orderId});
    }

    useEffect(()=>{
        const handleOrders = async() => {
            let list = await getOrders();
            console.log(list);
            setList(list);
        };

        handleOrders();
    }, [])

    return(
        <Stack>
            <Typography variant="h1">Create Order:</Typography>
            <TextField label="Delivery Guy's Name" value={nombreDomiciliario} onChange={(event)=>setNombreDomiciliario(event.target.value)}/>
            <TextField label="Status" value={estado} onChange={(event)=>setEstado(event.target.value)}/>
            <TextField label="User ID" value={userId} onChange={(event)=>setUserId(event.target.value)}/>
            <Button onClick={makeOrder}>Create an Order</Button>
            <Typography variant="h1">Order List:</Typography>
            {list.length !== 0 && list.map(
                (obj) => (<OrderComponent 
                    key={obj.id}
                    order={obj}
                    editClick={()=>{navigate(`/update/`)}}
                    deleteClick={()=>{removeOrder(obj.id)}}></OrderComponent>)
            )}
        </Stack>
    );

    //nombreDomiciliario, estado, userId
}

export default OrderListScreen;