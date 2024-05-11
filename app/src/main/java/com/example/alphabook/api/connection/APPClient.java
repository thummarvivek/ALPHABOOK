package com.example.alphabook.api.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APPClient {
//    public static final String BASEURL = "https://alphabookproject.000webhostapp.com/";
//     public static final String BASEURL = "http://vivekgroup.kesug.com/Alphabook/";
public static final String BASEURL = "http://192.168.29.154/Alphabook/";

    public static Retrofit getctient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }


}
