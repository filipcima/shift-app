package com.example.cimafilip.shiftapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String API_URL = "http://192.168.2.103:5000/"; // should be in config file
    private static Gson gson = new GsonBuilder()
            .setDateFormat("%d/%m/%y %H:%M:%S")
            .create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static IAPIEndpoints apiService = retrofit.create(IAPIEndpoints.class);

    public static IAPIEndpoints getApiService() {
        return apiService;
    }
}
