package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.EventTypeRequest;
import org.example.finalproject.api.v1.dtos.EventTypeResponse;

import java.util.List;

public interface EventTypeService {

    List<EventTypeResponse> getAllEventTypes();
    EventTypeResponse findById(Integer id);
    void createEventType(EventTypeRequest eventTypeRequest);
    void updateEventType(Integer id, EventTypeRequest eventTypeRequest);
    void deleteEventType(Integer id);

}

