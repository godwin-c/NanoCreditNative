package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {
    @SerializedName("success")
    private String success;

    public RegisterResponseModel(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
