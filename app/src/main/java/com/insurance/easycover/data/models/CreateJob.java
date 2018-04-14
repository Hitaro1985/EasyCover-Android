
package com.insurance.easycover.data.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CreateJob {

    @SerializedName("name")
    public String name;
    @SerializedName("nric")
    public String nric;
    @SerializedName("insurencetype")
    public String insurencetype;
    @SerializedName("indicative_sum")
    public String indicative_sum;
    @SerializedName("address")
    public String address;
    @SerializedName("postcode")
    public String postcode;
    @SerializedName("state")
    public String state;
    @SerializedName("country")
    public String country;
    @SerializedName("expired_date")
    public String expired_date;
    @SerializedName("phoneno")
    public String phoneno;
    @SerializedName("company_id")
    public Integer companyId;
//    @SerializedName("userid")
//    public String userid;

    @SerializedName("documents")
    public List<String> documents;


}
