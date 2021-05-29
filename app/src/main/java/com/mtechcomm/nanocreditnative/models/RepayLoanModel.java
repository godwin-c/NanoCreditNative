package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class RepayLoanModel {

    @SerializedName("amount")
            private int amount;
    @SerializedName("applicationId")
            private int applicationId;
    @SerializedName("document")
            private int document;

    public RepayLoanModel(int amount, int applicationId, int document) {
        this.amount = amount;
        this.applicationId = applicationId;
        this.document = document;
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

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }
}
