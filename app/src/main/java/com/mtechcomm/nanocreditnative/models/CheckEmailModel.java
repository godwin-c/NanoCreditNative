package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class CheckEmailModel {
    @SerializedName("email")
    private String email;

    public CheckEmailModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
