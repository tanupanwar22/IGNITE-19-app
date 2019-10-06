package com.example.ignite19.Admin;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ignite19.ui.Notifications.NotificationPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class NotificationSenderAdmin {

    public static void uploadToFirebase(final Context context, final String titleInput, final String bodyInput, final String topicCollegeName, String uuid) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        String pushKey = databaseReference.push().getKey();

        //make an object of type notification
        NotificationPOJO pojo = new NotificationPOJO(titleInput,bodyInput, ServerValue.TIMESTAMP);
        databaseReference.child("Notifications").child(pushKey).setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sendPostRequest(context,titleInput,bodyInput,topicCollegeName);
            }
        });
    }

    public static void sendPostRequest(final Context context, String titleInput, String bodyInput, final String collegeName) {
        try{

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject data = new JSONObject();
            data.put("title", titleInput);
            data.put("text", bodyInput);
            data.put("android_channel_id","com.example.ignite19");
            JSONObject notification_data = new JSONObject();
            notification_data.put("data", data);
            //can specify the topics here
            notification_data.put("to","/topics/" + collegeName);

            JsonObjectRequest request = new JsonObjectRequest(url, notification_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toasty.success(context,"Notified " + collegeName,Toasty.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(context,"failed to notify " + collegeName ,Toasty.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    String api_key_header_value = "key=AAAAH8C3opc:APA91bEX5yIMAfRseTVmtSF9LgY_5c2tu1-LL-GxQ1DLcA0CuXVE4XpQitSUVk4TFbHAng-iai9VsNwHJdHYPO3S_10YrTsTVTck4crDiC4DBqPsgeeOyx6TkXrvlxHl3e8hM8EhF-GR";
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", api_key_header_value);
                    return headers;
                }
            };

            queue.add(request);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
