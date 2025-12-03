package org.example.finalproject.api.v1.dtos;

import java.time.LocalDate;

public class MessageRequest {

    // Attributes
    private String text;

    // Constructor with zero parameters
    public MessageRequest() {

    }

    // Constructor with all attributes
    public MessageRequest(String text) {
        this.text = text;
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
