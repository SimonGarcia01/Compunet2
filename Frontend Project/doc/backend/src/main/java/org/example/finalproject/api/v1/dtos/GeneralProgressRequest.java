package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;

public class GeneralProgressRequest {

    // Attributes
    private String type;
    private BigDecimal percentage;
    private String days_or_weeks;

    // Constructor with zero parameters
    public GeneralProgressRequest() {

    }

    // Constructor with all attributes
    public GeneralProgressRequest(String type, BigDecimal percentage, String days_or_weeks) {
        this.type = type;
        this.percentage = percentage;
        this.days_or_weeks = days_or_weeks;
    }

    // Getters and Setters
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
