package org.example.finalproject.api.v1.restcontrollers;

import org.example.finalproject.api.v1.dtos.*;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    // Beans
    @Autowired
    private NotificationService notificationService;

    // Get notification by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getNotificationById(@PathVariable("id") Integer id) {

        try {
            NotificationResponse notification = notificationService.findById(id);
            return ResponseEntity.status(200).body(notification);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

    // Get all notifications
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> getAllNotifications() {

        var notifications = notificationService.getAllNotifications();
        return ResponseEntity.status(200).body(notifications);

    }

    // Create a new notification
    @PostMapping("")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> createNotification(@RequestBody NotificationRequest notificationRequest) {

        try {
            notificationService.createNotification(notificationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Notification created successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Update a notification
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> updateNotification(@PathVariable("id") Integer id, @RequestBody NotificationRequest notificationRequest) {

        try {
            notificationService.updateNotification(id, notificationRequest);
            return ResponseEntity.status(200).body(new MsgResp("Notification updated successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new MsgResp(e.getMessage()));
        }

    }

    // Delete a notification
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Integer id) {

        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.status(200).body(new MsgResp("Notification deleted successfully."));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new MsgResp(e.getMessage()));
        }

    }

}