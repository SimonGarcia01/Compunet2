package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.User;

import java.time.LocalDate;

public class TrainerTraineeRequest {

    // Attributes
    private UserRequest trainer;
    private UserRequest trainee;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor with zero parameters
    public TrainerTraineeRequest() {

    }

    // Constructor with all the attributes
    public TrainerTraineeRequest(UserRequest trainer, UserRequest trainee, LocalDate startDate, LocalDate endDate) {
        this.trainer = trainer;
        this.trainee = trainee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public UserRequest getTrainer() {
        return trainer;
    }

    public void setTrainer(UserRequest trainer) {
        this.trainer = trainer;
    }

    public UserRequest getTrainee() {
        return trainee;
    }

    public void setTrainee(UserRequest trainee) {
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
