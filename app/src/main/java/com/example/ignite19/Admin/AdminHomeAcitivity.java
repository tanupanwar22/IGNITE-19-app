package com.example.ignite19.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.WorkManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.EventScheduleStaticScheduleClass;
import com.example.ignite19.LoginActivity;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;
import com.example.ignite19.UserDetail;
import com.example.ignite19.ui.schedule.eventSchedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminHomeAcitivity extends AppCompatActivity implements AdminDataCommunication, DataCommunication {


    ArrayList<String> collegeNames = new ArrayList<>();
    ArrayList<String> eventNames = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<eventSchedule> day0CompleteSchedule = new ArrayList<>();
    private ArrayList<eventSchedule> day1CompleteSchedule = new ArrayList<>();
    private ArrayList<eventSchedule> day2CompleteSchedule = new ArrayList<>();
    private HashMap<String ,String> eventWithDateTime = new HashMap<>();
    private HashMap<String,String> uuidList = new HashMap<>();
    Boolean flag1Status = false,flag2Status = false;
    String fcmFlag = null;
    int fcmFlagInt = 0;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_acitivity);
        getSupportActionBar().setTitle("Admin Panel");

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        View hView = navigationView.getHeaderView(0);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.adminHomeFragment, R.id.nav_about_ignite_admin, R.id.nav_sponsors_admin,
                R.id.nav_sign_out_admin,R.id.nav_about_app_admin,R.id.nav_schedule_admin)
                .setDrawerLayout(drawer)
                .build();


        day0CompleteSchedule = EventScheduleStaticScheduleClass.getDay0EventSchedule();
        day1CompleteSchedule = EventScheduleStaticScheduleClass.getDay1EventSchedule();
        day2CompleteSchedule = EventScheduleStaticScheduleClass.getDay2EventSchedule();

        NavController navController = Navigation.findNavController(this, R.id.admin_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                  String uuid = snapshot.getKey();
                  UserDetail userDetail = snapshot.getValue(UserDetail.class);
                  String college_name = userDetail.getCollege_name();
                  collegeNames.add(college_name);
                  String topicNameForCollegeName = college_name.replaceAll(" ","_").toLowerCase();
                  uuidList.put(topicNameForCollegeName,uuid);
              }
              flag1Status = true;
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
                    String event_time = mList.getEvent_date();
                    eventWithDateTime.put(event_name,event_time);
                    eventNames.add(event_name);
                }
                flag2Status = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = getIntent();
       String uuid = intent.getStringExtra("UUID");
        String displayName = intent.getStringExtra("userName");
        fcmFlag = intent.getStringExtra("mFlag");

        if(fcmFlag !=null){
            fcmFlagInt = Integer.valueOf(fcmFlag);
        }
        if(fcmFlagInt == 1){
            Navigation.findNavController(this, R.id.admin_host_fragment).navigate(R.id.action_adminHomeFragment_to_seeNotificationAdmin);
        }


        //subscribe to topic admin_xx
        FirebaseMessaging.getInstance().subscribeToTopic("admin_xx")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "succesfully subscribed";
                        if (!task.isSuccessful()) {
                            msg = "subscription failed";
                        }
                   //     Log.d(TAG, msg);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_admin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.admin_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<String> getCollegeNames() {
        return collegeNames;
    }

    @Override
    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    @Override
    public HashMap<String, String> getEventWithDateAndTime() {
        return eventWithDateTime;
    }

    @Override
    public HashMap<String,String> getAllUUIDs() {
        return uuidList;
    }

    @Override
    public Boolean getFlag1Status() {
        return flag1Status;
    }

    @Override
    public Boolean getFlag2Status() {
        return flag2Status;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.admin_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickSignOutAdmin(MenuItem item) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Sign Out");
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        //clear user workmanager notifications
                        WorkManager.getInstance(getApplicationContext()).cancelAllWork();
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public ArrayList<eventSchedule> getDay0CompleteSchedule() {
        return day0CompleteSchedule;
    }

    @Override
    public ArrayList<eventSchedule> getDay1CompleteSchedule() {
        return day1CompleteSchedule;
    }

    @Override
    public ArrayList<eventSchedule> getDay2CompleteSchedule() {
        return day2CompleteSchedule;
    }

    @Override
    public ArrayList<UserDataAndEventList> getAllEventList() {
        return null;
    }

    @Override
    public ArrayList<Participation> getUserParticipationDetails() {
        return null;
    }

    @Override
    public String getUUID() {
        return null;
    }

    @Override
    public ArrayList<String> getRegisteredEventNames() {
        return null;
    }

    @Override
    public ArrayList<String> getUnRegisteredEventNames() {
        return null;
    }

    @Override
    public UserDetail getUserDetail() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public Boolean getFirstListenerFlagStatus() {
        return null;
    }

    @Override
    public Boolean getSecondListenerFlagStatus() {
        return null;
    }
}
