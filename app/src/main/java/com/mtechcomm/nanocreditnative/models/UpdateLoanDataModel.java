package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateLoanDataModel implements Serializable {

    @SerializedName("loanID")
    private String loanID;
    @SerializedName("status")
    private String status;
    @SerializedName("CustomerID")
    private String CustomerID;
    @SerializedName("loanAmount")
    private String loanAmount;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;

    public UpdateLoanDataModel(String loanID, String status, String customerID, String loanAmount, String startDate, String endDate) {
        this.loanID = loanID;
        this.status = status;
        CustomerID = customerID;
        this.loanAmount = loanAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
