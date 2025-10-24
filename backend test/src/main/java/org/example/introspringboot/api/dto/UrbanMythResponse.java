package org.example.introspringboot.api.dto;

public class UrbanMythResponse {

    private String title;
    private String originCity;
    private Double credibilityScore;
    private Double viralSpreadIndex;
    private Integer socialMediaMentions;

    public UrbanMythResponse(String title, String originCity, Double credibilityScore, Double viralSpreadIndex, Integer socialMediaMentions) {
        this.title = title;
        this.originCity = originCity;
        this.credibilityScore = credibilityScore;
        this.viralSpreadIndex = viralSpreadIndex;
        this.socialMediaMentions = socialMediaMentions;
    }

    public UrbanMythResponse() {
        //Default Constructor
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public Double getCredibilityScore() {
        return credibilityScore;
    }

    public void setCredibilityScore(Double credibilityScore) {
        this.credibilityScore = credibilityScore;
    }

    public Double getViralSpreadIndex() {
        return viralSpreadIndex;
    }

    public void setViralSpreadIndex(Double viralSpreadIndex) {
        this.viralSpreadIndex = viralSpreadIndex;
    }

    public Integer getSocialMediaMentions() {
        return socialMediaMentions;
    }

    public void setSocialMediaMentions(Integer socialMediaMentions) {
        this.socialMediaMentions = socialMediaMentions;
    }
}
