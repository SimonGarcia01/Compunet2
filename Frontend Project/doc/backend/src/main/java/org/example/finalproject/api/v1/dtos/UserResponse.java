package org.example.finalproject.api.v1.dtos;

import java.time.LocalDate;


public class UserResponse {

    private Integer userId;
    private String email;
    private String personalId;
    private String name;
    private String photoUrl;
    private boolean active;
    private LocalDate creationDate;

    public UserResponse() {
        //Default constructor
    }

    public UserResponse(Integer userId, String email, String personalId, String name, String photoUrl, boolean active, LocalDate creationDate) {
        this.userId = userId;
        this.email = email;
        this.personalId = personalId;
        this.name = name;
        this.photoUrl = photoUrl;
        this.active = active;
        this.creationDate = creationDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
