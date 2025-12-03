package org.example.finalproject.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "historical_records")
public class HistoricalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    //Foreign keys
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "workout_program_id", nullable = true)
    private WorkoutProgram workoutProgram;

    // Attributes
    @Column(nullable = true, length = 200)
    private String details;
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal estimatedBurntCalories;

    public HistoricalRecord() {
        //default constructor
    }

    public HistoricalRecord(Integer recordId, String details, BigDecimal estimatedBurntCalories) {
        this.recordId = recordId;
        this.details = details;
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getEstimatedBurntCalories() {
        return estimatedBurntCalories;
    }

    public void setEstimatedBurntCalories(BigDecimal estimateBurntCalories) {
        this.estimatedBurntCalories = estimateBurntCalories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public WorkoutProgram getWorkoutProgram() {
        return workoutProgram;
    }

    public void setWorkoutProgram(WorkoutProgram workoutProgram) {
        this.workoutProgram = workoutProgram;
    }
}
