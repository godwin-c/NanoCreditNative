package com.mtechcomm.nanocreditnative.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NLP_Api_Client {
    private static final String BASE_URL = "https://nlp-gh.oncustomer.tech/api/"; //Resources.getSystem().getString(R.string.BASE_URL);
    private static final String AUTH_BASE_URL = "https://nlp-gh.oncustomer.tech/api/"; //Resources.getSystem().getString(R.string.AUTH_BASE_URL);
    private static final String API_USER_CREDENTIALS = "ussd:ussd1234:SHARE:SHARED"; //Resources.getSystem().getString(R.string.API_USER_CREDENTIALS);

    private static Retrofit retrofit = null;
    private static Retrofit auth_retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getAuthClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", "Basic dXNzZDp1c3NkMTIzNDpTSEFSRQ==")
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (auth_retrofit == null) {
            auth_retrofit = new Retrofit.Builder()
                    .baseUrl(AUTH_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return auth_retrofit;
    }

}
