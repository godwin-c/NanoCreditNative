package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class RepayLoanResponseModel {

    @SerializedName("applicationId")
            private int applicationId;
    @SerializedName("completed")
            private Boolean completed;
    @SerializedName("creditProduct")
            private int creditProduct;
    @SerializedName("days")
            private int days;
    @SerializedName("discount")
            private String discount;
    @SerializedName("exceeded")
            private Boolean exceeded;
    @SerializedName("fees")
            private int fees;
    @SerializedName("inTime")
            private Boolean inTime;
    @SerializedName("mandateId")
            private String mandateId;
    @SerializedName("outstanding")
            private  int outstanding;
    @SerializedName("payment")
            private int payment;
    @SerializedName("paymentDate")
            private String paymentDate;
    @SerializedName("penalty")
            private String penalty;
    @SerializedName("typePayment")
            private String typePayment;

    public RepayLoanResponseModel(int applicationId, Boolean completed, int creditProduct, int days,
                                  String discount, Boolean exceeded, int fees, Boolean inTime, String mandateId,
                                  int outstanding, int payment, String paymentDate, String penalty, String typePayment) {
        this.applicationId = applicationId;
        this.completed = completed;
        this.creditProduct = creditProduct;
        this.days = days;
        this.discount = discount;
        this.exceeded = exceeded;
        this.fees = fees;
        this.inTime = inTime;
        this.mandateId = mandateId;
        this.outstanding = outstanding;
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.penalty = penalty;
        this.typePayment = typePayment;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getCreditProduct() {
        return creditProduct;
    }

    public void setCreditProduct(int creditProduct) {
        this.creditProduct = creditProduct;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Boolean getExceeded() {
        return exceeded;
    }

    public void setExceeded(Boolean exceeded) {
        this.exceeded = exceeded;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public Boolean getInTime() {
        return inTime;
    }

    public void setInTime(Boolean inTime) {
        this.inTime = inTime;
    }

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public int getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(int outstanding) {
        this.outstanding = outstanding;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }
}
