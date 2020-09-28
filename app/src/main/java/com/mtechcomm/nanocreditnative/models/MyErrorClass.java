package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class MyErrorClass {
    @SerializedName("error")
    private String error;


    public MyErrorClass(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
