package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAcceptAgent {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("jobid")
    @Expose
    public Integer jobid;
    @SerializedName("costomerId")
    @Expose
    public Integer costomerId;
    @SerializedName("agentId")
    @Expose
    public Integer agentId;
}
