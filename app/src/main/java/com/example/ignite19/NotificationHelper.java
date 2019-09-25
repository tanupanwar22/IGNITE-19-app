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
        Intent intent = new Intent(ct,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0, intent, 0);

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ct, MainActivity.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
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
