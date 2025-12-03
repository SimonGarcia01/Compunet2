package org.example.finalproject.api.v1.dtos;

public class RoleRequest {

    // Attributes
    private String name;
    private String description;

    // Constructor with zero parameters
    public RoleRequest() {

    }

    // Constructor with all the parameters
    public RoleRequest(String name, String description) {
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