package com.example.ignite19;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ignite19.ui.notificationModule.NotifyWorker;

import static android.content.ContentValues.TAG;

public class NotificationHelper {


    public static void triggerNotification(Context ct,String title,String text){
        // Create an explicit intent for an Activity in your app
   String GROUP_IGNITE19 = "com.example.ignite19";
        Intent intent = new Intent(ct,LoginActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("text",text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //pending intent for directions Button action
        //add direction buttons only when remote message has longitude and lattitude
        String myUri = String.format ("geo:%f,%f?q=%f,%f(%s)",null ,null,null,null,"myLocation");
        Uri gmmIntentUri = Uri.parse(myUri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        PendingIntent mapPendingIntent = PendingIntent.getActivity(ct,0,mapIntent,0);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ct, MainActivity.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setGroup(GROUP_IGNITE19)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .addAction(R.drawable.location,"Directions",mapPendingIntent);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setSound(alarmSound);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ct);
        int oneTimeID = (int) SystemClock.uptimeMillis();
        Log.d(TAG, "triggerNotification: notificationID " + oneTimeID);
        notificationManager.notify(oneTimeID, builder.build());

    }
}
