package org.example.finalproject.api.v1.dtos;

public class AuthResponse {
    private String accessToken;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponse() {
        //Default constructor
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
