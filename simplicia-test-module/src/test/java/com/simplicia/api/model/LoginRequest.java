package com.simplicia.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String username;
    private String password;
    @JsonProperty("grant_type")
    private String grantType;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrantType() {
        return grantType;
    }

    public LoginRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginRequest setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }
}
