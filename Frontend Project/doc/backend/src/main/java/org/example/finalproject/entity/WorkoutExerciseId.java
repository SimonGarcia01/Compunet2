package org.example.finalproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WorkoutExerciseId implements Serializable {
    @Column(name="workout_program_id")
    private Integer workoutProgramId;

    @Column(name="exercise_id")
    private Integer exerciseId;

    public WorkoutExerciseId(Integer workoutProgramId, Integer exerciseId) {
        this.workoutProgramId = workoutProgramId;
        this.exerciseId = exerciseId;
    }

    public WorkoutExerciseId() {
        //default constructor
    }
    @Override
    public int hashCode() {
        return Objects.hash(workoutProgramId, exerciseId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WorkoutExerciseId that){
            return Objects.equals(this.workoutProgramId, that.workoutProgramId) && Objects.equals(this.exerciseId, that.exerciseId);
        }

        return false;
    }

    public Integer getWorkoutProgramId() {
        return workoutProgramId;
    }

    public void setWorkoutProgramId(Integer workoutProgramId) {
        this.workoutProgramId = workoutProgramId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
}
