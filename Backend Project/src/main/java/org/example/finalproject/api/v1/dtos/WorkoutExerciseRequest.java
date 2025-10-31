package org.example.finalproject.api.v1.dtos;

public class WorkoutExerciseRequest {

    // Attributes
    private Integer workoutId;
    private Integer exerciseId;
    private WorkoutProgramRequest workoutProgram;
    private ExerciseRequest exercise;
    private int series;
    private int session;
    private int amount;

    // Constructor with zero parameters
    public WorkoutExerciseRequest() {

    }

    // Constructor with all attributes
    public WorkoutExerciseRequest(Integer workoutId, Integer exerciseId, WorkoutProgramRequest workoutProgram, ExerciseRequest exercise, int series, int session, int amount) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.series = series;
        this.session = session;
        this.amount = amount;
    }

    // Getters and Setters
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
