package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGetQuotDocument {
    @SerializedName("jobId")
    @Expose
    public Integer jobId;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
}
