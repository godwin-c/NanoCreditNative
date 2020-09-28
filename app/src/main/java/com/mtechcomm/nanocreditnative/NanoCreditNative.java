package com.mtechcomm.nanocreditnative;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.lenddo.mobile.core.LenddoCoreInfo;
import com.lenddo.mobile.datasdk.AndroidData;
import com.lenddo.mobile.datasdk.models.ClientOptions;


public class NanoCreditNative extends MultiDexApplication implements Application.ActivityLifecycleCallbacks{

    private boolean activityInForeground;

    @Override
    public void onCreate() {
        super.onCreate();

        LenddoCoreInfo.initCoreInfo(getApplicationContext());
        LenddoCoreInfo.setOnboardingPartnerScriptId("5defb6c71d523a0dd834de5a");
        ClientOptions clientOptions = new ClientOptions();
        clientOptions.enableLogDisplay(BuildConfig.DEBUG);


        AndroidData.setup(getApplicationContext(), clientOptions);

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        //Here you can add all Activity class you need to check whether its on screen or not

        activityInForeground = activity instanceof MainActivity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isActivityInForeground() {
        return activityInForeground;
    }
}
