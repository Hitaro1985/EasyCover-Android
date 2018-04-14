package com.insurance.easycover.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDC100 on 3/18/2018.
 */

public class CompanyType {

    public CompanyType(int pId, String type) {
        typeId = pId;
        companyType = type;
    }

    public int typeId;

    public String companyType;


    public static List<CompanyType> getCompanyType() {

        List<CompanyType> companyTypes = new ArrayList<>();
        //insuranceTypes.add(new InsuranceType(1, "Fire Insurance"));
        //  insuranceTypes.add(new InsuranceType(5, "Health Insurance"));
        //  insuranceTypes.add(new InsuranceType(6, "Health Insurance"));

        return companyTypes;
    }


    @Override
    public String toString() {
        return companyType;
    }
}
