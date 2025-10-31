package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventRequest {

    private String name;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private String description;
    private Integer maxAttendees;
    private BigDecimal estimatedBurntCalories;

    //This will later be assigned
    private Integer userId;
    private Integer availableSpaceId;
    private Integer eventTypeId;

    public EventRequest() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(Integer maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public BigDecimal getEstimatedBurntCalories() {
        return estimatedBurntCalories;
    }

    public void setEstimatedBurntCalories(BigDecimal estimatedBurntCalories) {
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAvailableSpaceId() {
        return availableSpaceId;
    }

    public void setAvailableSpaceId(Integer availableSpaceId) {
        this.availableSpaceId = availableSpaceId;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}