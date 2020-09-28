package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerEnquiryResult {
    @SerializedName("acceptScoring")
    private boolean acceptScoring;

    @SerializedName("acceptScoringDate")
    private String acceptScoringDate;

    @SerializedName("activeLoans")
    private ArrayList<HashMap> activeLoans;

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("phones")
    private ArrayList<HashMap<String, String>> phones;


    public CustomerEnquiryResult(boolean acceptScoring, String acceptScoringDate, ArrayList<HashMap> activeLoans, int customerId, ArrayList<HashMap<String, String>> phones) {
        this.acceptScoring = acceptScoring;
        this.acceptScoringDate = acceptScoringDate;
        this.activeLoans = activeLoans;
        this.customerId = customerId;
        this.phones = phones;
    }

    public boolean isAcceptScoring() {
        return acceptScoring;
    }

    public void setAcceptScoring(boolean acceptScoring) {
        this.acceptScoring = acceptScoring;
    }

    public String getAcceptScoringDate() {
        return acceptScoringDate;
    }

    public void setAcceptScoringDate(String acceptScoringDate) {
        this.acceptScoringDate = acceptScoringDate;
    }

    public ArrayList<HashMap> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(ArrayList<HashMap> activeLoans) {
        this.activeLoans = activeLoans;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public ArrayList<HashMap<String, String>> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<HashMap<String, String>> phones) {
        this.phones = phones;
    }
}
