package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class ResponseCompletedJobs {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("assignJobsId")
    @Expose
    private Integer assignJobsId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneno")
    @Expose
    private String phoneno;
    @SerializedName("nrc")
    @Expose
    private String nrc;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("verifyToken")
    @Expose
    private String verifyToken;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("devicetoken")
    @Expose
    private String devicetoken;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("refferalcode")
    @Expose
    private Object refferalcode;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("isAvailable")
    @Expose
    private Integer isAvailable;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("assUpdatedAt")
    @Expose
    private String assUpdatedAt;
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
    private String jobstatus;
    @SerializedName("quotation_id")
    @Expose
    private Integer quotationId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nric")
    @Expose
    private BigInteger nric;
    @SerializedName("insurance_type")
    @Expose
    private String insuranceType;
    @SerializedName("indicative_sum")
    @Expose
    private Integer indicativeSum;
    @SerializedName("job_status")
    @Expose
    private Integer jobStatus;
    @SerializedName("quotation_price")
    @Expose
    private String quotationPrice;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("language")
    @Expose
    private String language;

    public Integer getAssignJobsId() {
        return assignJobsId;
    }

    public void setAssignJobsId(Integer assignJobsId) {
        this.assignJobsId = assignJobsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Object getRefferalcode() {
        return refferalcode;
    }

    public void setRefferalcode(Object refferalcode) {
        this.refferalcode = refferalcode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
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

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
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

    public BigInteger getNric() {
        return nric;
    }

    public void setNric(BigInteger nric) {
        this.nric = nric;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAssUpdatedAt() {
        return assUpdatedAt;
    }

    public void setAssUpdatedAt(String assUpdatedAt) {
        this.assUpdatedAt = assUpdatedAt;
    }
}
