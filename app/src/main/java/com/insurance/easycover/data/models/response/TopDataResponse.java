package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by naveedali on 11/15/17.
 */

public class TopDataResponse<T> extends TopResponse {

    @SerializedName("data")
    public T data;

    public TopDataResponse(T data) {
        this.data = data;
    }


}
