package com.insurance.easycover.data.models.response;

/**
 * Created by PDC100 on 3/9/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSignUp {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SinUpData data;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SinUpData getData() {
        return data;
    }

    public void setData(SinUpData data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

}