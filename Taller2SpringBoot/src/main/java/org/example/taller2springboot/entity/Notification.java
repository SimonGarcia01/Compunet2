package org.example.taller2springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    // Foreign keys
    @ManyToOne(fetch  = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @JsonIgnore
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceivedNotification> receivedNotificationsList;

    //Attributes
    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 200)
    private String text;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    public Notification() {
        //Default constructor
    }

    public Notification(Integer notificationId, String title, String text, LocalDateTime creationDateTime) {
        this.notificationId = notificationId;
        this.title = title;
        this.text = text;
        this.creationDateTime = creationDateTime;
    }

    // Getters and Setters
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<ReceivedNotification> getReceivedNotificationsList() {
        return receivedNotificationsList;
    }

    public void setReceivedNotificationsList(List<ReceivedNotification> receivedNotificationsList) {
        this.receivedNotificationsList = receivedNotificationsList;
    }

}
