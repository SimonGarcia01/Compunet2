import { configureStore } from "@reduxjs/toolkit";
import { combineReducers } from "redux";
import  exercisesReducer  from "./exercises/exercisesSlice";
import  workoutProgramsReducer  from "./workoutPrograms/workoutProgramsSlice";
import trainerReducer from './trainer/trainerSlice';
import { authReducer } from "./auth/reducer";
import recommendationsReducer from './recommendations/recommendationsSlice';
import userProgressReducer from './userProgress/userProgressSlice';
import eventsReducer from './events/eventsSlice';
import adminReducer from './admin/adminSlice';
import historyReducer from './history/historySlice';
import workoutReducer from './workout/workoutSlice';
const rootReducer = combineReducers({
  auth: authReducer,
  exercises: exercisesReducer,
  workoutPrograms: workoutProgramsReducer,
  trainer: trainerReducer,
  recommendations: recommendationsReducer,
  userProgress: userProgressReducer,
  events: eventsReducer,
  admin: adminReducer,
  history: historyReducer,
  workout: workoutReducer,
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof store.dispatch;

export const store = configureStore({
  reducer: rootReducer,
  // Redux Toolkit includes thunk middleware by default
});
store.subscribe(() => {
    const state = store.getState();
    localStorage.setItem("auth", JSON.stringify(state.auth));
  });