package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PDC100 on 3/9/2018.
 */

public class ResponseLogin {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private User data;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("userVerified")
    @Expose
    private List<Integer> userVerified = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
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

    public List<Integer> getUserVerified() {
        return userVerified;
    }

    public void setUserVerified(List<Integer> userVerified) {
        this.userVerified = userVerified;
    }
}
