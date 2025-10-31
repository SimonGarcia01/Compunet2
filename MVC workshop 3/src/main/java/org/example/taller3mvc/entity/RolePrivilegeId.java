package org.example.taller3mvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolePrivilegeId implements Serializable {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "privilege_id")
    private Integer privilegeId;

    @Override
    public int hashCode() {
        return Objects.hash(roleId, privilegeId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RolePrivilegeId that){
            return Objects.equals(this.roleId, that.roleId) && Objects.equals(this.privilegeId, that.privilegeId);
        }

        return false;
    }

    public RolePrivilegeId(Integer roleId, Integer privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public RolePrivilegeId() {
        //Default constructor
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
}
