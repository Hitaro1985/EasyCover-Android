package com.insurance.easycover.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveedali on 12/5/17.
 */

public class InsuranceType {

    public InsuranceType(int pId, String type) {
        typeId = pId;
        insuranceType = type;
    }

    public int typeId;

    public String insuranceType;


    public static List<InsuranceType> getInsuranceTypes() {

        List<InsuranceType> insuranceTypes = new ArrayList<>();
        //insuranceTypes.add(new InsuranceType(1, "Fire Insurance"));
        //  insuranceTypes.add(new InsuranceType(5, "Health Insurance"));
        //  insuranceTypes.add(new InsuranceType(6, "Health Insurance"));

        return insuranceTypes;
    }


    @Override
    public String toString() {
        return insuranceType;
    }
}