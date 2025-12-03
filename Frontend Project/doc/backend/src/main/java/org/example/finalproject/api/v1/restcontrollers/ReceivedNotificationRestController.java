package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.entity.ReceivedNotificationId;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.ReceivedNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/received_notifications")
public class ReceivedNotificationRestController {

    // Beans
    @Autowired
    private ReceivedNotificationService receivedNotificationService;

    // Get received notification by ID
    @GetMapping("/{userId}/{notificationId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getReceivedNotificationById(@PathVariable("userId") Integer userId, @PathVariable("notificationId") Integer notificationId) {

        try {
            ReceivedNotificationRequestResponse receivedNotification = receivedNotificationService.findById(userId, notificationId);
            return ResponseEntity.status(200).body(receivedNotification);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all received notifications
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllReceivedNotifications() {

        var receivedNotifications = receivedNotificationService.getAllReceivedNotifications();
        return ResponseEntity.status(200).body(receivedNotifications);

    }

    // Create a new received notification
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createReceivedNotification(@RequestBody ReceivedNotificationRequestResponse receivedNotificationRequest) {

        try {
            receivedNotificationService.createReceivedNotification(receivedNotificationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Received Notification created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a received notification
    @PutMapping("/{userId}/{notificationId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateReceivedNotification(@PathVariable("userId") Integer userId, @PathVariable("notificationId") Integer notificationId, @RequestBody ReceivedNotificationRequestResponse receivedNotificationRequest) {

        try {
            receivedNotificationService.updateReceivedNotification(userId, notificationId, receivedNotificationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Received Notification updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a notification
    @DeleteMapping("/{userId}/{notificationId}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteReceivedNotification(@PathVariable("userId") Integer userId, @PathVariable("notificationId") Integer notificationId) {

        try {
            receivedNotificationService.deleteReceivedNotification(userId, notificationId);
            return ResponseEntity.status(200).body(new MsgResp("Received Notification deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}