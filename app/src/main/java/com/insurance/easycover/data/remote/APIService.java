package com.insurance.easycover.data.remote;

import com.insurance.easycover.data.models.Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by PDC100 on 3/9/2018.
 */

public interface APIService {
    @POST("/posts")
    @FormUrlEncoded
    Call<Register> saveRegister(@Field("title") String title,
                                @Field("body") String body,
                                @Field("userId") long userId);
}
