package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.ReceivedNotificationRequestResponse;
import org.example.finalproject.api.v1.mappers.NotificationMapper;
import org.example.finalproject.api.v1.mappers.ReceivedNotificationMapper;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.entity.ReceivedNotification;
import org.example.finalproject.entity.ReceivedNotificationId;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.ReceivedNotificationRepository;
import org.example.finalproject.service.ReceivedNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivedNotificationServiceImpl implements ReceivedNotificationService {

    // Beans
    @Autowired
    private ReceivedNotificationRepository receivedNotificationRepository;
    @Autowired
    private ReceivedNotificationMapper receivedNotificationMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    // Find received notification by ID
    @Override
    public ReceivedNotificationRequestResponse findById(Integer userId, Integer notificationId) {

        //Make the id so then it can be used to look for it
        ReceivedNotificationId id = new ReceivedNotificationId();
        id.setUserId(userId);
        id.setNotificationId(notificationId);

        return receivedNotificationRepository.findById(id)
                .map(receivedNotificationMapper::toReceivedNotificationResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Received Notification not found with id: " + id));
    }

    // Get all received notifications
    @Override
    public List<ReceivedNotificationRequestResponse> getAllReceivedNotifications() {

        return receivedNotificationRepository.findAll().stream()
                .map(receivedNotificationMapper::toReceivedNotificationResponse)
                .toList();

    }

    // Create a new received notification
    @Override
    @Transactional
    public void createReceivedNotification(ReceivedNotificationRequestResponse request) {

        if (request .getNotification() == null ||
                request.getUser() == null){
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        ReceivedNotification newReceivedNotification = receivedNotificationMapper.toReceivedNotification(request);

        receivedNotificationRepository.save(newReceivedNotification);

    }

    @Override
    @Transactional
    public void updateReceivedNotification(Integer userId, Integer notificationId, ReceivedNotificationRequestResponse request) {

        //Make the id so then it can be used to look for it
        ReceivedNotificationId id = new ReceivedNotificationId();
        id.setUserId(userId);
        id.setNotificationId(notificationId);

        ReceivedNotification existingReceivedNotification = receivedNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Received Notification not found with ID: " + id));

        if (request.getNotification() != null)
            existingReceivedNotification.setNotification(notificationMapper.toNotification(request.getNotification()));

        if (request.getUser() != null)
            existingReceivedNotification.setUser(userMapper.toUser(request.getUser()));

        receivedNotificationRepository.save(existingReceivedNotification);

    }

    // Delete a received notification
    @Override
    public void deleteReceivedNotification(Integer userId, Integer notificationId) {

        //Make the id so then it can be used to look for it
        ReceivedNotificationId id = new ReceivedNotificationId();
        id.setUserId(userId);
        id.setNotificationId(notificationId);

        receivedNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Received Notification not found with id: " + id));

        receivedNotificationRepository.deleteById(id);

    }
}
