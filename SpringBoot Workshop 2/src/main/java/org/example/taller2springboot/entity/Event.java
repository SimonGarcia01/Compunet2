package org.example.taller2springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id", nullable = false)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availablespace_id", nullable = false)
    private AvailableSpace availableSpace;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notificationsList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventAttendance> eventAttendancesList;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricalRecord> historicalRecordsList;

    //Attributes
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private LocalDateTime dateTimeStart;
    @Column(nullable = false)
    private LocalDateTime dateTimeEnd;
    @Column(nullable = true, length = 200)
    private String description;
    @Column(nullable = false)
    private LocalDate creationDate;
    @Column(nullable = false)
    private int maxAttendees;
    @Column(nullable = false, length = 10)
    private String status;
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal estimatedBurntCalories;

    public Event() {
        //Default constructor
    }

    public Event(Integer eventId, String name, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String description, LocalDate creationDate, int maxAttendees, String status, BigDecimal estimatedBurntCalories) {
        this.eventId=eventId;
        this.name = name;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.description = description;
        this.creationDate = creationDate;
        this.maxAttendees = maxAttendees;
        this.status = status;
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getEstimatedBurntCalories() {
        return estimatedBurntCalories;
    }

    public void setEstimatedBurntCalories(BigDecimal estimatedBurntCalories) {
        this.estimatedBurntCalories = estimatedBurntCalories;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public AvailableSpace getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(AvailableSpace availableSpace) {
        this.availableSpace = availableSpace;
    }

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EventAttendance> getEventAttendancesList() {
        return eventAttendancesList;
    }

    public void setEventAttendancesList(List<EventAttendance> eventAttendancesList) {
        this.eventAttendancesList = eventAttendancesList;
    }

    public List<HistoricalRecord> getHistoricalRecordsList() {
        return historicalRecordsList;
    }

    public void setHistoricalRecordsList(List<HistoricalRecord> historicalRecordsList) {
        this.historicalRecordsList = historicalRecordsList;
    }
}