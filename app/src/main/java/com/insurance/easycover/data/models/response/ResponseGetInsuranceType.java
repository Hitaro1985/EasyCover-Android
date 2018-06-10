package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PDC100 on 3/16/2018.
 */

public class ResponseGetInsuranceType {

    @SerializedName("insurance_name")
    @Expose
    private Object insuranceName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companys")
    @Expose
    private List<Company> companys = null;

    public String getInsuranceName() {
        return (String)insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(List<Company> companys) {
        this.companys = companys;
    }
}