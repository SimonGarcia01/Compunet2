package org.example.taller2springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePrivilege> rolePrivilegesList;

    @JsonIgnore
    @OneToMany(mappedBy = "role",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRolesList;

    //Attributes
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = true, length = 200)
    private String description;


    public Role() {
        //Default Constructor
    }

    public Role(Integer roleId, String name, String description) {
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

    public List<RolePrivilege> getRolePrivilegesList() {
        return rolePrivilegesList;
    }

    public void setRolePrivilegesList(List<RolePrivilege> rolePrivilagesList) {
        this.rolePrivilegesList = rolePrivilagesList;
    }

    public List<UserRole> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<UserRole> userRolesList) {
        this.userRolesList = userRolesList;
    }

}
