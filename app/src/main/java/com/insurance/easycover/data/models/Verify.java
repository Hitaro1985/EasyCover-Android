package com.insurance.easycover.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verify {

    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("verifyToken")
    @Expose
    public String verifyToken;

}