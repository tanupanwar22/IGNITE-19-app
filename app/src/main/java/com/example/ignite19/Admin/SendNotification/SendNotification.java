package com.example.ignite19.Admin.SendNotification;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ignite19.Admin.AdminDataCommunication;
import com.example.ignite19.R;
import com.example.ignite19.ui.Notifications.NotificationPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.core.Tag;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNotification extends Fragment implements View.OnClickListener {
    ArrayList<String > collegeNames = new ArrayList<>();
    Button sendButton;
    EditText titleEditText,bodyEditText;
    AdminDataCommunication dataCommunication;
    HashMap<String ,String> uuidList = new HashMap<>();
    String titleInput,bodyInput;
    View v;

    public SendNotification() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (AdminDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uuidList = dataCommunication.getAllUUIDs();
        Bundle bundle = getArguments();
        if(bundle!=null){
            collegeNames = bundle.getStringArrayList("college_names");

        }


        for(int i = 0 ; i < collegeNames.size();++i){
            Log.d("milk", "onCreateView: " + collegeNames.get(i));
        }

        v = inflater.inflate(R.layout.fragment_send_notification, container, false);
        titleEditText = v.findViewById(R.id.not_title);
        bodyEditText = v.findViewById(R.id.not_body);
        sendButton = v.findViewById(R.id.not_button);
        sendButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.not_button:
                if(checkEditTextEmptyOrNot()){
                    //now lets put this input in the form of message and send it as post request and message
                    for(int i = 0 ; i <collegeNames.size();++i){
                        //changing college name to proper format
                       String topicCollegeName = collegeNames.get(i).replaceAll(" ","_").toLowerCase();
                       String uuid = uuidList.get(topicCollegeName);
                        Log.d("milk", "onClick: " + uuid  + " " + topicCollegeName);
                       uploadToFirebase(titleInput,bodyInput,topicCollegeName,uuid);
                    }

                  //  sendToFirebase(titleInput,bodyInput);
                }
                else{
                    Toasty.error(getContext(),"Please enter values in both the fields",Toasty.LENGTH_SHORT).show();
                }
        }
    }

    private void uploadToFirebase(final String titleInput, final String bodyInput, final String topicCollegeName, String uuid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uuid);
        String pushKey = databaseReference.push().getKey();

        //make an object of type notification
        NotificationPOJO pojo = new NotificationPOJO(titleInput,bodyInput, ServerValue.TIMESTAMP);
        databaseReference.child("Notifications").child(pushKey).setValue(pojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                sendPostRequest(titleInput,bodyInput,topicCollegeName);
            }
        });
    }



    private void sendPostRequest(String titleInput, String bodyInput, final String collegeName) {
        try{

            RequestQueue queue = Volley.newRequestQueue(getContext());
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
                    Log.d("milk", "onResponse: success" + " from" + collegeName + response.toString());
                    Toasty.success(getContext(),"Notified " + collegeName,Toasty.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("milk", "onResponse: error from " + collegeName + error.toString());
                    Toasty.error(getContext(),"failed to notify " + collegeName ,Toasty.LENGTH_LONG).show();
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

    private boolean checkEditTextEmptyOrNot() {
        titleInput = titleEditText.getText().toString();
        bodyInput = bodyEditText.getText().toString();
        if(TextUtils.isEmpty(titleInput) || TextUtils.isEmpty(bodyInput)){
            return false;
        }
        else {
            return true;
        }
    }
}
