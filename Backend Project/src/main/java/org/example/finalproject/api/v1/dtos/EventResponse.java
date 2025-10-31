package org.example.finalproject.api.v1.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventResponse {

    // Attributes
    private Integer eventId;
    private EventTypeResponse eventType;
    private AvailableSpaceResponse availableSpace;
    private UserResponse user;
    private String name;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private String description;
    private LocalDate creationDate;
    private int maxAttendees;
    private String status;
    private BigDecimal estimatedBurntCalories;

    // Constructor with zero parameters
    public EventResponse(){

    }

    // Constructor with all the attributes
    public EventResponse(Integer eventId, EventTypeResponse eventType, AvailableSpaceResponse availableSpace, UserResponse user, String name, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String description, LocalDate creationDate, int maxAttendees, String status, BigDecimal estimatedBurntCalories) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.availableSpace = availableSpace;
        this.user = user;
        this.name = name;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.description = description;
        this.creationDate = creationDate;
        this.maxAttendees = maxAttendees;
        this.status = status;
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public EventTypeResponse getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeResponse eventType) {
        this.eventType = eventType;
    }

    public AvailableSpaceResponse getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(AvailableSpaceResponse availableSpace) {
        this.availableSpace = availableSpace;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getEstimatedBurntCalories() {
        return estimatedBurntCalories;
    }

    public void setEstimatedBurntCalories(BigDecimal estimatedBurntCalories) {
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

}
