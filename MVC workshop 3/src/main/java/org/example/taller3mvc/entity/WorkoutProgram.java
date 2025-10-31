package org.example.taller3mvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "workoutprograms")
public class WorkoutProgram {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workoutId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "workoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWorkoutProgram> userWorkoutProgramsList;

    @JsonIgnore
    @OneToMany(mappedBy = "workoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExercise> workoutExercisesList;

    @JsonIgnore
    @OneToMany(mappedBy = "workoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricalRecord> historicalRecordsList;

    //Attributes
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 50)
    private String description;
    @Column(nullable = false, length = 100)
    private String photoUrl;
    @Column(nullable = false)
    private LocalDate creationDate;
    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private User user;

    public WorkoutProgram(Integer workoutId, String name, String description, String photoUrl, LocalDate creationDate, boolean completed, User creator) {
        this.workoutId = workoutId;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.creationDate = creationDate;
        this.completed = completed;
        this.user = creator;
    }
    public WorkoutProgram(){
        //Constructor with zero parameters
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<UserWorkoutProgram> getUserWorkoutProgramsList() {
        return userWorkoutProgramsList;
    }

    public void setUserWorkoutProgramsList(List<UserWorkoutProgram> userWorkoutProgramsList) {
        this.userWorkoutProgramsList = userWorkoutProgramsList;
    }

    public List<WorkoutExercise> getWorkoutExercisesList() {
        return workoutExercisesList;
    }

    public void setWorkoutExercisesList(List<WorkoutExercise> workoutExercisesList) {
        this.workoutExercisesList = workoutExercisesList;
    }

    public List<HistoricalRecord> getHistoricalRecordsList() {
        return historicalRecordsList;
    }

    public void setHistoricalRecordsList(List<HistoricalRecord> historicalRecordsList) {
        this.historicalRecordsList = historicalRecordsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}