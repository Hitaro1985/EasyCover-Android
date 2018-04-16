package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestComplete {
    @SerializedName("jobid")
    @Expose
    public Integer jobid;
}