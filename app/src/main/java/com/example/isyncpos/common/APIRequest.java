package com.example.isyncpos.common;

import com.example.isyncpos.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequest {

    private static Retrofit instance;
    private static OkHttpClient client = new OkHttpClient();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    public static Retrofit getInstance(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .client(client
                            .newBuilder()
                            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                            .connectTimeout(BuildConfig.REQUEST_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(BuildConfig.REQUEST_WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(BuildConfig.REQUEST_READ_TIMEOUT, TimeUnit.SECONDS)
                            .build()
                    )
                    .baseUrl(BuildConfig.ENV.equals("DEVELOPMENT") ? BuildConfig.API_DEVELOPMENT_URL : BuildConfig.API_PRODUCTION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

}
