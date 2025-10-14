package org.example.introspringboot.api.v1.dto;

//Object only to send as response
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse() {
        //Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
