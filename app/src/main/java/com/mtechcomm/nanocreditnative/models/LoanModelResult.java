package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class LoanModelResult {

    @SerializedName("applicationId")
    private int applicationId;

    @SerializedName("creditProduct")
    private int creditProduct;

    @SerializedName("customerId")
    private int customerId;

    public LoanModelResult(int applicationId, int creditProduct, int customerId) {
        this.applicationId = applicationId;
        this.creditProduct = creditProduct;
        this.customerId = customerId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
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
}
