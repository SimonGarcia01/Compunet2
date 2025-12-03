package org.example.finalproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRoleId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    public UserRoleId(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof UserRoleId that){
            return Objects.equals(this.userId, that.userId) && Objects.equals(this.roleId, that.roleId);
        }

        return false;
    }

    public UserRoleId() {
        //Default constructor
    }

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
}
