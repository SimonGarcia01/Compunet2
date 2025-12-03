package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.Event;

import java.time.LocalDateTime;

public class NotificationResponse {

    // Attributes
    private Integer notificationId;
    private EventResponse event;
    private String title;
    private String text;
    private LocalDateTime creationDateTime;

    // Constructor with zero parameters
    public NotificationResponse() {

    }

    // Constructor with all attributes
    public NotificationResponse(Integer notificationId, EventResponse event, String title, String text, LocalDateTime creationDateTime) {
        this.notificationId = notificationId;
        this.event = event;
        this.title = title;
        this.text = text;
        this.creationDateTime = creationDateTime;
    }

    // Getters and Setters
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

}
