package com.insurance.easycover.data.models.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/14/2018.
 */

public class ResponseGetAllJob {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<GetAllJobDatum> data = null;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetAllJobDatum> getData() {
        return data;
    }

    public void setData(List<GetAllJobDatum> data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}