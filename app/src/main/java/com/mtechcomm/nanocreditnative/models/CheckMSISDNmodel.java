package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class CheckMSISDNmodel {
    @SerializedName("phonenumber")
    private String phonenumber;

    public CheckMSISDNmodel(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
