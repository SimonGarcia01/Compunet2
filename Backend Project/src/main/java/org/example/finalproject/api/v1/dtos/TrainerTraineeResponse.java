package org.example.finalproject.api.v1.dtos;

import java.time.LocalDate;

public class TrainerTraineeResponse {

    // Attributes
    private Integer id;
    private UserResponse trainer;
    private UserResponse trainee;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor with zero parameters
    public TrainerTraineeResponse() {

    }

    // Constructor with all the attributes
    public TrainerTraineeResponse(Integer id, UserResponse trainer, UserResponse trainee, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.trainer = trainer;
        this.trainee = trainee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserResponse getTrainer() {
        return trainer;
    }

    public void setTrainer(UserResponse trainer) {
        this.trainer = trainer;
    }

    public UserResponse getTrainee() {
        return trainee;
    }

    public void setTrainee(UserResponse trainee) {
        this.trainee = trainee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
