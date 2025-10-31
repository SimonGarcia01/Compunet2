package org.example.taller3mvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer exerciseId;

    //foreign key
    @JsonIgnore
    @OneToMany(mappedBy="exercise", cascade= CascadeType.ALL,orphanRemoval = true)
    private List<WorkoutExercise> workoutExerciseList;

    //Attributes
    @Column(nullable = false,length = 30)
    private String name;

    @Column(nullable = false,length = 30)
    private String type;

    @Column(nullable = false,length = 50)
    private String description;

    @Column(nullable=false,length = 20)
    private String difficulty;

    @Column(nullable = false,length = 100)
    private String videoUrl;

    @Column(nullable=false , length=10)
    private String progressUnit;

    @Column(nullable = true, precision = 10, scale=2)
    private BigDecimal estimatedUnitaryCaloriesBurnt;

    public Exercise(Integer exerciseId, String name, String type, String description, String difficulty, String videoUrl, String progressUnit, BigDecimal estimatedUnitaryCaloriesBurnt) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.difficulty = difficulty;
        this.videoUrl = videoUrl;
        this.progressUnit = progressUnit;
        this.estimatedUnitaryCaloriesBurnt = estimatedUnitaryCaloriesBurnt;
    }

    public Exercise() {
        //default constructor
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public List<WorkoutExercise> getWorkoutExerciseList() {
        return workoutExerciseList;
    }

    public void setWorkoutExerciseList(List<WorkoutExercise> workoutExerciseList) {
        this.workoutExerciseList = workoutExerciseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getProgressUnit() {
        return progressUnit;
    }

    public void setProgressUnit(String progressUnit) {
        this.progressUnit = progressUnit;
    }

    public BigDecimal getEstimatedUnitaryCaloriesBurnt() {
        return estimatedUnitaryCaloriesBurnt;
    }

    public void setEstimatedUnitaryCaloriesBurnt(BigDecimal estimatedUnitaryCaloriesBurnt) {
        this.estimatedUnitaryCaloriesBurnt = estimatedUnitaryCaloriesBurnt;
    }
}
