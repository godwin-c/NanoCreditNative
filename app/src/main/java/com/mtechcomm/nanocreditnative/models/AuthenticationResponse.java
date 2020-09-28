package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class AuthenticationResponse {
    @SerializedName("token")
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
