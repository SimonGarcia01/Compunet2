package org.example.taller3mvc.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="complete_exercises")
public class CompleteExercise {

    //Primary Key
    @EmbeddedId
    private CompleteExerciseId id;

    //Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workoutProgramId")
    @JoinColumn(name="workout_program_id", nullable=false)
    private WorkoutProgram workoutProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exerciseId")
    @JoinColumn(name="exercise_id", nullable=false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("generalProgressId")
    @JoinColumn(name="general_progress_id", nullable=false)
    private GeneralProgress generalProgress;

    //Attributes
    @Column(nullable=false)
    private LocalDate dateCompletion;

    public CompleteExercise() {
        //Default Constructor
    }

    public CompleteExercise(CompleteExerciseId id, WorkoutProgram workoutProgram, Exercise exercise, GeneralProgress generalProgress, LocalDate dateCompletion) {
        this.id = id;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.generalProgress = generalProgress;
        this.dateCompletion = dateCompletion;
    }

    public CompleteExerciseId getId() {
        return id;
    }

    public void setId(CompleteExerciseId id) {
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

    public GeneralProgress getGeneralProgress() {
        return generalProgress;
    }

    public void setGeneralProgress(GeneralProgress generalProgress) {
        this.generalProgress = generalProgress;
    }

    public LocalDate getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(LocalDate dateCompletion) {
        this.dateCompletion = dateCompletion;
    }
}
