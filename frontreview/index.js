//Now you can import the functions from the other files
import {increment, getCounter} from'./counter.js';

//const is a like a final variable, cannot be changed
//this represent like you take the resource and you can use it
const paragraph = document.getElementById('paragraph');
const button = document.getElementById('button');

//Now we do this from the outside
//let is a variable that can be changed
//let count = 0;

//var variable is a function varibale


//This is an arrow function
const add = () => {
    //Update the count variable
    //count++;
    increment();

    //Now we need to update the paragraph
    paragraph.innerText =`The current count is at: ${getCounter()}`;
}

button.addEventListener('click', add);

