package com.insurance.easycover.data.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Register {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("devicename")
    @Expose
    public String deviceName = "android";
    @SerializedName("devicetoken")
    @Expose
    public String deviceToken;
    @SerializedName("usertype")
    @Expose
    public String userType;
    @SerializedName("verifyToken")
    @Expose
    public String verifyToken;
    @SerializedName("longitude")
    @Expose
    public long longitude;
    @SerializedName("latitude")
    @Expose
    public long latitude;

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    @Override
    public String toString() {
        return "Register{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", password='" + password + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", userType='" + userType + '\'' +
                ", verifyToken='" + verifyToken + '\'' +
                '}';
    }
}