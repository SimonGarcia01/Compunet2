package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;

public class HistoricalRecordResponse {

    // Attributes
    private Integer recordId;
    private UserResponse user;
    private EventResponse event;
    private WorkoutProgramResponse workoutProgram;
    private String details;
    private BigDecimal estimatedBurntCalories;

    // Constructor with zero parameters
    public HistoricalRecordResponse() {

    }

    // Constructor with all attributes
    public HistoricalRecordResponse(Integer recordId, UserResponse user, EventResponse event, WorkoutProgramResponse workoutProgram, String details, BigDecimal estimatedBurntCalories) {
        this.recordId = recordId;
        this.user = user;
        this.event = event;
        this.workoutProgram = workoutProgram;
        this.details = details;
        this.estimatedBurntCalories = estimatedBurntCalories;
    }


    // Getters and Setters
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
        this.event = event;
    }

    public WorkoutProgramResponse getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgramResponse workoutProgram) {
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
