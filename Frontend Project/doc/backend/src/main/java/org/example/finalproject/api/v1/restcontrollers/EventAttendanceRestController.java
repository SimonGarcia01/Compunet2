package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.EventAttendanceRequest;
import org.example.finalproject.api.v1.dtos.MsgResp;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.EventAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event_attendances")
public class EventAttendanceRestController {

    @Autowired
    private EventAttendanceService eventAttendanceService;

    //Get attendance by IDs
    @GetMapping("/{userId}/{eventId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAttendance(@PathVariable("userId") Integer userId, @PathVariable("eventId") Integer eventId) {
        try {
            var response = eventAttendanceService.findById(userId, eventId);
            return ResponseEntity.status(200).body(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }
    }

    //Get all attendances
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllAttendances() {
        var eventAttendances = eventAttendanceService.getAllEventAttendances();
        return ResponseEntity.status(200).body(eventAttendances);
    }

    //Create attendance
    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createAttendance(@RequestBody EventAttendanceRequest request) {
        try {
            eventAttendanceService.createEventAttendance(request);
            return ResponseEntity.status(200).body(new MsgResp("The attendance of the user to the event was added."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    //Delete attendance
    @DeleteMapping("/{userId}/{eventId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteAttendance(@PathVariable("userId") Integer userId, @PathVariable("eventId") Integer eventId) {
        try {
            eventAttendanceService.deleteEventAttendance(userId, eventId);
            return ResponseEntity.status(200).body(new MsgResp("The attendance of the user to the event was removed."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }
    }

    //For this one I didn't make the update since it's just an intermediate table
    //And changing info seems a little meaningless since there is not much info within
    //This table. Just delete and create another one.
}
