package org.example.finalproject.api.v1.dtos;

public class RecommendationRequest {

    // Attributes
    private String content;
    private Integer generalProgressId; // Opcional: ID del GeneralProgress asociado

    // Constructor with zero parameters
    public RecommendationRequest() {

    }

    // Constructor with all the attributes
    public RecommendationRequest(String content) {
        this.content = content;
    }

    public RecommendationRequest(String content, Integer generalProgressId) {
        this.content = content;
        this.generalProgressId = generalProgressId;
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGeneralProgressId() {
        return generalProgressId;
    }

    public void setGeneralProgressId(Integer generalProgressId) {
        this.generalProgressId = generalProgressId;
    }

}
