package com.foss.ipinfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API
{
    @GET("json")
    Call<IpInfo> getIpInfo();
}

