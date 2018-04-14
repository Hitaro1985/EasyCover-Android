package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/18/2018.
 */

public class RequestAssignedJob {
    @SerializedName("assignedjobid")
    @Expose
    public String assignedjobid;
}
