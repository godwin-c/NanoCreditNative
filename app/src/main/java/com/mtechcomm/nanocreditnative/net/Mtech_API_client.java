package com.mtechcomm.nanocreditnative.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Mtech_API_client {

    private static final String BASE_URL = "http://52.206.231.182/nlp/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
