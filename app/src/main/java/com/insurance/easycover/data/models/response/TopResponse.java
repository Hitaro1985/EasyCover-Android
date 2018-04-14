package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by naveedali on 11/15/17.
 */

public class TopResponse {

    @SerializedName("message")
    public String message;
    @SerializedName("response_code")
    public int responseCode;
}
