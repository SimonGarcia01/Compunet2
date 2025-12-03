package org.example.finalproject.entity;

import jakarta.persistence.*;

//Intermediate table between User and WorkoutProgram
@Entity
@Table(name = "users_workoutprograms")
public class UserWorkoutProgram {
    @EmbeddedId
    private UserWorkoutId userWorkoutId;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("workoutId")
    @JoinColumn(name="workout_id", nullable=false)
    private WorkoutProgram workoutProgram;


    public UserWorkoutProgram() {
        // default constructor
    }

    public UserWorkoutProgram(UserWorkoutId userWorkoutId, User user, WorkoutProgram workoutProgram) {
        this.userWorkoutId = userWorkoutId;
        this.user = user;
        this.workoutProgram = workoutProgram;
    }

    public UserWorkoutId getUserWorkoutId() {
        return userWorkoutId;
    }

    public void setUserWorkoutId(UserWorkoutId userWorkoutId) {
        this.userWorkoutId = userWorkoutId;
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
}
