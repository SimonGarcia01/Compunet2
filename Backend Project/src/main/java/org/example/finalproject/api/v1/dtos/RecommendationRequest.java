package org.example.finalproject.api.v1.dtos;

public class RecommendationRequest {

    // Attributes
    private String content;

    // Constructor with zero parameters
    public RecommendationRequest() {

    }

    // Constructor with all the attributes
    public RecommendationRequest(String content) {
        this.content = content;
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
