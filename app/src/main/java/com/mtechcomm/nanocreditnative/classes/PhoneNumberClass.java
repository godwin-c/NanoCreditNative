package com.mtechcomm.nanocreditnative.classes;

public class PhoneNumberClass {
    private String Id;
    private String country;
    private String code;

    public PhoneNumberClass(String id, String country, String code) {
        Id = id;
        this.country = country;
        this.code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
