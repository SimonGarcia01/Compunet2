package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.User;
import org.example.finalproject.entity.WorkoutProgram;

public class UserWorkoutProgramRequestResponse {

    // Attributes
    private UserRequest user;
    private WorkoutProgramRequest workoutProgram;

    // Constructor with zero parameters
    public UserWorkoutProgramRequestResponse() {

    }

    // Constructor with all the attributes
    public UserWorkoutProgramRequestResponse(UserRequest user, WorkoutProgramRequest workoutProgram) {
        this.user = user;
        this.workoutProgram = workoutProgram;
    }

    // Getters and Setters
    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public WorkoutProgramRequest getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgramRequest workoutProgram) {
        this.workoutProgram = workoutProgram;
    }

}
