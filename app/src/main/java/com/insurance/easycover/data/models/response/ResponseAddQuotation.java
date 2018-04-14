package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/16/2018.
 */

public class ResponseAddQuotation {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("quotation_price")
    @Expose
    private String quotationPrice;
    @SerializedName("quotation_description")
    @Expose
    private String quotationDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
}
