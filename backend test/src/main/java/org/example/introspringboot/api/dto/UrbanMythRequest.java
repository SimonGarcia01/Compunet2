package org.example.introspringboot.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UrbanMythRequest {
    private Long mythId;
    private String title;
    private String originCity;
    private String country;
    private Integer firstReportedYear;
    private String description;
    private String keyCharacters;
    private Double credibilityScore;
    private String sourceType;
    private LocalDate lastSightingDate;
    private Double latitude;
    private Double longitude;
    private Double viralSpreadIndex;
    private Integer socialMediaMentions;
    private String reportedBy;
    private String audioRecordingUrl;
    private String imageEvidenceUrl;
    private Boolean debunked = false;
    private LocalDateTime createdAt;

    public UrbanMythRequest() {
        //Default Constructor
    }

    public UrbanMythRequest(Long mythId, String title, String originCity, String country, Integer firstReportedYear, String description, String keyCharacters, Double credibilityScore, String sourceType, LocalDate lastSightingDate, Double latitude, Double longitude, Double viralSpreadIndex, Integer socialMediaMentions, String reportedBy, String audioRecordingUrl, String imageEvidenceUrl, Boolean debunked, LocalDateTime createdAt) {
        this.mythId = mythId;
        this.title = title;
        this.originCity = originCity;
        this.country = country;
        this.firstReportedYear = firstReportedYear;
        this.description = description;
        this.keyCharacters = keyCharacters;
        this.credibilityScore = credibilityScore;
        this.sourceType = sourceType;
        this.lastSightingDate = lastSightingDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.viralSpreadIndex = viralSpreadIndex;
        this.socialMediaMentions = socialMediaMentions;
        this.reportedBy = reportedBy;
        this.audioRecordingUrl = audioRecordingUrl;
        this.imageEvidenceUrl = imageEvidenceUrl;
        this.debunked = debunked;
        this.createdAt = createdAt;
    }

    public Long getMythId() {
        return mythId;
    }

    public void setMythId(Long mythId) {
        this.mythId = mythId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getFirstReportedYear() {
        return firstReportedYear;
    }

    public void setFirstReportedYear(Integer firstReportedYear) {
        this.firstReportedYear = firstReportedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyCharacters() {
        return keyCharacters;
    }

    public void setKeyCharacters(String keyCharacters) {
        this.keyCharacters = keyCharacters;
    }

    public Double getCredibilityScore() {
        return credibilityScore;
    }

    public void setCredibilityScore(Double credibilityScore) {
        this.credibilityScore = credibilityScore;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public LocalDate getLastSightingDate() {
        return lastSightingDate;
    }

    public void setLastSightingDate(LocalDate lastSightingDate) {
        this.lastSightingDate = lastSightingDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getAudioRecordingUrl() {
        return audioRecordingUrl;
    }

    public void setAudioRecordingUrl(String audioRecordingUrl) {
        this.audioRecordingUrl = audioRecordingUrl;
    }

    public String getImageEvidenceUrl() {
        return imageEvidenceUrl;
    }

    public void setImageEvidenceUrl(String imageEvidenceUrl) {
        this.imageEvidenceUrl = imageEvidenceUrl;
    }

    public Boolean getDebunked() {
        return debunked;
    }

    public void setDebunked(Boolean debunked) {
        this.debunked = debunked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
