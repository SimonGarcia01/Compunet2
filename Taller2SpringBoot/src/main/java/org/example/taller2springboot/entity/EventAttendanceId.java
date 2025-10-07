package org.example.taller2springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventAttendanceId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "event_id")
    private Integer eventId;

    public EventAttendanceId(Integer userId, Integer eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public EventAttendanceId() {
        //Default constructor
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventAttendanceId that) {
            return Objects.equals(this.userId, that.userId) && Objects.equals(this.eventId, that.eventId);
        }

        return false;
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