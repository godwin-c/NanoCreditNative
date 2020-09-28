package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class CheckScoreReadyResult {

    @SerializedName("amount")
    private int amount;

    @SerializedName("creditProduct")
    private int creditProduct;

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("applicationId")
    private int applicationId;

    @SerializedName("applicationDate")
    private String applicationDate;

    public CheckScoreReadyResult(int amount, int creditProduct, int customerId, int applicationId, String applicationDate) {
        this.amount = amount;
        this.creditProduct = creditProduct;
        this.customerId = customerId;
        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreditProduct() {
        return creditProduct;
    }

    public void setCreditProduct(int creditProduct) {
        this.creditProduct = creditProduct;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
}
