package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class RequestAccept {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("jobid")
    @Expose
    public Integer jobid;
    @SerializedName("costomerId")
    @Expose
    public Integer costomerId;
}
