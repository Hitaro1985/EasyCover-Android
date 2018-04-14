package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by naveedali on 11/15/17.
 */

public class TopListDataResponse<T> extends TopResponse {

    @SerializedName("data")
    public List<T> data;

    public TopListDataResponse(List<T> data) {
        this.data = data;
    }


}
