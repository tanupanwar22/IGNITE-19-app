package com.example.ignite19;

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
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class myFirebaseMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle = null;
        String messageText = null;
        String messageEventName = null;
        String messageEventVenue = null;
        String messageLongitude = null;
        String messageLatitude = null;


        if(remoteMessage.getData().size() > 0){
            Map<String ,String> mMap = remoteMessage.getData();
            messageTitle = mMap.get("title");
            messageText = mMap.get("text");
        }
        triggerNotification(this,messageTitle,messageText,messageEventName,messageEventVenue,messageLatitude,messageLongitude);
    }
    public  void triggerNotification(Context ct, String messageTitle, String messageText, String messageEventName, String messageEventVenue, String latitude, String longitude){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(ct,LoginActivity.class);
        intent.putExtra("mFlag","1");
        PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            //for devices with notification channel
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ct, MainActivity.CHANNEL_ID)
                    .setContentTitle(messageTitle)
                    .setContentText(messageText)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageText))
                    .setSmallIcon(R.drawable.ic_stat_logo33)
                    .setColor(getResources().getColor(R.color.goldenYellow))
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ct);
            int oneTimeID = (int) SystemClock.uptimeMillis();
            notificationManager.notify(oneTimeID, builder.build());
        }
        else{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ct, MainActivity.CHANNEL_ID)
                    .setContentTitle(messageTitle)
                    .setContentText(messageText)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageText))
                    .setSmallIcon(R.drawable.ic_stat_logo33)
                    .setColor(getResources().getColor(R.color.goldenYellow))
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ct);
            int oneTimeID = (int) SystemClock.uptimeMillis();
            notificationManager.notify(oneTimeID, builder.build());
        }
        }
}