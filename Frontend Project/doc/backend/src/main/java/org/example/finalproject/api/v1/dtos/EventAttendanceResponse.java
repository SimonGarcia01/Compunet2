package org.example.finalproject.api.v1.dtos;

import java.time.LocalDate;

public class EventAttendanceResponse {
    private UserResponse user;
    private EventResponse event;
    private LocalDate dateInscription;

    public EventAttendanceResponse() {
        //Default constructor
    }

    public EventAttendanceResponse(UserResponse user, EventResponse event, LocalDate dateInscription) {
        this.user = user;
        this.event = event;
        this.dateInscription = dateInscription;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public EventResponse getEvent() {
        return event;
    }

    public void setEvent(EventResponse event) {
        this.event = event;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
}
