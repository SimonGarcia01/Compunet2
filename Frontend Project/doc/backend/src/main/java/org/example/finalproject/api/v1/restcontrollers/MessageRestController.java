package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.ExerciseService;
import org.example.finalproject.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/messages")
public class MessageRestController {

    // Beans
    @Autowired
    private MessageService messageService;

    // Get message by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getMessageById(@PathVariable("id") Integer id) {

        try {
            MessageResponse message = messageService.findById(id);
            return ResponseEntity.status(200).body(message);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all message
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllMessages() {

        var messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);

    }

    // Create a new message
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createMessage(@RequestBody MessageRequest messageRequest) {

        try {
            messageService.createMessage(messageRequest);
            return ResponseEntity.status(200).body(new MsgResp("Message created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a message
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateMessage(@PathVariable("id") Integer id, @RequestBody MessageRequest messageRequest) {

        try {
            messageService.updateMessage(id, messageRequest);
            return ResponseEntity.status(200).body(new MsgResp("Message updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a message
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Integer id) {

        try {
            messageService.deleteMessage(id);
            return ResponseEntity.status(200).body(new MsgResp("Message deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}