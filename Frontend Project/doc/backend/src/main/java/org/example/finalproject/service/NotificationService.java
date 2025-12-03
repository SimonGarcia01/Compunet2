package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.*;

import java.util.List;

public interface NotificationService {

    NotificationResponse findById(Integer id);
    List<NotificationResponse> getAllNotifications();
    void createNotification(NotificationRequest notificationRequest);
    void updateNotification(Integer id, NotificationRequest notificationRequest);
    void deleteNotification(Integer id);

}
