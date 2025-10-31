package org.example.taller2springboot.entity;

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
    private List<Recommendation> recomendationsList;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false),
            @JoinColumn(name = "workout_id", referencedColumnName = "workout_id", nullable = false)
    })
    private UserWorkoutProgram userWorkoutProgram;

    //Attributes
    @Column(nullable= false, length=30)
    private String type;
    @Column(nullable = false, precision = 6, scale = 3)
    private BigDecimal percentage;

    public GeneralProgress() {
        //Default constructor
    }

    public GeneralProgress(Integer progressId, String type, BigDecimal percentage) {
        this.progressId = progressId;
        this.type = type;
        this.percentage = percentage;
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
        return recomendationsList;
    }

    public void setRecommendationsList(List<Recommendation> recomendationsList) {
        this.recomendationsList = recomendationsList;
    }

    public UserWorkoutProgram getUserWorkoutProgram() {
        return userWorkoutProgram;
    }

    public void setUserWorkoutProgram(UserWorkoutProgram userWorkoutProgram) {
        this.userWorkoutProgram = userWorkoutProgram;
    }

}
