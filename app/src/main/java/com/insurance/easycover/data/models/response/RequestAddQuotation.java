package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PDC100 on 3/15/2018.
 */

public class RequestAddQuotation {
    @SerializedName("quotation_price")
    @Expose
    public String quotationPrice;
    @SerializedName("job_id")
    @Expose
    public Integer jobId;
    @SerializedName("assign_id")
    @Expose
    public Integer assignId;
    @SerializedName("quotation_description")
    @Expose
    public String quotationDescription;
    @SerializedName("documents")
    @Expose
    public List<String> documents;
}
