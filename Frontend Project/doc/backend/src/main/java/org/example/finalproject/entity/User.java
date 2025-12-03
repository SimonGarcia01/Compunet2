package org.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    //Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerTrainee> trainers;

    @JsonIgnore
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerTrainee> trainees;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRolesList = new java.util.HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWorkoutProgram> userWorkoutProgramsList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceivedNotification> receivedNotificationsList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventsList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventAttendance> eventAttendancesList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricalRecord> historicalRecordsList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutProgram> workoutProgramsList;

    @JsonIgnore
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendationList;


    //Attributes
    @Column(nullable = false, unique = true, length = 180)
    private String email;
    @Column(nullable = false, length = 20, unique = true)
    private String personalId;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 60)
    private String encryptedPassword;
    @Column(nullable = true, length = 100)
    private String photoUrl;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private LocalDate creationDate;

    public User() {
        //Default constructor
    }

    public User(Integer userId, String personalId, String name, String encryptedPassword, String photoUrl, boolean active, LocalDate creationDate) {
        this.userId = userId;
        this.personalId = personalId;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.photoUrl = photoUrl;
        this.active = active;
        this.creationDate = creationDate;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public List<ReceivedNotification> getReceivedNotificationsList() {
        return receivedNotificationsList;
    }

    public void setReceivedNotificationsList(List<ReceivedNotification> receivedNotificationsList) {
        this.receivedNotificationsList = receivedNotificationsList;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
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

    public List<WorkoutProgram> getWorkoutProgramsList() {
        return workoutProgramsList;
    }

    public void setWorkoutProgramsList(List<WorkoutProgram> workoutProgramsList) {
        this.workoutProgramsList = workoutProgramsList;
    }

    public List<UserWorkoutProgram> getUserWorkoutProgramsList() {
        return userWorkoutProgramsList;
    }

    public void setUserWorkoutProgramsList(List<UserWorkoutProgram> userWorkoutProgramsList) {
        this.userWorkoutProgramsList = userWorkoutProgramsList;
    }

    public List<TrainerTrainee> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainerTrainee> trainers) {
        this.trainers = trainers;
    }

    public List<TrainerTrainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<TrainerTrainee> trainees) {
        this.trainees = trainees;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserRole> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(Set<UserRole> userRolesList) {
        this.userRolesList = userRolesList;
    }
}