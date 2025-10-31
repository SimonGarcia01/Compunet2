package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.WorkoutExerciseId;

public class WorkoutExerciseResponse {

    // Attributes
    private WorkoutExerciseId id;
    private WorkoutProgramResponse workoutProgram;
    private ExerciseRequest exercise;
    private int series;
    private int session;
    private int amount;

    // Constructor with zero parameters
    public WorkoutExerciseResponse() {

    }

    // Constructor with all attributes
    public WorkoutExerciseResponse(WorkoutExerciseId id, WorkoutProgramResponse workoutProgram, ExerciseRequest exercise, int series, int session, int amount) {
        this.id = id;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.series = series;
        this.session = session;
        this.amount = amount;
    }

    // Getters and Setters
    public WorkoutExerciseId getId() {
        return id;
    }

    public void setId(WorkoutExerciseId id) {
        this.id = id;
    }

    public WorkoutProgramResponse getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgramResponse workoutProgram) {
        this.workoutProgram = workoutProgram;
    }

    public ExerciseRequest getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseRequest exercise) {
        this.exercise = exercise;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
