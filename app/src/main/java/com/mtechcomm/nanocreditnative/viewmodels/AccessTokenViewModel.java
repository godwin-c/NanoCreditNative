package com.mtechcomm.nanocreditnative.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mtechcomm.nanocreditnative.repositories.AccessTokenRepository;

public class AccessTokenViewModel extends ViewModel {
    private MutableLiveData<String> apiAccessToken;
    private AccessTokenRepository tokenRepository;
    public void init(){
        if (apiAccessToken != null){
            return;
        }
        tokenRepository = AccessTokenRepository.getInstance();
        apiAccessToken = tokenRepository.getAccessToken();
    }
    public LiveData<String> getApiAccessToken(){
        return apiAccessToken;
    }
}
