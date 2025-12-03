package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.User;

import java.time.LocalDate;

public class WorkoutProgramResponse {

    // Attributes
    private Integer workoutId;
    private String name;
    private String description;
    private String photoUrl;
    private LocalDate creationDate;
    private boolean completed;
    private UserResponse user;

    // Constructor with zero parameters
    public WorkoutProgramResponse() {

    }

    // Constructor with all the attributes
    public WorkoutProgramResponse(Integer workoutId, String name, String description, String photoUrl, LocalDate creationDate, boolean completed, UserResponse user) {
        this.workoutId = workoutId;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.creationDate = creationDate;
        this.completed = completed;
        this.user = user;
    }

    // Getters and Setters
    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

}