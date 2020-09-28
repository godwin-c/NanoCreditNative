package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class LoanOfferModel {

    @SerializedName("amount")
    private int amount;

    @SerializedName("applicationId")
    private int applicationId;

    public LoanOfferModel(int amount, int applicationId) {
        this.amount = amount;
        this.applicationId = applicationId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
