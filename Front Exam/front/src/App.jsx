import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import LoginForm from "./screens/LoginForm";
import NotFound from "./screens/NotFound";
import OrderListScreen from "./screens/OrderListScreen";
import UpdateScreen from "./screens/UpdateScreen";


const router = createBrowserRouter([
  {
    path:"/",
    element: <LoginForm/>
  },
  {
    path:"/orders",
    element:<OrderListScreen/>
  },
  {
    path:"/update",
    element:<UpdateScreen/>
  },
  {
    path:"*",
    element: <NotFound/>
  }
]);

const App = () => {
  return (
    <RouterProvider router={router}/>
  );
}

export default App;