package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class UpdateLoanDataModel {
    @SerializedName("loanid")
    private String loanid;
    @SerializedName("lstatus")
    private boolean lstatus;

    public UpdateLoanDataModel(String loanid, boolean lstatus) {
        this.loanid = loanid;
        this.lstatus = lstatus;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public boolean getLstatus() {
        return lstatus;
    }

    public void setLstatus(boolean lstatus) {
        this.lstatus = lstatus;
    }
}
