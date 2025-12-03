package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.EventRequest;
import org.example.finalproject.api.v1.dtos.EventResponse;
import java.util.List;

public interface EventService {
    List<EventResponse> getAllEvents();
    EventResponse findById(Integer id);
    void createEvent(EventRequest eventRequest);
    void updateEvent(Integer id, EventRequest eventRequest);
    void deleteEvent(Integer id);
}

