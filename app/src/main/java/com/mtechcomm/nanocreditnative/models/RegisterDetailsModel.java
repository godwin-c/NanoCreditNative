package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class RegisterDetailsModel {
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("phonenumber")
    private String phonenumber;
    @SerializedName("email")
    private String email;
    @SerializedName("dob")
    private String dob;
    @SerializedName("password")
    private String password;
    @SerializedName("numberverified")
    private String numberverified;
    @SerializedName("accountname")
    private String accountname;
    @SerializedName("accountno")
    private String accountno;

    public RegisterDetailsModel(String firstname, String lastname, String phonenumber, String email, String dob, String password, String numberverified, String accountname, String accountno) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.numberverified = numberverified;
        this.accountname = accountname;
        this.accountno = accountno;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberverified() {
        return numberverified;
    }

    public void setNumberverified(String numberverified) {
        this.numberverified = numberverified;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
}
