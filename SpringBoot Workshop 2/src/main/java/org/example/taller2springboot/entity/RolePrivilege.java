package org.example.taller2springboot.entity;

import jakarta.persistence.*;

//Intermediate Table between roles and privileges
@Entity
@Table(name = "roles_privileges")
public class RolePrivilege {

    @EmbeddedId
    private RolePrivilegeId rolePrivilegeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("privilegeId")
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privilege privilege;

    public RolePrivilege(RolePrivilegeId rolePrivilegeId, Role role, Privilege privilege) {
        this.rolePrivilegeId = rolePrivilegeId;
        this.role = role;
        this.privilege = privilege;
    }

    public RolePrivilege() {
        //Default constructor
    }

    public RolePrivilegeId getRolePrivilegeId() {
        return rolePrivilegeId;
    }

    public void setRolePrivilegeId(RolePrivilegeId rolePrivilegeId) {
        this.rolePrivilegeId = rolePrivilegeId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
}
