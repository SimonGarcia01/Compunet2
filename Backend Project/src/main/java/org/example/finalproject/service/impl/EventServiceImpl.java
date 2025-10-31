package org.example.finalproject.service.impl;

import org.example.finalproject.api.v1.dtos.EventRequest;
import org.example.finalproject.api.v1.dtos.EventResponse;
import org.example.finalproject.api.v1.mappers.EventMapper;
import org.example.finalproject.entity.Event;
import org.example.finalproject.repository.EventRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.repository.AvailableSpaceRepository;
import org.example.finalproject.repository.EventTypeRepository;
import org.example.finalproject.entity.User;
import org.example.finalproject.entity.AvailableSpace;
import org.example.finalproject.entity.EventType;
import org.example.finalproject.service.EventService;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvailableSpaceRepository availableSpaceRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventResponse findById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        return eventMapper.toEventResponse(event);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    @Override
    public void deleteEvent(Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete — event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    public void createEvent(EventRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new MissingInfoException("Event name is required");
        }
        if (request.getDateTimeStart() == null || request.getDateTimeEnd() == null) {
            throw new MissingInfoException("Start and end date/time are required");
        }

        if (eventRepository.existsByName(request.getName())) {
            throw new UniquenessViolationException("Event with this name already exists");
        }

        Event event = eventMapper.toEvent(request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        AvailableSpace availableSpace = availableSpaceRepository.findById(request.getAvailableSpaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Available space not found with ID: " + request.getAvailableSpaceId()));

        EventType eventType = eventTypeRepository.findById(request.getEventTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Event type not found with ID: " + request.getEventTypeId()));

        event.setUser(user);
        event.setAvailableSpace(availableSpace);
        event.setEventType(eventType);

        event.setCreationDate(LocalDate.now());
        event.setStatus("ACTIVE");

        eventRepository.save(event);
    }

    @Override
    public void updateEvent(Integer id, EventRequest request) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));

        if (request.getName() != null && !request.getName().equals(existingEvent.getName())) {
            eventRepository.findByName(request.getName()).ifPresent(e -> {
                if (!e.getEventId().equals(id)) {
                    throw new UniquenessViolationException("Another event with this name already exists");
                }
            });
            existingEvent.setName(request.getName());
        }

        if (request.getDescription() != null)
            existingEvent.setDescription(request.getDescription());

        if (request.getDateTimeStart() != null)
            existingEvent.setDateTimeStart(request.getDateTimeStart());

        if (request.getDateTimeEnd() != null)
            existingEvent.setDateTimeEnd(request.getDateTimeEnd());

        if (request.getMaxAttendees() != null)
            existingEvent.setMaxAttendees(request.getMaxAttendees());

        if (request.getEstimatedBurntCalories() != null)
            existingEvent.setEstimatedBurntCalories(request.getEstimatedBurntCalories());

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));
            existingEvent.setUser(user);
        }

        if (request.getAvailableSpaceId() != null) {
            AvailableSpace availableSpace = availableSpaceRepository.findById(request.getAvailableSpaceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Available space not found with ID: " + request.getAvailableSpaceId()));
            existingEvent.setAvailableSpace(availableSpace);
        }

        if (request.getEventTypeId() != null) {
            EventType eventType = eventTypeRepository.findById(request.getEventTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event type not found with ID: " + request.getEventTypeId()));
            existingEvent.setEventType(eventType);
        }

        eventRepository.save(existingEvent);
    }
}