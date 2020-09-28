package com.mtechcomm.nanocreditnative.classes;

public class CreateUserDetails {
    private String accountNumber;
    private String scannedID;

    public CreateUserDetails(String accountNumber, String scannedID) {
        this.accountNumber = accountNumber;
        this.scannedID = scannedID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getScannedID() {
        return scannedID;
    }

    public void setScannedID(String scannedID) {
        this.scannedID = scannedID;
    }
}
