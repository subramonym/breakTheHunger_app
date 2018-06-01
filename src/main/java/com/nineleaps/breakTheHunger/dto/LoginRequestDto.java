package com.nineleaps.breakTheHunger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestDto {
    @JsonProperty
    String mobileNo;
    @JsonProperty
    String password;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
