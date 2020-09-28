package com.mtechcomm.nanocreditnative.models;

public class PaymentModel {

    private String document;
    private float amount;
    private String loanId;

    public PaymentModel(String document, float amount, String loanId) {
        this.document = document;
        this.amount = amount;
        this.loanId = loanId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
