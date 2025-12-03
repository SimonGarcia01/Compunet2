package org.example.finalproject.api.v1.dtos;

public class EventAttendanceRequest {
    private Integer userId;
    private Integer eventId;

    public EventAttendanceRequest() {
        //Default constructor
    }

    public EventAttendanceRequest(Integer userId, Integer eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}

