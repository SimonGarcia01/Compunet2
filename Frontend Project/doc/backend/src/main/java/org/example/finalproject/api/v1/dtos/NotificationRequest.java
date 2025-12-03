package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.Event;

import java.time.LocalDateTime;

public class NotificationRequest {

    // Attributes
    private Integer eventId;
    private EventRequest event;
    private String title;
    private String text;

    // Constructor with zero parameters
    public NotificationRequest() {

    }

    // Constructor with all attributes
    public NotificationRequest(Integer eventId, EventRequest event, String title, String text) {
        this.eventId = eventId;
        this.event = event;
        this.title = title;
        this.text = text;
    }

    // Getters and Setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public EventRequest getEvent() {
        return event;
    }

    public void setEvent(EventRequest event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
