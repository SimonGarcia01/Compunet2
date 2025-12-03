package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.ReceivedNotificationRequestResponse;

import java.util.List;

public interface ReceivedNotificationService {

    ReceivedNotificationRequestResponse findById(Integer userId, Integer notificationId);
    List<ReceivedNotificationRequestResponse> getAllReceivedNotifications();
    void createReceivedNotification(ReceivedNotificationRequestResponse receivedNotificationRequest);
    void updateReceivedNotification(Integer userId, Integer notificationId, ReceivedNotificationRequestResponse receivedNotificationRequest);
    void deleteReceivedNotification(Integer userId, Integer notificationId);

}
