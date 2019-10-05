package com.example.ignite19;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ignite19.ui.notificationModule.NotifyWorker;
import com.example.ignite19.ui.schedule.eventSchedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity implements DataCommunication {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView userNameHeaderTextView;
    private ArrayList<eventSchedule> day0CompleteSchedule = new ArrayList<>();
    private ArrayList<eventSchedule> day1CompleteSchedule = new ArrayList<>();
    private ArrayList<eventSchedule> day2CompleteSchedule = new ArrayList<>();
    private ArrayList<UserDataAndEventList> userEventList = new ArrayList<>();
    private ArrayList<Participation> userParticipationDetails = new ArrayList<>();
    private ArrayList<UserDataAndEventList.Builder> userEventListBuilder = new ArrayList<>();
    public static String CHANNEL_ID = "com.example.ignite19";
    private final DatabaseReference referenceToEventDesc = FirebaseDatabase.getInstance().getReference("EventDesc");
    private DatabaseReference referenceToUserParticipation;
    ArrayList<String> eventNameList = new ArrayList<>();
    private String TAG = "alpha";
    private UserDetail userDetail;
    private String displayName;
    private  boolean FLAG1 = false;
    private boolean FLAG2 = false;
    String fcmTitle;
    String fcmText;
    String fcmVenue;
    String fcmEventName;
   // String fcmGeoLocation;
    String fcmLatitude;
    String fcmLongitude;
    AlertDialog alertDialog;


    TextView fcmTitleTextView,fcmTextTextView,fcmEventNameTextView,fcmEventVenueTextView,fcmEventParticipantsTextView;


    private ArrayList<String > registeredEventNames = new ArrayList<>();
    private ArrayList<String > unRegisteredEventNames = new ArrayList<>();



    private String uuid;


    @Override
    protected void onStart() {
        super.onStart();
        uuid=FirebaseAuth.getInstance().getUid();

    }

    @Override
    protected void onResume() {
        super.onResume();
        userEventList.clear();
        userParticipationDetails.clear();
        userEventListBuilder.clear();
        registeredEventNames.clear();
        unRegisteredEventNames.clear();

      //  fcmGeoLocation = intent.getStringExtra("mEventGeoLocation");
        referenceToEventDesc.addValueEventListener(eventDescEventListener);
        referenceToUserParticipation = FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation");
        referenceToUserParticipation.addValueEventListener(participationListener);
        //load user college name is navigation header
        new someBackgroundTask().execute();
        Log.d("FCMToken", "token "+ FirebaseInstanceId.getInstance().getToken());


        //background calculation thread



    }



    @Override
    protected void onStop() {
        super.onStop();
        referenceToEventDesc.removeEventListener(eventDescEventListener);
        referenceToUserParticipation.removeEventListener(participationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view2);
        View hView = navigationView.getHeaderView(0);
        userNameHeaderTextView = hView.findViewById(R.id.user_name_header);



        Intent intent = getIntent();
        uuid = intent.getStringExtra("UUID");
        displayName = intent.getStringExtra("userName");
        fcmTitle = intent.getStringExtra("mTitle");
        fcmText = intent.getStringExtra("mText");
        fcmEventName = intent.getStringExtra("mEventName");
        fcmVenue = intent.getStringExtra("mEventVenue");
        fcmLatitude = intent.getStringExtra("mLatitude");
        fcmLongitude = intent.getStringExtra("mLongitude");
        Log.d(TAG, "onCreate: before psspss" + fcmTitle + fcmText + fcmEventName + fcmLongitude + fcmLatitude);
        Log.d(TAG, "onCreate: " + " before psspss");
        if(fcmTitle!=null){
            Log.d(TAG, "onCreate: " + " inside psspss");
            createAlertDialogForFirebaseNotification();
        }

        //firebase messageing token
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                // send it to server
                int oneTimeID = (int) SystemClock.uptimeMillis();
                FirebaseDatabase.getInstance().getReference("Tokens").child(uuid).child(String.valueOf(oneTimeID)).setValue(token);

            }
        });
        //store this token in firebase with uuid as key


        // Intent intent = getIntent();
        //uuid = intent.getStringExtra("UUID");
        //displayName = intent.getStringExtra("userName");
        //referenceToUserParticipation = FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation");
        //referenceToUserParticipation.addValueEventListener(participationListener);



        createNotificationChannel();

        day0CompleteSchedule = EventScheduleStaticScheduleClass.getDay0EventSchedule();
        day1CompleteSchedule = EventScheduleStaticScheduleClass.getDay1EventSchedule();
        day2CompleteSchedule = EventScheduleStaticScheduleClass.getDay2EventSchedule();


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about_ignite, R.id.nav_sponsors,
                R.id.nav_sign_out, R.id.nav_contact_us, R.id.nav_feedback,R.id.nav_about_app)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

  /*  private void createAlertDialogForNotificationContent(String firebaseNotificationTitle, String firebaseNotificationContent) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(firebaseNotificationTitle);
        mBuilder.setIcon(R.drawable.ic_notification);
        mBuilder.setMessage(firebaseNotificationContent);
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
            }
        });

        AlertDialog m1 = mBuilder.create();
        m1.show();
    }*/

    private void createNotificationChannel() {
        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_name = "IGNITE Notification Channel";
            CharSequence name = channel_name;
            String channel_description = "IGNITE'19 Notification channel";
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Configure the notification channel.
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            channel.setSound(alarmSound,att);
            channel.enableLights(true);
            channel.enableLights(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            try{
                notificationManager.createNotificationChannel(channel);
            }
            catch (NullPointerException e){
                e.printStackTrace();
                Log.d(TAG, "createNotificationChannel: failed to create notification channel");
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
        if(FLAG1 == false){
            return null;
        }
        else
            return userEventList;
    }

    @Override
    public ArrayList<Participation> getUserParticipationDetails() {
        if(FLAG2 == false){
            return null;
        }
        else{
            return userParticipationDetails;
        }

    }

    @Override
    public ArrayList<String> getEventNames() {
        return eventNameList;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public ArrayList<String> getRegisteredEventNames() {
        return registeredEventNames;
    }

    @Override
    public ArrayList<String> getUnRegisteredEventNames() {
        return unRegisteredEventNames;
    }


    @Override
    public UserDetail getUserDetail() {
        return userDetail;
    }

    @Override
    public String getUserName() {
        return displayName;
    }

    @Override
    public Boolean getFirstListenerFlagStatus() {
        return FLAG1;
    }

    @Override
    public Boolean getSecondListenerFlagStatus() {
        return FLAG2;
    }


    private ValueEventListener participationListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                String key = snapshot.getKey();
                Participation p = snapshot.getValue(Participation.class);
                int value = 0;
                if (p != null) {
                    value = p.getParticipation();
                    if(value == 0){
                        unRegisteredEventNames.add(key);

                    }
                    else {
                        registeredEventNames.add(key);
                    }
                    userParticipationDetails.add(new Participation.Builder(value,key).build());
                }
                else{
                    Toasty.error(getApplicationContext(),"Failed to get participation Details",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onDataChange: Failed to get participation Details" );

                }
                FLAG2 = true;
                //if other user detail are available then add them in builder also
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ValueEventListener eventDescEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                UserDataAndEventList.Builder mEventList = snapshot.getValue(UserDataAndEventList.Builder.class);

                if (mEventList != null) {
                    switch (mEventList.getEvent_name()) {

                        case "Agomotto's Amet":
                            String desctexta="";
                            try  {
                                InputStream is = getAssets().open("Agomotto's Amet desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctexta=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctexta);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-X");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.agomotto_amet_ic);
                            mEventList.setEvent_image_uri(R.drawable.agomotto_amet_p2);
                            break;

                        case "Death's Head":
                            String desctextd="";
                            try  {
                                InputStream is = getAssets().open("Death's Head desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextd=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextd);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(5);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.deaths_head_ic);
                            mEventList.setEvent_image_uri(R.drawable.deaths_head_p2);
                            break;



                        case "Scattergories":
                            String desctexts="";
                            try  {
                                InputStream is = getAssets().open("Scattergories desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctexts=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctexts);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(4);
                            mEventList.setEvent_venue("LHC-S");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.scattergorries_ic);
                            mEventList.setEvent_image_uri(R.drawable.scattergories_p2);
                            break;


                        case "C for Codetta":
                            String desctextc="";
                            try  {
                                InputStream is = getAssets().open("C for Codetta desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextc=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextc);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.c_for_codetta_ic);
                            mEventList.setEvent_image_uri(R.drawable.c_for_codetta_p2);
                            break;

                        case "Captain Algorika":
                            String desctextca="";
                            try  {
                                InputStream is = getAssets().open("Captain Algorika desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextca=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextca);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(3);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.captain_algorika_ic);
                            mEventList.setEvent_image_uri(R.drawable.captain_algorika_p2);
                            break;
                        case "Cerebro":
                            String desctextce="";
                            try  {
                                InputStream is = getAssets().open("Cerebro desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextce=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextce);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.cerebro_ic);
                            mEventList.setEvent_image_uri(R.drawable.cerebro_p2);
                            break;
                        case "Code Infinity":
                            String desctextci="";
                            try  {
                                InputStream is = getAssets().open("Code Infinity desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextci=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextci);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.code_infinity_ic);
                            mEventList.setEvent_image_uri(R.drawable.code_infinity_p2);
                            break;

                        case "Dormammu's Loop":
                            String desctextdl="";
                            try  {
                                InputStream is = getAssets().open("Dormammu's Loop desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextdl=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextdl);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(3);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.dormammu_loop_ic);
                            mEventList.setEvent_image_uri(R.drawable.code_infinity_p2);
                            break;

                        case "Krypthon":
                            String desctextk="";
                            try  {
                                InputStream is = getAssets().open("Krypthon desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextk=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextk);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.krypthon_ic);
                            mEventList.setEvent_image_uri(R.drawable.krypthon_p2);
                            break;
                        case "Merc with the Mouth":
                            String desctextm="";
                            try  {
                                InputStream is = getAssets().open("Merc with the Mouth desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextm=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextm);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.merc_with_the_mouth);
                            mEventList.setEvent_image_uri(R.drawable.merc_with_the_mouthp2);
                            break;
                        case "ORM Master":
                            String desctexto="";
                            try  {
                                InputStream is = getAssets().open("ORM Master desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctexto=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctexto);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.orm_master_ic);
                            mEventList.setEvent_image_uri(R.drawable.orm_master_p2);
                            break;
                        case "Rage of Ultron":
                            String desctextr="";
                            try  {
                                InputStream is = getAssets().open("Rage of Ultron desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctextr=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctextr);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(3);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.rage_of_ultron_ic);
                            mEventList.setEvent_image_uri(R.drawable.rage_of_ultron_p2);
                            break;

                        case "The Search of Gauntlet":

                            String desctexttsg="";
                            try  {
                                InputStream is = getAssets().open("The Search of Gauntlet desc.TXT");
                                int size=is.available();
                                byte[] buffer=new byte[size];
                                is.read(buffer);
                                is.close();
                                desctexttsg=new String(buffer);
                            }
                            catch (IOException ex){
                                ex.printStackTrace();
                            }
                            mEventList.setEvent_description(desctexttsg);
                            mEventList.setEvent_latitude(13.009319);
                            mEventList.setEvent_longitude(74.794500);
                            mEventList.setNumber_of_participants(2);
                            mEventList.setEvent_venue("LHC-C");
                            mEventList.setEvent_duration("01:00:00");
                            mEventList.setEvent_icon_uri(R.drawable.search_of_the_gauntlet_ic);
                            mEventList.setEvent_image_uri(R.drawable.the_search_of_the_gaunt_p2);
                            break;



                        default:
                            Log.d(TAG, "onDataChange: some error has occured inside switch case of database listener");
                            break;
                    }
                    userEventList.add(mEventList.build());
                    eventNameList.add(mEventList.getEvent_name());
                    FLAG1 = true;


                }
                else{
                    Toasty.error(getApplicationContext(),"Failed to get event Description",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onDataChange: Failed to get event Description" );
                }

            }
            //pass this event list builder to further function to add more user related details
            //add these events to notification channel
            //configure notification channel changes

            /*workmanager notification are being removed because we can schedule notifications from firebase itself,
            its better to schedule them from firebase because that will give us independence from timing change issues

             */
            //configureNotifications(userEventList);

        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public ArrayList<UserDataAndEventList.Builder> getUserEventListBuilder() {
        return userEventListBuilder;
    }

    private void configureNotifications(ArrayList<UserDataAndEventList> userDataAndEventLists) {
        //event details
        String  eventName;
        String eventLatitude;
        String eventLongitude;
        String  eventDate;
        Data inputData;
        String eventVenue;
        //we set a tag to be able to cancel all work of this type if needed
        String workTag;

        long epochTime;

        WorkManager wm = WorkManager.getInstance(getApplicationContext());
        for(int i = 0; i < userDataAndEventLists.size(); ++i){
            eventName = userDataAndEventLists.get(i).getEvent_name();
            Log.d(TAG, "configureNotifications: props " + eventName);
            eventDate = userDataAndEventLists.get(i).getEvent_date();
            Log.d(TAG, "configureNotifications: props"  + eventDate);
            eventLatitude = Double.toString(userDataAndEventLists.get(i).getEvent_latitude());
            Log.d(TAG, "configureNotifications: props" + eventLatitude);
            eventLongitude = Double.toString(userDataAndEventLists.get(i).getEvent_longitude());
            Log.d(TAG, "configureNotifications: props" + eventLongitude);
            epochTime = DateTimeConverter.convertDateToEpoch(eventDate);
            eventVenue = userDataAndEventLists.get(i).getEvent_venue();
            workTag = eventName;

            //cancel previous work if already enqueued
            if(wm.getWorkInfosByTag(workTag) != null){
                //if previous work exists by same tag
                wm.cancelAllWorkByTag(workTag);
            }

            //decreasing 10 minutes worth of milliseconds
            epochTime = epochTime - 600000;

            long delay = epochTime - Calendar.getInstance().getTimeInMillis();
            //don't send notifications when event time is already passed
            if(delay <=0){
                continue;
            }

            Log.d(TAG, "configureNotifications: props " + delay);
            inputData = new Data.Builder().putString("eventName",eventName)
                    .putString("eventLatitude",eventLatitude)
                    .putString("eventLongitude",eventLongitude)
                    .putString("eventVenue",eventVenue)
                    .build();
            OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .addTag(workTag)
                    .build();


            wm.enqueue(notificationWork);
        }
    }


    public void onClickSignOut(MenuItem item) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Sign Out");
        builder1.setMessage("Are you sure? You won't be able to get event related updates.");
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








    private class someBackgroundTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            FirebaseDatabase.getInstance().getReference("Users").child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userDetail = dataSnapshot.getValue(UserDetail.class);
                    userNameHeaderTextView.startAnimation(MyAnimations.in(1000));
                    if(userDetail !=null) {
                        userNameHeaderTextView.setText(userDetail.getCollege_name());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }




    private void createAlertDialogForFirebaseNotification() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View alertView;
        alertView = inflater.inflate(R.layout.fcm_alert_dialog, null);
        fcmTextTextView = alertView.findViewById(R.id.fcm_title);
        fcmTitleTextView = alertView.findViewById(R.id.fcm_text);
        fcmEventNameTextView = alertView.findViewById(R.id.fcm_event_name);
        fcmEventVenueTextView = alertView.findViewById(R.id.fcm_event_venue);
        fcmEventParticipantsTextView = alertView.findViewById(R.id.fcm_participants);
        fcmTitleTextView.setText(fcmTitle);
        fcmTextTextView.setText(fcmText);
        fcmEventNameTextView.setText(fcmEventName);
        fcmEventVenueTextView.setText(fcmVenue);
        Log.d(TAG, "createAlertDialogForFirebaseNotification: " + "psspss" + fcmEventName + " " + fcmTitle + " " + fcmText + " " + fcmLatitude + fcmLongitude);




        //for fcm participants
        FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(fcmEventName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Participation p = dataSnapshot.getValue(Participation.class);
                if(p != null){
                    if(p.getParticipation() == 0){
                        //user have not registered for that event
                    }
                    else{
                        String p1 = null,p2 = null ,p3 = null ,p4 = null,p5= null;
                        if(p.getParticipant1() != null){
                            p1 = p.getParticipant1();
                        }
                        if(p.getParticipant2() != null){
                            p2 =  p.getParticipant2();
                        }
                        if(p.getParticipant3() != null){
                            p3 = p.getParticipant3();
                        }
                        if(p.getParticipant4() != null){
                            p4 = p.getParticipant4();
                        }
                        if(p.getParticipant5() != null){
                            p5 = p.getParticipant5();
                        }

                        if(p1 !=null && p2!=null && p3 != null && p4 != null && p5!=null){
                            String x1 = p1 + "\n" + p2 + "\n" + p3 + "\n" + p4 + "\n" + p5;
                            fcmEventParticipantsTextView.setText(x1);

                        }
                        else if(p1 !=null && p2!=null && p3 != null && p4 != null ){
                            String x2 = p1 + "\n" + p2 + "\n" + p3 + "\n" + p4;
                            fcmEventParticipantsTextView.setText(x2);
                        }
                        else if(p1 !=null && p2!=null && p3 != null ){
                            String x3 = p1 + "\n" + p2 + "\n" + p3 ;
                            fcmEventParticipantsTextView.setText(x3);
                        }
                        else if(p1 !=null && p2!=null ){
                            String x4 = p1 + "\n " + p2;
                            fcmEventParticipantsTextView.setText(x4);
                        }
                        else if(p1 !=null){
                            fcmEventParticipantsTextView.setText(p1);
                        }
                }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // AlertDialog builder = new AlertDialog.Builder(getApplicationContext()).setView(alertView);
   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setView(alertView);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("CANCEL", null);
        builder.setNeutralButton("DIRECTIONS",null);
        builder.setTitle("Notification");
        builder.setIcon(R.drawable.ic_notification);

        //final AlertDialog dialog = builder.create();
        alertDialog = builder.create();
        alertDialog.show();
        Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button neutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        neutral.setTextColor(getResources().getColor(R.color.materialGreen));
        positive.setTextColor(getResources().getColor(R.color.materialGreen));
        negative.setTextColor(getResources().getColor(R.color.materialGreen));

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myUri = String.format ("geo:%f,%f?q=%f,%f(%s)",Double.valueOf(fcmLatitude),Double.valueOf(fcmLongitude),Double.valueOf(fcmLatitude),Double.valueOf(fcmLongitude),"Mylocation");
                Uri gmmIntentUri = Uri.parse(myUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                alertDialog.dismiss();
            }
        });

    }

}
