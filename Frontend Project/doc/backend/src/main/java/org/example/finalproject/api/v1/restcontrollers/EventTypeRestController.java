package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.EventTypeRequest;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event_types")
public class EventTypeRestController {

    @Autowired
    private EventTypeService eventTypeService;

    // Get all event types
    @GetMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllEventTypes() {
        var eventTypes = eventTypeService.getAllEventTypes();
        return ResponseEntity.status(200).body(eventTypes);
    }

    // Get event type by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getEventTypeById(@PathVariable("id") Integer id) {
        try {
            var eventType = eventTypeService.findById(id);
            return ResponseEntity.status(200).body(eventType);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    // Create a new event type
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createEventType(@RequestBody EventTypeRequest eventTypeRequest) {
        try {
            eventTypeService.createEventType(eventTypeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Event type created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Update event type
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateEventType(@PathVariable("id") Integer id, @RequestBody EventTypeRequest eventTypeRequest) {
        try {
            eventTypeService.updateEventType(id, eventTypeRequest);
            return ResponseEntity.status(200).body(new MsgResp("Event type updated successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    // Delete event type
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteEventType(@PathVariable("id") Integer id) {
        try {
            eventTypeService.deleteEventType(id);
            return ResponseEntity.status(200).body(new MsgResp("Event type deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }
}