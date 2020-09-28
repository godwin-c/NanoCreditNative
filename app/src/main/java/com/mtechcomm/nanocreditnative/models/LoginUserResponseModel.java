package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class LoginUserResponseModel {
    @SerializedName("id")
    private String id;

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
    private String numberVerified;
    @SerializedName("accountname")
    private String accountName;
    @SerializedName("accountno")
    private String accountnumber;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("cid")
    private String cid;
    @SerializedName("docid")
    private String docid;
    @SerializedName("loanid")
    private String loanid;
    @SerializedName("lstatus")
    private String lstatus;
    @SerializedName("last_login_datetime")
    private String last_login_datetime;

    public LoginUserResponseModel(String id, String firstname, String lastname, String phonenumber, String email, String dob,
                                  String password, String numberVerified, String accountName, String accountnumber,
                                  String created_at, String cid, String docid, String loanid, String lstatus, String last_login_datetime) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.numberVerified = numberVerified;
        this.accountName = accountName;
        this.accountnumber = accountnumber;
        this.created_at = created_at;
        this.cid = cid;
        this.docid = docid;
        this.loanid = loanid;
        this.lstatus = lstatus;
        this.last_login_datetime = last_login_datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNumberVerified() {
        return numberVerified;
    }

    public void setNumberVerified(String numberVerified) {
        this.numberVerified = numberVerified;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public String getLstatus() {
        return lstatus;
    }

    public void setLstatus(String lstatus) {
        this.lstatus = lstatus;
    }

    public String getLast_login() {
        return last_login_datetime;
    }

    public void setLast_login(String last_login) {
        this.last_login_datetime = last_login;
    }
}
