package com.mtechcomm.nanocreditnative.repositories;

import androidx.lifecycle.MutableLiveData;

import com.mtechcomm.nanocreditnative.net.NLP_API_Interface;
import com.mtechcomm.nanocreditnative.net.NLP_Api_Client;

public class AccessTokenRepository {
    private static  AccessTokenRepository instance;
    private String accessToken = new String();
    NLP_API_Interface apiInterface;
    private static final String TAG = AccessTokenRepository.class.getSimpleName();

    public static AccessTokenRepository getInstance(){
        if (instance == null){
            instance = new AccessTokenRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getAccessToken(){
         apiInterface = NLP_Api_Client.getAuthClient().create(NLP_API_Interface.class);
        final MutableLiveData mutableLiveData = new MutableLiveData();

//        Call<String> call = apiInterface.getAuthToken();
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.d(TAG, "onResponse: Auth Gotten " + response.toString());
//                mutableLiveData.setValue(call);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });


        return mutableLiveData;
    }
}
