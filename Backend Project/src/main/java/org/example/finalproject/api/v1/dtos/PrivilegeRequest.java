package org.example.finalproject.api.v1.dtos;

public class PrivilegeRequest {

    // Attributes
    private String name;
    private String description;

    // Constructor with zero parameters
    public PrivilegeRequest() {

    }

    // Constructor with all attributes
    public PrivilegeRequest(String name, String description) {
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
