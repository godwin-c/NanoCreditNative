package com.mtechcomm.nanocreditnative.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.mtechcomm.nanocreditnative.models.UpdateLoanDataModel;
import com.mtechcomm.nanocreditnative.models.UpdateUserDataModel;
import com.mtechcomm.nanocreditnative.net.Mtech_API_Interface;
import com.mtechcomm.nanocreditnative.net.Mtech_API_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLoanInfoService extends Service {
    public UpdateLoanInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Gson gson = new Gson();
        String data_sort = intent.getStringExtra("data");
        UpdateLoanDataModel updateLoanDataModel =  gson.fromJson(intent.getStringExtra("data"), UpdateLoanDataModel.class);
        beginUploadEvent(updateLoanDataModel);

        return START_REDELIVER_INTENT;
    }

    private void beginUploadEvent(UpdateLoanDataModel updateLoanDataModel) {
        Mtech_API_Interface mtech_api_interface = Mtech_API_client.getClient().create(Mtech_API_Interface.class);
        Call<UpdateUserDataModel> call = mtech_api_interface.updateLoanInfo(updateLoanDataModel);
        call.enqueue(new Callback<UpdateUserDataModel>() {
            @Override
            public void onResponse(Call<UpdateUserDataModel> call, Response<UpdateUserDataModel> response) {

                if (response.code() == 201){

                }
            }

            @Override
            public void onFailure(Call<UpdateUserDataModel> call, Throwable t) {

            }
        });
    }
}
