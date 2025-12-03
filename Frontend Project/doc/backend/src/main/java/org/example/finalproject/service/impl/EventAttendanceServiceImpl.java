package org.example.finalproject.service.impl;

import org.example.finalproject.api.v1.dtos.EventAttendanceRequest;
import org.example.finalproject.api.v1.dtos.EventAttendanceResponse;
import org.example.finalproject.api.v1.mappers.EventAttendanceMapper;
import org.example.finalproject.entity.Event;
import org.example.finalproject.entity.EventAttendance;
import org.example.finalproject.entity.EventAttendanceId;
import org.example.finalproject.entity.User;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.EventAttendanceRepository;
import org.example.finalproject.repository.EventRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.service.EventAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventAttendanceServiceImpl implements EventAttendanceService {

    @Autowired
    private EventAttendanceRepository eventAttendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventAttendanceMapper eventAttendanceMapper;

    @Override
    public EventAttendanceResponse findById(Integer userId, Integer eventId) {
        //Make the id so then it can be used to look for it
        EventAttendanceId id = new EventAttendanceId();
        id.setUserId(userId);
        id.setEventId(eventId);

        EventAttendance attendance = eventAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for user ID: " + userId + " and event ID: " + eventId));

        return eventAttendanceMapper.toEventAttendanceResponse(attendance);
    }

    @Override
    public List<EventAttendanceResponse> getAllEventAttendances() {
        return eventAttendanceRepository.findAll().stream().map(
                eventAttendanceMapper::toEventAttendanceResponse
                ).toList();
    }

    @Transactional
    @Override
    public void createEventAttendance(EventAttendanceRequest request) {
        //First yuo must make sure that the user and the event exist
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        //Now you must make sure that this relation is not added yet
        EventAttendanceId id = new EventAttendanceId();
        id.setUserId(user.getUserId());
        id.setEventId(event.getEventId());

        if (eventAttendanceRepository.existsById(id)) {
            throw new UniquenessViolationException("User is already registered for this event.");
        }

        //Now you can make the actual relation
        EventAttendance attendance = new EventAttendance();
        attendance.setId(id);
        attendance.setUser(user);
        attendance.setEvent(event);
        attendance.setDateInscription(LocalDate.now());

        //SAVE FINALLY
        eventAttendanceRepository.save(attendance);
    }


    @Override
    public void deleteEventAttendance(Integer userId, Integer eventId) {
        //Again, I must make the id to check if it exists.
        EventAttendanceId id = new EventAttendanceId();
        id.setUserId(userId);
        id.setEventId(eventId);

        EventAttendance attendance = eventAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for user ID: " + userId + " and event ID: " + eventId));

        eventAttendanceRepository.delete(attendance);
    }
}