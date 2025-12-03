package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.NotificationRequest;
import org.example.finalproject.api.v1.dtos.NotificationResponse;
import org.example.finalproject.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    public Notification toNotification(NotificationRequest notificationRequest);
    public NotificationResponse toNotificationResponse(Notification notification);

}
