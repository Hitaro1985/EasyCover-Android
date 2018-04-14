package com.insurance.easycover.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PDC100 on 3/14/2018.
 */

class ServiceGenerator {

    public String mbaseURL;

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder;

    public static <S> S createService(Class<S> serviceClass, String baseURL) {
        builder =     new Retrofit.Builder()
                        .baseUrl(baseURL)
                        //THIS IS THE LINE WITH ERROR!!!!!!!!!!!!
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}