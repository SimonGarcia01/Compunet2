package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExerciseProgressResponse {

    // Attributes
    private Integer progressId;
    private UserResponse user;
    private WorkoutProgramResponse workoutProgram;
    private ExerciseResponse exercise;
    private LocalDate recordDate;
    private String periodType; // 'DAILY' or 'WEEKLY'
    private Integer repetitions;
    private Integer timeMinutes;
    private BigDecimal distanceKm;
    private Integer rpe;
    private String notes;
    private BigDecimal estimatedCaloriesBurnt;

    // Constructor with zero parameters
    public ExerciseProgressResponse() {

    }

    // Getters and Setters
    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public WorkoutProgramResponse getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgramResponse workoutProgram) {
        this.workoutProgram = workoutProgram;
    }

    public ExerciseResponse getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseResponse exercise) {
        this.exercise = exercise;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(Integer timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getRpe() {
        return rpe;
    }

    public void setRpe(Integer rpe) {
        this.rpe = rpe;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getEstimatedCaloriesBurnt() {
        return estimatedCaloriesBurnt;
    }

    public void setEstimatedCaloriesBurnt(BigDecimal estimatedCaloriesBurnt) {
        this.estimatedCaloriesBurnt = estimatedCaloriesBurnt;
    }
}

