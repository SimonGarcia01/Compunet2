package org.example.taller2springboot.entity;

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
    @Column(nullable=false, precision=5, scale=3)
    private BigDecimal exerciseProgress;

    @Column(nullable=true)
    private int series;

    public WorkoutExercise(WorkoutExerciseId id, WorkoutProgram workoutProgram, Exercise exercise, BigDecimal exerciseProgress, int series) {
        this.id = id;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.exerciseProgress = exerciseProgress;
        this.series = series;
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

    public BigDecimal getExerciseProgress() {
        return exerciseProgress;
    }

    public void setExerciseProgress(BigDecimal exerciseProgress) {
        this.exerciseProgress = exerciseProgress;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }
}
