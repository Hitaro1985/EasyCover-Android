package com.insurance.easycover.data.network;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NaveedAli on 3/14/2017.
 */

public class EasyCoverServiceFactory {

    public static IEasyCoverEndPointApi service;
    public static String AUTH_USERNAME = "";
    public static String AUTH_PASSWORD = "";

    public static IEasyCoverEndPointApi getInstance() {
        IEasyCoverEndPointApi IDuaEndPointApi = null;
        if (IDuaEndPointApi == null) {


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

            okHttpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Content-Type","application/json").build();
                    //setLogLevel(RestAdapter.LogLevel.FULL);
                    Log.e("here", String.format("\nrequest:\n%s\nheaders:\n%s", request.body().toString(), request.headers()));
                    return chain.proceed(request);
                }
            });

           /*
           // enable cache if there is need
           File httpCacheDirectory = new File(AppClass.getContext().getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .cache(cache)
                    .build();
 */
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(ApiConstants.BASE_URL)
                    .client(okHttpClient.build())
                    .build();

            IDuaEndPointApi = retrofit.create(IEasyCoverEndPointApi.class);
        }
        return IDuaEndPointApi;
    }


    /****
     * @return
     */
    private static String addAuthHeader() {
        String authStr = AUTH_USERNAME + ":" + AUTH_PASSWORD;
        // Encode authentication values, and add to header
        String authStrEncoded = "";
        try {
            authStrEncoded = new String(Base64.encode(
                    authStr.getBytes("UTF-8"), Base64.NO_WRAP));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return authStrEncoded;
    }

    /*private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 640000, 2419200))
                    .build();
        }
    };*/

}
