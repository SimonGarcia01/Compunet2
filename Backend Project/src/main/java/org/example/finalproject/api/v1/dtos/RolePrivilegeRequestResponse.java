package org.example.finalproject.api.v1.dtos;

public class RolePrivilegeRequestResponse {

    // Attributes
    private Integer roleId;
    private Integer privilegeId;
    private RoleRequest role;
    private PrivilegeRequest privilege;

    // Constructor with zero parameters
    public RolePrivilegeRequestResponse() {

    }

    // Constructor with all the attributes
    public RolePrivilegeRequestResponse(Integer roleId, Integer privilegeId, RoleRequest role, PrivilegeRequest privilege) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
        this.role = role;
        this.privilege = privilege;
    }

    // Getters and Setters
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

    public RoleRequest getRole() {
        return role;
    }

    public void setRole(RoleRequest role) {
        this.role = role;
    }

    public PrivilegeRequest getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeRequest privilege) {
        this.privilege = privilege;
    }
}
