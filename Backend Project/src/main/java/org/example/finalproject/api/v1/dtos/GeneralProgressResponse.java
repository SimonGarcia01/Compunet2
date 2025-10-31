package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;

public class GeneralProgressResponse {

    // Attributes
    private Integer progressId;
    private String type;
    private BigDecimal percentage;
    private String days_or_weeks;

    // Constructor with zero parameters
    public GeneralProgressResponse() {

    }

    // Constructor with all attributes
    public GeneralProgressResponse(Integer progressId, String type, BigDecimal percentage, String days_or_weeks) {
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

    public String getDays_or_weeks() {
        return days_or_weeks;
    }

    public void setDays_or_weeks(String days_or_weeks) {
        this.days_or_weeks = days_or_weeks;
    }
}
