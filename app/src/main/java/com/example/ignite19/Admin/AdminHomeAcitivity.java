package com.example.ignite19.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;
import com.example.ignite19.UserDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeAcitivity extends AppCompatActivity implements AdminDataCommunication {


    ArrayList<String> collegeNames = new ArrayList<>();
    ArrayList<String> eventNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_acitivity);
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                  UserDetail userDetail = snapshot.getValue(UserDetail.class);
                  String college_name = userDetail.getCollege_name();
                  collegeNames.add(college_name);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("EventDesc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDataAndEventList mList   = snapshot.getValue(UserDataAndEventList.class);
                    String event_name = mList.getEvent_name();
                    Log.d("romeo", "onDataChange: event_name " + event_name);
                    eventNames.add(event_name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<String> getCollegeNames() {
        return collegeNames;
    }

    @Override
    public ArrayList<String> getEventNames() {
        return eventNames;
    }
}
