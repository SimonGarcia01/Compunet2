package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.Role;

public class RoleResponse {

    // Attributes
    private Integer roleId;
    private String name;
    private String description;

    // Constructor with zero parameters
    public RoleResponse() {

    }

    // Constructor with all the parameters
    public RoleResponse(Integer roleId, String name, String description) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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