package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.insurance.easycover.data.models.response.CreateJobDetail;
import com.insurance.easycover.data.models.response.CreateJobDocument;

import java.util.List;

/**
 * Created by PDC100 on 3/13/2018.
 */

public class ResponseCreateJob {

    @SerializedName("job")
    @Expose
    private CreateJobDetail job;
    @SerializedName("documents")
    @Expose
    private List<CreateJobDocument> documents = null;

    public CreateJobDetail getJob() {
        return job;
    }

    public void setJob(CreateJobDetail job) {
        this.job = job;
    }

    public List<CreateJobDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<CreateJobDocument> documents) {
        this.documents = documents;
    }

}