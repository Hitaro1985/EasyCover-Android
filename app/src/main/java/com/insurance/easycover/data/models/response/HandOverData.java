package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class HandOverData {
    @SerializedName("agentid")
    @Expose
    public Integer agentid;
    @SerializedName("customerid")
    @Expose
    public Integer customerid;
    @SerializedName("jobid")
    @Expose
    public Integer jobid;
}
