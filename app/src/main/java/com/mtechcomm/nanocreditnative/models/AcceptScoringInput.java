package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class AcceptScoringInput {
    @SerializedName("document")
    private String document;

    public AcceptScoringInput(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
