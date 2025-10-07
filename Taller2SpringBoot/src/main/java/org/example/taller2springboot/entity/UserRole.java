package org.example.taller2springboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

//Intermediate table between users and roles
@Entity
@Table(name = "user_roles")
public class UserRole {

    //Id
    @EmbeddedId
    private UserRoleId id;

    //Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // Attributes
    @Column(nullable = false)
    private LocalDate assignedDate;

    public UserRole(UserRoleId id, User user, Role role, LocalDate assignedDate) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.assignedDate = assignedDate;
    }

    public UserRole() {
        //Default Constructor
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
