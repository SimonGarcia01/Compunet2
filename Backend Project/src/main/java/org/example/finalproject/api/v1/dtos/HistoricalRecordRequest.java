package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;

public class HistoricalRecordRequest {

    // Attributes
    private UserRequest user;
    private EventRequest event;
    private WorkoutProgramRequest workoutProgram;
    private String details;
    private BigDecimal estimatedBurntCalories;

    // Constructor with zero parameters
    public HistoricalRecordRequest() {

    }

    // Constructor with all attributes
    public HistoricalRecordRequest(UserRequest user, EventRequest event, WorkoutProgramRequest workoutProgram, String details, BigDecimal estimatedBurntCalories) {
        this.user = user;
        this.event = event;
        this.workoutProgram = workoutProgram;
        this.details = details;
        this.estimatedBurntCalories = estimatedBurntCalories;
    }


    // Getters and Setters
    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public EventRequest getEvent() {
        return event;
    }

    public void setEvent(EventRequest event) {
        this.event = event;
    }

    public WorkoutProgramRequest getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgramRequest workoutProgram) {
        this.workoutProgram = workoutProgram;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getEstimatedBurntCalories() {
        return estimatedBurntCalories;
    }

    public void setEstimatedBurntCalories(BigDecimal estimatedBurntCalories) {
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

}
