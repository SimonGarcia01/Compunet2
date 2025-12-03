package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.NotificationRequest;
import org.example.finalproject.api.v1.dtos.NotificationResponse;
import org.example.finalproject.api.v1.mappers.NotificationMapper;
import org.example.finalproject.entity.*;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.EventRepository;
import org.example.finalproject.repository.NotificationRepository;
import org.example.finalproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    // Beans
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private EventRepository eventRepository;

    // Find notification by ID
    @Override
    public NotificationResponse findById(Integer id) {

        return notificationRepository.findById(id)
                .map(notificationMapper::toNotificationResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

    }

    // Get all notifications
    @Override
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll().stream()
                .map(notificationMapper::toNotificationResponse)
                .toList();

    }

    // Create a new notification
    @Override
    @Transactional
    public void createNotification(NotificationRequest request) {

        if (request.getEventId() == null ||
                request.getTitle() == null ||
                request.getText() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        if (notificationRepository.existsByTitle(request.getTitle())) {
            throw new UniquenessViolationException("Notification with this title already exists");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        Notification newNotification = notificationMapper.toNotification(request);
        //newNotification.setEvent(eventRepository.findById(request.getEvent())
          //      .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId())));

        notificationRepository.save(newNotification);

    }

    // Update an existing notification
    @Override
    @Transactional
    public void updateNotification(Integer id, NotificationRequest request) {

        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));

        if (request.getTitle() != null && !request.getTitle().equals(existingNotification.getTitle())) {
            if(notificationRepository.findByTitle(request.getTitle()).isPresent()) {
                throw new UniquenessViolationException("Another notification with this title already exists");
            }
            existingNotification.setTitle(request.getTitle());
        }

        if (request.getTitle() != null)
            existingNotification.setTitle(request.getTitle());

        if (request.getText() != null)
            existingNotification.setText(request.getText());

        notificationRepository.save(existingNotification);

    }

    // Delete a notification
    @Override
    public void deleteNotification(Integer id) {

        notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        notificationRepository.deleteById(id);

    }

}
