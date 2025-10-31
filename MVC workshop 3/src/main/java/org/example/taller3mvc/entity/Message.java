package org.example.taller3mvc.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class Message {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trainer_trainee_id", nullable = false)
    private TrainerTrainee trainerTraineeId;

    // Attributes
    @Column(nullable = false)
    private LocalDate sentDate;
    @Column(nullable = true)
    private LocalDate readDate;
    @Column(nullable = false, length = 1000)
    private String thread;
    @Column(nullable = true, length = 3000)
    private String text;

    public Message(Integer messageId, LocalDate sentDate, LocalDate readDate, String thread, String text) {
        this.messageId = messageId;
        this.sentDate = sentDate;
        this.readDate = readDate;
        this.thread = thread;
        this.text = text;
    }

    public Message() {
        //Default Constructor
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public TrainerTrainee getTrainerTrainee() {
        return trainerTraineeId;
    }

    public void setTrainerTrainee(TrainerTrainee trainerTrainee) {
        this.trainerTraineeId = trainerTrainee;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDate getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDate readDate) {
        this.readDate = readDate;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
