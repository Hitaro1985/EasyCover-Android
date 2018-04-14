package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class RequestJobDetail {
    @SerializedName("jobId")
    @Expose
    public Integer jobId;
}
