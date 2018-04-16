package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGetQuotationById {
    @SerializedName("quotation_id")
    @Expose
    public Integer quotationId;
}
