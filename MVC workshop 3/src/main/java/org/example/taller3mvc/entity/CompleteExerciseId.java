package org.example.taller3mvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Table
@Embeddable
public class CompleteExerciseId implements Serializable {

    @Column(name="workout_program_id")
    private Integer workoutProgramId;

    @Column(name="exercise_id")
    private Integer exerciseId;

    @Column(name="general_progress_id")
    private Integer generalProgressId;

    @Override
    public int hashCode() {
        return Objects.hash(workoutProgramId, exerciseId, generalProgressId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CompleteExerciseId that){
            return Objects.equals(this.workoutProgramId, that.workoutProgramId) &&
                    Objects.equals(this.exerciseId, that.exerciseId) &&
                    Objects.equals(this.generalProgressId, that.generalProgressId);
        }

        return false;
    }

    public CompleteExerciseId(Integer workoutProgramId, Integer exerciseId, Integer generalProgressId) {
        this.workoutProgramId = workoutProgramId;
        this.exerciseId = exerciseId;
        this.generalProgressId = generalProgressId;
    }

    public CompleteExerciseId() {
        //Default constructor
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

    public Integer getGeneralProgressId() {
        return generalProgressId;
    }

    public void setGeneralProgressId(Integer generalProgressId) {
        this.generalProgressId = generalProgressId;
    }
}
