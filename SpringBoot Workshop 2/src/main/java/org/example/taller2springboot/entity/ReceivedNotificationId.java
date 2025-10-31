package org.example.taller2springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReceivedNotificationId implements Serializable {

    @Column(name="user_id")
    private Integer userId;

    @Column(name="notification_id")
    private Integer notificationId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, notificationId);
    }

    public ReceivedNotificationId() {
        //default constructor
    }

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

    public ReceivedNotificationId(Integer userId, Integer notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReceivedNotificationId that){
            return Objects.equals(this.userId, that.userId) && Objects.equals(this.notificationId, that.notificationId);
        }

        return false;
    }
}
