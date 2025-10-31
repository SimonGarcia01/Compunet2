package org.example.finalproject.api.v1.dtos;

public class ReceivedNotificationRequestResponse {

    // Attributes
    private Integer userId;
    private Integer notificationId;
    private NotificationRequest notification;
    private UserRequest user;

    // Constructor with zero parameters
    public ReceivedNotificationRequestResponse() {

    }

    // Constructor with all attributes
    public ReceivedNotificationRequestResponse(Integer userId, Integer notificationId, NotificationRequest notification, UserRequest user) {
        this.userId = userId;
        this.notificationId = notificationId;
        this.notification = notification;
        this.user = user;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationRequest getNotification() {
        return notification;
    }

    public void setNotification(NotificationRequest notification) {
        this.notification = notification;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }
}
