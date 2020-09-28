package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerModelResult {
    @SerializedName("acceptScoring")
    private boolean acceptScoring;

    @SerializedName("surname")
    private String surname;

    @SerializedName("document")
    private String document;

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("name")
    private String name;

    @SerializedName("phones")
    private ArrayList<HashMap<String, String>> phones;

    @SerializedName("account")
    private String account;

    public CustomerModelResult(boolean acceptScoring, String surname, String document, int customerId,
                               String name, ArrayList<HashMap<String, String>> phones, String account) {
        this.acceptScoring = acceptScoring;
        this.surname = surname;
        this.document = document;
        this.customerId = customerId;
        this.name = name;
        this.phones = phones;
        this.account = account;
    }

    public boolean isAcceptScoring() {
        return acceptScoring;
    }

    public void setAcceptScoring(boolean acceptScoring) {
        this.acceptScoring = acceptScoring;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HashMap<String, String>> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<HashMap<String, String>> phones) {
        this.phones = phones;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
