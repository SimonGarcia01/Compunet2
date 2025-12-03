package org.example.finalproject.api.v1.dtos;

public class EventTypeResponse {

    // Attributes
    private Integer eventTypeId;
    private String name;
    private String description;

    // Constructor with zero parameters
    public EventTypeResponse() {

    }

    // Constructor with all the attributes
    public EventTypeResponse(Integer eventTypeId, String name, String description) {
        this.eventTypeId = eventTypeId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
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

}
