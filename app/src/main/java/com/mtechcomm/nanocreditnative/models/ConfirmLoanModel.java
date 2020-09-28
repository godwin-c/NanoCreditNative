package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class ConfirmLoanModel {
    @SerializedName("accepted")
    private boolean accepted;

    @SerializedName("applicationId")
    private int applicationId;


    public ConfirmLoanModel(boolean accepted, int applicationId) {
        this.accepted = accepted;
        this.applicationId = applicationId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
