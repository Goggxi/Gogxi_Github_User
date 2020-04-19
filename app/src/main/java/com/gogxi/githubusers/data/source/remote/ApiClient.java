package com.gogxi.githubusers.data.source.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = " https://api.github.com";
    public static final String TOKEN_GITHUB = "5527a894c083b2a23625c3070d0ef2bf9ec411ca";

    private static Retrofit retrofit = null;

    public ApiService getClient(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
