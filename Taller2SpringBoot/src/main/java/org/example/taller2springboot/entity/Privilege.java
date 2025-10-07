package org.example.taller2springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "privileges")
public class Privilege {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer privilegeId;

    // Foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePrivilege> rolesPrivilegesList;

    //Attributes
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = true, length = 200)
    private String description;

    public Privilege() {
        //Default constructor
    }

    public Privilege(Integer privilegeId, String name, String description) {
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

    public List<RolePrivilege> getRolesPrivilegesList() {
        return rolesPrivilegesList;
    }

    public void setRolesPrivilegesList(List<RolePrivilege> rolePrivilagesList) {
        this.rolesPrivilegesList = rolePrivilagesList;
    }

}
