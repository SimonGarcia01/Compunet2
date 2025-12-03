package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.ReceivedNotificationRequestResponse;
import org.example.finalproject.entity.ReceivedNotification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceivedNotificationMapper {

    public ReceivedNotification toReceivedNotification(ReceivedNotificationRequestResponse receivedNotificationRequest);
    public ReceivedNotificationRequestResponse  toReceivedNotificationResponse(ReceivedNotification receivedNotification);

}
