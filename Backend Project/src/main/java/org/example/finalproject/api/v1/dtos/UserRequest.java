package org.example.finalproject.api.v1.dtos;

public class UserRequest {

    private String email;
    private String personalId;
    private String name;
    private String encryptedPassword;
    private String photoUrl;
    private boolean active;

    public UserRequest() {
        //Default constructor
    }

    public UserRequest(String email, String personalId, String name, String encryptedPassword, String photoUrl, boolean active) {
        this.email = email;
        this.personalId = personalId;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.photoUrl = photoUrl;
        this.active = active;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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
}
