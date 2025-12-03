package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;

public class ExerciseResponse {

    // Attributes
    private Integer exerciseId;
    private String name;
    private String type;
    private String description;
    private String difficulty;
    private String videoUrl;
    private String progressUnit;
    private BigDecimal estimatedUnitaryCaloriesBurnt;

    // Constructor with zero parameters
    public ExerciseResponse() {

    }

    // Constructor with all the attributes
    public ExerciseResponse(Integer exerciseId, String name, String type, String description, String difficulty, String videoUrl, String progressUnit, BigDecimal estimatedUnitaryCaloriesBurnt) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.difficulty = difficulty;
        this.videoUrl = videoUrl;
        this.progressUnit = progressUnit;
        this.estimatedUnitaryCaloriesBurnt = estimatedUnitaryCaloriesBurnt;
    }

    // Getters and Setters
    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
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
