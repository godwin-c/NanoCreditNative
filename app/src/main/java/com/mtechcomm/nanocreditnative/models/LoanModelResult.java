package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class LoanModelResult {

    @SerializedName("applicationId")
    private int applicationId;
    @SerializedName("customerId")
    private int customerId;

    public LoanModelResult(int applicationId, int customerId) {
        this.applicationId = applicationId;
        this.customerId = customerId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
