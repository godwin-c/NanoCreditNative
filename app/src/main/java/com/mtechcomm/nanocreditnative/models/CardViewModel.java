package com.mtechcomm.nanocreditnative.models;

public class CardViewModel {
    private String state;
    private String balance;

    public CardViewModel(String state, String balance) {
        this.state = state;
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
