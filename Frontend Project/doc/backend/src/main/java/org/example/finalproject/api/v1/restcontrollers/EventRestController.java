package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.EventRequest;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    //Get event by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getEventById(@PathVariable("id") Integer id) {
        try {
            var event = eventService.findById(id);
            return ResponseEntity.status(200).body(event);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    //Get all events
    @GetMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllEvents() {
        var events = eventService.getAllEvents();
        return ResponseEntity.status(200).body(events);
    }

    //Create an event
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
        try {
            eventService.createEvent(eventRequest);
            return ResponseEntity.status(200).body(new MsgResp("Event created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    //Update an event
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Integer id, @RequestBody EventRequest request) {
        try {
            eventService.updateEvent(id, request);
            return ResponseEntity.status(200).body(new MsgResp("Event updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    //Delete an event
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Integer id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.status(200).body(new MsgResp("Event deleted successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }
}
