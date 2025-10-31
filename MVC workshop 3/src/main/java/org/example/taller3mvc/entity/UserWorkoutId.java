package org.example.taller3mvc.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserWorkoutId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "workout_Id")
    private Integer workoutId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, workoutId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserWorkoutId that) {
            return Objects.equals(this.userId, that.userId) && Objects.equals(this.workoutId, that.workoutId);
        }

        return false;
    }

    public UserWorkoutId(Integer userId, Integer workoutId) {
        this.userId = userId;
        this.workoutId = workoutId;
    }

    public UserWorkoutId() {
        //Default Constructor
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }
}
