
package org.example.taller2springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainer_trainee")
public class TrainerTrainee {

    // Primary key
    @EmbeddedId
    private TrainerTraineeId id;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("traineeId")
    @JoinColumn(name = "trainee_id", nullable = false)
    private User trainee;

    @JsonIgnore
    @OneToMany(mappedBy = "trainerTrainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @JsonIgnore
    @OneToMany(mappedBy = "trainerTrainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    //Attributes
    @Column(nullable = true)
    private LocalDate startDate;
    @Column(nullable = true)
    private LocalDate endDate;

    //Constructors
    public TrainerTrainee(TrainerTraineeId id, User trainer, User trainee, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.trainer = trainer;
        this.trainee = trainee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TrainerTrainee() {
        //Default constructor
    }

    // Getters and Setters
    public TrainerTraineeId getId() {
        return id;
    }

    public void setId(TrainerTraineeId id) {
        this.id = id;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public User getTrainee() {
        return trainee;
    }

    public void setTrainee(User trainee) {
        this.trainee = trainee;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
