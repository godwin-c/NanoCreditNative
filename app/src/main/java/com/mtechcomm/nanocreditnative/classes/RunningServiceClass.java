package com.mtechcomm.nanocreditnative.classes;

public class RunningServiceClass {

    boolean userCreated;
    boolean scoringAccepted;
    boolean loanRequested;
    boolean lenddoSDKcalled;
    boolean priorityDataSet;
    boolean checkScoreReady;
    boolean loanOffered;
    boolean loanConfirmed;

    String phoneNumber;
    String documentID;



    public RunningServiceClass(String phoneNumber, String documentID, boolean userCreated, boolean scoringAccepted, boolean loanRequested, boolean lenddoSDKcalled, boolean priorityDataSet, boolean checkScoreReady, boolean loanOffered, boolean loanConfirmed) {
        this.phoneNumber = phoneNumber;
        this.documentID = documentID;
        this.userCreated = userCreated;
        this.scoringAccepted = scoringAccepted;
        this.loanRequested = loanRequested;
        this.lenddoSDKcalled = lenddoSDKcalled;
        this.priorityDataSet = priorityDataSet;
        this.checkScoreReady = checkScoreReady;
        this.loanOffered = loanOffered;
        this.loanConfirmed = loanConfirmed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }

    public boolean isScoringAccepted() {
        return scoringAccepted;
    }

    public void setScoringAccepted(boolean scoringAccepted) {
        this.scoringAccepted = scoringAccepted;
    }

    public boolean isLoanRequested() {
        return loanRequested;
    }

    public void setLoanRequested(boolean loanRequested) {
        this.loanRequested = loanRequested;
    }

    public boolean isLenddoSDKcalled() {
        return lenddoSDKcalled;
    }

    public void setLenddoSDKcalled(boolean lenddoSDKcalled) {
        this.lenddoSDKcalled = lenddoSDKcalled;
    }

    public boolean isPriorityDataSet() {
        return priorityDataSet;
    }

    public void setPriorityDataSet(boolean priorityDataSet) {
        this.priorityDataSet = priorityDataSet;
    }

    public boolean isCheckScoreReady() {
        return checkScoreReady;
    }

    public void setCheckScoreReady(boolean checkScoreReady) {
        this.checkScoreReady = checkScoreReady;
    }

    public boolean isLoanOffered() {
        return loanOffered;
    }

    public void setLoanOffered(boolean loanOffered) {
        this.loanOffered = loanOffered;
    }

    public boolean isLoanConfirmed() {
        return loanConfirmed;
    }

    public void setLoanConfirmed(boolean loanConfirmed) {
        this.loanConfirmed = loanConfirmed;
    }
}
