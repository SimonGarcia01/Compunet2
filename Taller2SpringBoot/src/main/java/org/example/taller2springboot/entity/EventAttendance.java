package org.example.taller2springboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

//Intermediate table between Events and Users
@Entity
@Table(name="events_attendance")
public class EventAttendance {

    @EmbeddedId
    private EventAttendanceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name="event_id", nullable=false)
    private Event event;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false)
    private LocalDate dateInscription;

    public EventAttendance(EventAttendanceId id, Event event, User user, LocalDate dateInscription) {
        this.id = id;
        this.event = event;
        this.user = user;
        this.dateInscription = dateInscription;
    }
    public EventAttendance(){
        //default constructor
    }

    public EventAttendanceId getId() {
        return id;
    }

    public void setId(EventAttendanceId id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
}
