package com.insurance.easycover.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PDC100 on 3/16/2018.
 */

public class ResponseReferralCode {
    @SerializedName("referral_code")
    @Expose
    private String referralCode;

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
