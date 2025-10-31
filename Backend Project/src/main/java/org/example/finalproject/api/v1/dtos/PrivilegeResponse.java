package org.example.finalproject.api.v1.dtos;

public class PrivilegeResponse {

    // Attributes
    private Integer privilegeId;
    private String name;
    private String description;

    // Constructor with zero parameters
    public PrivilegeResponse() {

    }

    // Constructor with all attributes
    public PrivilegeResponse(Integer privilegeId, String name, String description) {
        this.privilegeId = privilegeId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
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
