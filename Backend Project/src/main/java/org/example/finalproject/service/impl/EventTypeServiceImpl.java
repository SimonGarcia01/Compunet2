package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.EventTypeRequest;
import org.example.finalproject.api.v1.dtos.EventTypeResponse;
import org.example.finalproject.entity.EventType;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.api.v1.mappers.EventTypeMapper;
import org.example.finalproject.repository.EventTypeRepository;
import org.example.finalproject.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventTypeMapper eventTypeMapper;

    @Override
    public List<EventTypeResponse> getAllEventTypes() {
        return eventTypeRepository.findAll().stream()
                .map(eventTypeMapper::toEventTypeResponse)
                .toList();
    }

    @Override
    public EventTypeResponse findById(Integer id) {
        return eventTypeRepository.findById(id)
                .map(eventTypeMapper::toEventTypeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Event type not found with id: " + id));
    }

    @Transactional
    @Override
    public void createEventType(EventTypeRequest eventTypeRequest) {
        if (eventTypeRequest.getName() == null || eventTypeRequest.getName().isBlank()) {
            throw new MissingInfoException("Event type name cannot be empty.");
        }

        if (eventTypeRepository.findByNameIgnoreCase(eventTypeRequest.getName()).isPresent()) {
            throw new UniquenessViolationException("An event type with this name already exists.");
        }

        EventType eventType = eventTypeMapper.toEventTypeEntity(eventTypeRequest);
        eventTypeRepository.save(eventType);
    }

    @Transactional
    @Override
    public void updateEventType(Integer id, EventTypeRequest eventTypeRequest) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event type not found with id: " + id));

        if (eventTypeRequest.getName() != null && !eventTypeRequest.getName().isBlank()) {
            if (eventTypeRepository.findByNameIgnoreCase(eventTypeRequest.getName()).isEmpty()) {
                eventType.setName(eventTypeRequest.getName());
            } else {
                throw new UniquenessViolationException("An event type with this name already exists.");
            }
        }

        if (eventTypeRequest.getDescription() != null)
            eventType.setDescription(eventTypeRequest.getDescription());

        eventTypeRepository.save(eventType);
    }

    @Override
    public void deleteEventType(Integer id) {
        eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event type not found with id: " + id));

        eventTypeRepository.deleteById(id);
    }
}

