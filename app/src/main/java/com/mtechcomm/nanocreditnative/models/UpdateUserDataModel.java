package com.mtechcomm.nanocreditnative.models;

import com.google.gson.annotations.SerializedName;

public class UpdateUserDataModel {
    @SerializedName("cid")
    private String cid;
    @SerializedName("docid")
    private String docid;
    @SerializedName("accountno")
    private String accountno;
    @SerializedName("email")
    private String email;

    public UpdateUserDataModel(String cid, String docid, String accountno, String email) {
        this.cid = cid;
        this.docid = docid;
        this.accountno = accountno;
        this.email = email;
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

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
