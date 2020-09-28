package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class UpdateUserDataResult {
    @SerializedName("success")
    private boolean success;

    public UpdateUserDataResult(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
