package org.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "general_progress")
public class GeneralProgress {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;

    @JsonIgnore
    @OneToMany(mappedBy = "generalProgress",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Recommendation> recommendationsList;

    @JsonIgnore
    @OneToMany(mappedBy="generalProgress", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<CompleteExercise> completedExercises;

    //Attributes
    @Column(nullable= false, length=30)
    private String type;
    @Column(nullable = false, precision = 6, scale = 3)
    private BigDecimal percentage;
    @Column(nullable=false, length = 10)
    private String days_or_weeks;

    public GeneralProgress() {
        //Default constructor
    }

    public GeneralProgress(Integer progressId, String type, BigDecimal percentage, String days_or_weeks) {
        this.progressId = progressId;
        this.type = type;
        this.percentage = percentage;
        this.days_or_weeks = days_or_weeks;
    }

    // Getters and Setters
    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(Integer progressId) {
        this.progressId = progressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public List<Recommendation> getRecomendationsList() {
        return recommendationsList;
    }

    public void setRecommendationsList(List<Recommendation> recomendationsList) {
        this.recommendationsList = recomendationsList;
    }

    public String getDays_or_weeks() {
        return days_or_weeks;
    }

    public void setDays_or_weeks(String days_or_weeks) {
        this.days_or_weeks = days_or_weeks;
    }

    public List<CompleteExercise> getCompletedExercises() {
        return completedExercises;
    }

    public void setCompletedExercises(List<CompleteExercise> completedExercises) {
        this.completedExercises = completedExercises;
    }
}
