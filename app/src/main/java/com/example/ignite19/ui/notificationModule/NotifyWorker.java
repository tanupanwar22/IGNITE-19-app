package com.example.ignite19.ui.notificationModule;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ignite19.LoginActivity;
import com.example.ignite19.MainActivity;
import com.example.ignite19.R;

import static android.content.ContentValues.TAG;

public class NotifyWorker extends Worker {
   private String GROUP_IGNITE19 = "com.example.ignite19";


    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }
    @NonNull
    @Override
    public Result doWork() {
        Log.d("ALPHA", "doWork: worker started" );
        triggerNotification();
        return Result.success();
    }
    public void triggerNotification(){

        String eventName = getInputData().getString("eventName");
        Log.d("ALPHA", "triggerNotification: notification triggered" );
        String eventLatitude = getInputData().getString("eventLatitude");
        String eventLongitude = getInputData().getString("eventLongitude");
        String eventVenue = getInputData().getString("eventVenue");

        double mLatitude = Double.parseDouble(eventLatitude);
        double mLongitude = Double.parseDouble(eventLongitude);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        //pending intent for directions Button action
        String myUri = String.format ("geo:%f,%f?q=%f,%f(%s)",mLatitude,mLongitude,mLatitude,mLongitude,"myLocation");
        Uri gmmIntentUri = Uri.parse(myUri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        PendingIntent mapPendingIntent = PendingIntent.getActivity(getApplicationContext(),0,mapIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),MainActivity.CHANNEL_ID )
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(eventName)
                .setContentText("Event will start in 10 minutes, reach venue in time")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Event will start in 10 minutes" + "\nVenue: " + eventVenue))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setGroup(GROUP_IGNITE19)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .addAction(R.drawable.ic_notifications_active_black_24dp,"Directions",mapPendingIntent);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            //setting priority for devices less than android oreo version
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setSound(alarmSound);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        int oneTimeID = (int) SystemClock.uptimeMillis();
        Log.d(TAG, "triggerNotification: notificationID " + oneTimeID);
        notificationManager.notify(oneTimeID, builder.build());

    }
    }



