package org.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exercise_progress")
public class ExerciseProgress {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_program_id", nullable = true)
    private WorkoutProgram workoutProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = true)
    private Exercise exercise;

    // Attributes
    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false, length = 10)
    private String periodType; // 'DAILY' or 'WEEKLY'

    @Column(nullable = true)
    private Integer repetitions;

    @Column(nullable = true)
    private Integer timeMinutes;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal distanceKm;

    @Column(nullable = true)
    private Integer rpe; // Rate of Perceived Exertion (1-10)

    @Column(nullable = true, length = 500)
    private String notes;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal estimatedCaloriesBurnt;

    public ExerciseProgress() {
        // Default constructor
    }

    public ExerciseProgress(Integer progressId, User user, WorkoutProgram workoutProgram, Exercise exercise,
                           LocalDate recordDate, String periodType, Integer repetitions, Integer timeMinutes,
                           BigDecimal distanceKm, Integer rpe, String notes, BigDecimal estimatedCaloriesBurnt) {
        this.progressId = progressId;
        this.user = user;
        this.workoutProgram = workoutProgram;
        this.exercise = exercise;
        this.recordDate = recordDate;
        this.periodType = periodType;
        this.repetitions = repetitions;
        this.timeMinutes = timeMinutes;
        this.distanceKm = distanceKm;
        this.rpe = rpe;
        this.notes = notes;
        this.estimatedCaloriesBurnt = estimatedCaloriesBurnt;
    }

    // Getters and Setters
    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

