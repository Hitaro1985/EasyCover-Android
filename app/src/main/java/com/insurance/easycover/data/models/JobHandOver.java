
package com.insurance.easycover.data.models;

import com.google.gson.annotations.SerializedName;


public class JobHandOver {

    @SerializedName("agentid")
    public Long mAgentid;
    @SerializedName("customerid")
    public Long mCustomerid;
    @SerializedName("jobid")
    public Long mJobid;

}
