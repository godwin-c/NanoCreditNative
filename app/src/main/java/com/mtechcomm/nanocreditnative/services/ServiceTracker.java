package com.mtechcomm.nanocreditnative.services;

import android.content.Context;
import android.content.SharedPreferences;

public class ServiceTracker {

    public enum ServiceState {
        STARTED,
        STOPPED,
    }

    private static final String NAME = "SPYSERVICE_KEY";
    private static final String KEY = "SPYSERVICE_STATE";

    public void setServiceState(Context context, ServiceState serviceState) {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putString(KEY, serviceState.name()).apply();
    }

    public ServiceState getServiceState(Context context) {
        SharedPreferences preferences = getPreferences(context);
        String value = preferences.getString(KEY, ServiceState.STOPPED.name());
        return ServiceState.valueOf(value);
    }

    private SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(NAME, 0);
    }
}
