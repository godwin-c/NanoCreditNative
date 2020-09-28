package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class EnquiryModel {

    @SerializedName("document")
    private String document;

    public EnquiryModel(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}
