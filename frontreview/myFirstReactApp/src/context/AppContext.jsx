import { createContext, useState, useContext } from "react";

const AppContext = createContext();

//Childern is a wrapper for all the components that will use this context
const AppProvider = ({ children }) => {
    //This is the global user state
    const [user, setUser] = useState(null);

    //The objective of this context is to provide the user and setUser to all the components that need it
    //The wrapper will then wrap all componentes that need this context
    return (
    <AppContext.Provider value={{ user, setUser }}>
        {children}
    </AppContext.Provider>
    );
}

//To use the AppContext in any component
export {AppProvider, AppContext};