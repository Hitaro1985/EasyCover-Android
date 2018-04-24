package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSMS {

    @SerializedName("phoneno")
    @Expose
    public String phoneno;

    @SerializedName("email")
    @Expose
    public String email;
}
