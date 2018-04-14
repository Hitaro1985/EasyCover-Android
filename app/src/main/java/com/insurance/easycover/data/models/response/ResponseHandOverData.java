package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class ResponseHandOverData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("jobstatus")
    @Expose
    private Object jobstatus;
    @SerializedName("quotation_id")
    @Expose
    private Object quotationId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Object getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(Object jobstatus) {
        this.jobstatus = jobstatus;
    }

    public Object getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Object quotationId) {
        this.quotationId = quotationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
