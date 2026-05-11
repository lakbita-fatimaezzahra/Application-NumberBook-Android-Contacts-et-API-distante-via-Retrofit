package com.example.numberbook;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final String API_LINK =
            "http://10.0.2.2/numberbook-api/api/";

    private static Retrofit retrofitInstance;

    public static Retrofit buildConnection() {

        if (retrofitInstance == null) {

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(API_LINK)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }
}