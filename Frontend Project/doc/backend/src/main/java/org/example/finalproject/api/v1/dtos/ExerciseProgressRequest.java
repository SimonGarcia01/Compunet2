package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExerciseProgressRequest {

    // Attributes
    private UserRequest user;
    private WorkoutProgramRequest workoutProgram;
    private ExerciseRequest exercise;
    private Integer workoutId; // ID del WorkoutProgram existente (opcional)
    private Integer exerciseId; // ID del Exercise existente (opcional)
    private LocalDate recordDate;
    private String periodType; // 'DAILY' or 'WEEKLY'
    private Integer repetitions;
    private Integer timeMinutes;
    private BigDecimal distanceKm;
    private Integer rpe;
    private String notes;
    private BigDecimal estimatedCaloriesBurnt;

    // Constructor with zero parameters
    public ExerciseProgressRequest() {

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

    public ExerciseRequest getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseRequest exercise) {
        this.exercise = exercise;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
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

