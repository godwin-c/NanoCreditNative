package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class LoanEnquiryModelResult {

    @SerializedName("amount")
    private String amount;
    @SerializedName("applicationId")
    private int applicationId;
    @SerializedName("creditProduct")
    private int creditProduct;
    @SerializedName("customerId")
    private int customerId;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("info")
    private String info;
    @SerializedName("outstanding")
    private String outstanding;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("status")
    private String status;
    @SerializedName("warning")
    private boolean warning;


    public LoanEnquiryModelResult(String amount, int applicationId, int creditProduct, int customerId, String endDate, String info,
                                  String outstanding, String startDate, String status, boolean warning) {
        this.amount = amount;
        this.applicationId = applicationId;
        this.creditProduct = creditProduct;
        this.customerId = customerId;
        this.endDate = endDate;
        this.info = info;
        this.outstanding = outstanding;
        this.startDate = startDate;
        this.status = status;
        this.warning = warning;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }
}