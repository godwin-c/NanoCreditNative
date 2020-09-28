package com.mtechcomm.nanocreditnative.models;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerModel {

   private String name;
   private String surname;
   private String document;
   private ArrayList<HashMap<String, String>> phones;
   private String account;
//   private String language;

    public CustomerModel(String name, String surname, String document, ArrayList<HashMap<String, String>> phones, String account) {
        this.name = name;
        this.surname = surname;
        this.document = document;
        this.phones = phones;
        this.account = account;
//        this.language = language;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
}
