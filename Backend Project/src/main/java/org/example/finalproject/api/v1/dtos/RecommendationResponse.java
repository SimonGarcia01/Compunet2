package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.GeneralProgress;
import org.example.finalproject.entity.User;

import java.time.LocalDate;

public class RecommendationResponse {

    // Attributes
    private Integer recommendationId;
    private GeneralProgressResponse generalProgress;
    private UserResponse trainer;
    private String content;
    private LocalDate commentDate;

    // Constructor with zero parameters
    public RecommendationResponse() {

    }

    // Constructor with all the attributes
    public RecommendationResponse(Integer recommendationId, GeneralProgressResponse generalProgress, UserResponse trainer, String content, LocalDate commentDate) {
        this.recommendationId = recommendationId;
        this.generalProgress = generalProgress;
        this.trainer = trainer;
        this.content = content;
        this.commentDate = commentDate;
    }

    // Getters and Setters
    public Integer getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Integer recommendationId) {
        this.recommendationId = recommendationId;
    }

    public GeneralProgressResponse getGeneralProgress() {
        return generalProgress;
    }

    public void setGeneralProgress(GeneralProgressResponse generalProgress) {
        this.generalProgress = generalProgress;
    }

    public UserResponse getTrainer() {
        return trainer;
    }

    public void setTrainer(UserResponse trainer) {
        this.trainer = trainer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

}
