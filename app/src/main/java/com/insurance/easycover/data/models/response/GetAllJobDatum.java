package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/14/2018.
 */

public class GetAllJobDatum {
    @SerializedName("jobs")
    @Expose
    private ShowJob jobs;

    public ShowJob getJobs() {
        return jobs;
    }

    public void setJobs(ShowJob jobs) {
        this.jobs = jobs;
    }

}