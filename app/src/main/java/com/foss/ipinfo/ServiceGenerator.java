package com.foss.ipinfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator
{
    private static final String baseURL="https://ipinfo.io";

    private static Integer timeOut=30;
    private static TimeUnit duration=TimeUnit.SECONDS;
    public static <S> S createService(Class<S> serviceClass)
    {
        OkHttpClient  httpClient=new OkHttpClient.Builder()
                .connectTimeout(timeOut,duration)
                .readTimeout(timeOut,duration )
                .writeTimeout(timeOut,duration)
                .cache(null)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(serviceClass);
    }
}
