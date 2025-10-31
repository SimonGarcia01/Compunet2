// TABLA INTERMEDIA.

package org.example.taller3mvc.entity;

import jakarta.persistence.*;

//Intermediate table between notifications and users
@Entity
@Table(name = "received_notifications")
public class ReceivedNotification {
    @EmbeddedId
    private ReceivedNotificationId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("notificationId")
    @JoinColumn(name="notification_id", nullable=false)
    private Notification notification;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name= "user_id", nullable=false)
    private User user;

    public ReceivedNotification(ReceivedNotificationId id, Notification notification, User user) {
        this.id = id;
        this.notification = notification;
        this.user = user;
    }

    public ReceivedNotification() {
        //Default constructor
    }

    public ReceivedNotificationId getId() {
        return id;
    }

    public void setId(ReceivedNotificationId id) {
        this.id = id;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
