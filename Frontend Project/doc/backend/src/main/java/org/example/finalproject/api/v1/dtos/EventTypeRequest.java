package org.example.finalproject.api.v1.dtos;

public class EventTypeRequest {

    // Attributes
    private String name;
    private String description;

    // Constructor with zero parameters
    public EventTypeRequest() {

    }

    // Constructor with all the attributes
    public EventTypeRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
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

}
