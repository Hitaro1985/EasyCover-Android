package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.SerializedName;

public class RequestResetPassword {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("verifyCode")
    public String verifyCode;

    @SerializedName("userrole")
    public String userrole;

}
