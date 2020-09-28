package com.mtechcomm.nanocreditnative.classes;

import java.util.ArrayList;

public class AppUser {
    private String surname;
    private String othernames;
    private ArrayList<String> phones;
    private String emailAddress;
    private String date_of_birth;
    private String password;
    private boolean phoneVerified;

    private String accountNumber;
    private String documentID;

    private int customerId;
    private int loanApplicationID;

    private int loanAmount;
    private String loanStartDate;
    private String loanEndDate;
    private String loanStatus;

    private boolean scoreComputed;

    private String last_login_datetime;


    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress,
                   String date_of_birth, String password,String accountNumber, boolean phoneVerified, int customerId,
                   String documentID, int loanApplicationID, String loanStatus, String last_login_datetime) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.phoneVerified = phoneVerified;
        this.customerId = customerId;
        this.documentID = documentID;
        this.loanApplicationID = loanApplicationID;
        this.loanStatus = loanStatus;
        this.last_login_datetime = last_login_datetime;
    }

    public String getLast_login_datetime() {
        return last_login_datetime;
    }

    public void setLast_login_datetime(String last_login_datetime) {
        this.last_login_datetime = last_login_datetime;
    }
    // "cid": null,
//             "docid": null,
//             "loanid": "5050",
//             "lstatus": "t",
//             "last_login_datetime": "2020-09-04 18:48:45.642855"

    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, String accountNumber, String documentID, int customerId, int loanApplicationID, int loanAmount, String loanStartDate, String loanEndDate, String loanStatus) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.documentID = documentID;
        this.customerId = customerId;
        this.loanApplicationID = loanApplicationID;
        this.loanAmount = loanAmount;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.loanStatus = loanStatus;
    }


    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, String accountNumber, String documentID, int customerId, int loanApplicationID, boolean scoreComputed) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.documentID = documentID;
        this.customerId = customerId;
        this.loanApplicationID = loanApplicationID;
        this.scoreComputed = scoreComputed;
    }

    public boolean isScoreComputed() {
        return scoreComputed;
    }

    public void setScoreComputed(boolean scoreComputed) {
        this.scoreComputed = scoreComputed;
    }

    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, String accountNumber, String documentID, int customerId, int loanApplicationID) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.documentID = documentID;
        this.customerId = customerId;
        this.loanApplicationID = loanApplicationID;
    }

    public int getLoanApplicationID() {
        return loanApplicationID;
    }

    public void setLoanApplicationID(int loanApplicationID) {
        this.loanApplicationID = loanApplicationID;
    }

    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, String accountNumber, String documentID, int customerId) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.documentID = documentID;
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public AppUser(String surname, String othernames, ArrayList<String> phones, String emailAddress, String date_of_birth, String password, String accountNumber, String documentID) {
        this.surname = surname;
        this.othernames = othernames;
        this.phones = phones;
        this.emailAddress = emailAddress;
        this.date_of_birth = date_of_birth;
        this.password = password;
        this.accountNumber = accountNumber;
        this.documentID = documentID;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
