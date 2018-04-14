package com.insurance.easycover.data.models.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/13/2018.
 */

public class CreateJobData {

    @SerializedName("job")
    @Expose
    private List<Job> job = null;
    @SerializedName("documents")
    @Expose
    private List<Object> documents = null;

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }

    public List<Object> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Object> documents) {
        this.documents = documents;
    }

}