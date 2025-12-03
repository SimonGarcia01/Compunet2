package org.example.finalproject.api.v1.dtos;

import org.example.finalproject.entity.Role;
import org.example.finalproject.entity.User;

public class UserRoleRequestResponse {

    // Attributes
    private Integer userId;
    private Integer roleId;
    private UserRequest user;
    private RoleRequest role;

    // Constructor with zero parameters
    public UserRoleRequestResponse() {

    }

    // Constructor with all the attributes
    public UserRoleRequestResponse(Integer userId, Integer roleId, UserRequest user, RoleRequest role) {
        this.userId = userId;
        this.roleId = roleId;
        this.user = user;
        this.role = role;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public RoleRequest getRole() {
        return role;
    }

    public void setRole(RoleRequest role) {
        this.role = role;
    }
}
