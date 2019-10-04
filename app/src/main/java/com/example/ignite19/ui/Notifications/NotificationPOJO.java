package com.example.ignite19.ui.Notifications;

import android.app.Notification;

import androidx.room.Ignore;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NotificationPOJO {

    private String title;
    private String body;
    Object timeStamp;

NotificationPOJO(){

}
    public NotificationPOJO(String title, String body, Object timeStamp) {
        this.title = title;
        this.body = body;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
    public Object getTimeStamp() {
        return timeStamp;
    }

    @Exclude
    public long timeStamp(){
        return (long)timeStamp;
    }
}
