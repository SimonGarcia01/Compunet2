package org.example.finalproject.api.v1.dtos;

import java.time.LocalDate;

public class MessageResponse {

    // Attributes
    private Integer messageId;
    private TrainerTraineeResponse trainerTraineeId;
    private LocalDate sentDate;
    private String text;

    // Constructor with zero parameters
    public MessageResponse() {

    }

    // Constructor with all attributes
    public MessageResponse(Integer messageId, TrainerTraineeResponse trainerTraineeId, LocalDate sentDate, String text) {

        this.messageId = messageId;
        this.trainerTraineeId = trainerTraineeId;
        this.sentDate = sentDate;
        this.text = text;
    }

    // Getters and Setters
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public TrainerTraineeResponse getTrainerTraineeId() {
        return trainerTraineeId;
    }

    public void setTrainerTraineeId(TrainerTraineeResponse trainerTraineeId) {
        this.trainerTraineeId = trainerTraineeId;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
