package org.example.taller3mvc.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

//Intermediate table between exercises and workout programs
@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {

    @EmbeddedId
    private WorkoutExerciseId id;

    // Foreign Keys
    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("workoutProgramId")
    @JoinColumn(name="workout_program_id", nullable=false)
    private WorkoutProgram workoutProgram;


    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("exerciseId")
    @JoinColumn(name="exercise_id", nullable=false)
    private Exercise exercise;

    //Attributes
    @Column(nullable=false)
    private int series;

    @Column(nullable=false)
    private int session;

    @Column(nullable=false)
    private int amount;

    public WorkoutExercise(WorkoutExerciseId id, WorkoutProgram workoutProgram, Exercise exercise, int series, int session, int amount) {
        this.id = id;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.series = series;
        this.session = session;
        this.amount = amount;
    }

    public WorkoutExercise() {
        //Default constructor
    }

    public WorkoutExerciseId getId() {
        return id;
    }

    public void setId(WorkoutExerciseId id) {
        this.id = id;
    }

    public WorkoutProgram getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgram workoutProgram) {
        this.workoutProgram = workoutProgram;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
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
