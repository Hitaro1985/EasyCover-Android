package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/16/2018.
 */

public class ResponseGetQuotation {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nric")
    @Expose
    private Integer nric;
    @SerializedName("phoneno")
    @Expose
    private String phoneno;
    @SerializedName("insurance_type")
    @Expose
    private String insuranceType;
    @SerializedName("indicative_sum")
    @Expose
    private Integer indicativeSum;
    @SerializedName("job_status")
    @Expose
    private Integer jobStatus;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("quotation_price")
    @Expose
    private String quotationPrice;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("quotation_description")
    @Expose
    private String quotationDescription;
    @SerializedName("customer_id")
    @Expose
    private int customer_id;
    @SerializedName("jobstatus")
    @Expose
    private String jobstatus;
    @SerializedName("quotation_id")
    @Expose
    private int quotationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNric() {
        return nric;
    }

    public void setNric(Integer nric) {
        this.nric = nric;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Integer getIndicativeSum() {
        return indicativeSum;
    }

    public void setIndicativeSum(Integer indicativeSum) {
        this.indicativeSum = indicativeSum;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQuotationPrice() {
        return quotationPrice;
    }

    public void setQuotationPrice(String quotationPrice) {
        this.quotationPrice = quotationPrice;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getQuotationDescription() {
        return quotationDescription;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public int getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(int quotationId) {
        this.quotationId = quotationId;
    }

    public void setQuotationDescription(String quotationDescription) {
        this.quotationDescription = quotationDescription;
    }
}
