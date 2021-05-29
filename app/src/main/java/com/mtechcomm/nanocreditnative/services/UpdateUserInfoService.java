package com.mtechcomm.nanocreditnative.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.mtechcomm.nanocreditnative.R;
import com.mtechcomm.nanocreditnative.activities.DashboardActivity;

public class UpdateUserInfoService extends Service {

    private static final String TAG = UpdateUserInfoService.class.getSimpleName();
    private PowerManager.WakeLock wakeLock = null;
    private Boolean isServiceStarted = false;

    public UpdateUserInfoService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = createNotification();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            String action = intent.getAction();
            Log.d(TAG, "onStartCommand: Action : " + action);
            Actions actions = Actions.valueOf(action);
            switch (actions){
                case START:
                    startBGService();
                    break;
                case STOP:
                    stopBGService();
                    break;
                default :
                    Log.d(TAG, "onStartCommand: Unusual Situation");
                    stopBGService();
            }
        }else{
            Log.d(TAG, "onStartCommand: We have a null Intent");
        }
        return START_STICKY;
    }

    private void stopBGService() {
        try {
            if (wakeLock.isHeld())
                wakeLock.release();
            stopForeground(true);
            stopSelf();
        } catch (Exception e) {
            Log.d(TAG, "stopBGService: Service stopped without being started : " + e.getMessage());
            //log("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false;
        (new ServiceTracker()).setServiceState(this, ServiceTracker.ServiceState.STOPPED);
    }

    private void startBGService() {
        if (isServiceStarted)
            return;

        isServiceStarted = true;
        (new ServiceTracker()).setServiceState(this, ServiceTracker.ServiceState.STARTED);
        //wakeLock = getSystemService(Context.POWER_SERVICE).

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "UpdateUserInfoService::lock");
        wakeLock.acquire();

        //Perform Network Activity Here


    }

    private Notification createNotification(){
        String notificationChannelId = "ENDLESS SERVICE CHANNEL";

        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel chanel = new NotificationChannel(notificationChannelId,"UpdateUserInfoNotification",NotificationManager.IMPORTANCE_HIGH);
            chanel.setDescription("User Info Update");;
            chanel.enableLights(true);
            chanel.setLightColor(Color.RED);
            chanel.enableVibration(true);
            chanel.setVibrationPattern(new long[90]);

            notificationManager.createNotificationChannel(chanel);

            // Create an explicit intent for an Activity in your app

            
            builder.setChannelId(notificationChannelId);
        }

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return builder.setContentTitle("Nano Credit User Update")
                .setContentText("Your details are being updated")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Nano Credit")
                .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
