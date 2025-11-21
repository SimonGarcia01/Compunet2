import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

const OrderComponent = ({order, editClick, deleteClick}) => {
    return(
        <Stack>
            <Typography variant="h2">{order.id}</Typography>
            <Typography variant="h4">{order.nombreDomiciliario}</Typography>
            <Typography variant="h4">{order.estado}</Typography>
            <Typography variant="h4">{order.userId}</Typography>
            <Typography variant="h4">{order.username}</Typography>
            <Button onClick={editClick}>Edit Order</Button>
            <Button onClick={deleteClick}>Delete Order</Button>
        </Stack>
    );
}

export default OrderComponent;

    // private Long id;
    // private String nombreDomiciliario;
    // private EstadoDomicilio estado;
    // private Long userId;
    // private String username;