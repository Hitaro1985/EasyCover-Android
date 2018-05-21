package com.insurance.easycover.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
//    @SerializedName("devicename")
//    public String deviceName = "android";
    @SerializedName("devicetoken")
    public String deviceToken;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("usertype")
    public String usertype;
//    @SerializedName("usertype")
//    public String userType;
}