package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class PriorityDataModelInput {

    @SerializedName("applicationId")
    private int applicationId;

    public PriorityDataModelInput(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
