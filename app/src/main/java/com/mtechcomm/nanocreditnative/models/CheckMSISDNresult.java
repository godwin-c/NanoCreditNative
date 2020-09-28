package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class CheckMSISDNresult {
    @SerializedName("success")
    private String success;

    public CheckMSISDNresult(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
