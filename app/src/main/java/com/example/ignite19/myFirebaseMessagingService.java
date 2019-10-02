package com.example.ignite19;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class myFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*if(remoteMessage.getNotification()!=null){

            Map<String ,String> mMap = remoteMessage.getData();



        }*/
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        if(remoteMessage.getData().size() > 0){
            Log.d("romeo", "onMessageReceived: " + remoteMessage.getData());
        }
        NotificationHelper.triggerNotification(getApplicationContext(),title,text);

    }
}