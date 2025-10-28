let counter = 0;

const increment = ()=>{
    counter++;
}

//To get the counter value
const getCounter = () => {
    return counter;
}

//Export the functions
//This are what ES modules are about
export {increment, getCounter};