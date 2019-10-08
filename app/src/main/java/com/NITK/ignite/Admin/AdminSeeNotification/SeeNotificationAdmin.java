package com.NITK.ignite.Admin.AdminSeeNotification;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.NITK.ignite.R;
import com.NITK.ignite.ui.Notifications.NotificationPOJO;
import com.NITK.ignite.ui.Notifications.NotificationUserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeNotificationAdmin extends Fragment {

        View v;
        NotificationUserAdapter adapter;
        RecyclerView recyclerView;

        ArrayList<NotificationPOJO> mList = new ArrayList<>();
        LottieAnimationView lottieAnimationView;

        public SeeNotificationAdmin() {
            // Required empty public constructor
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_see_notification_admin, container, false);
            recyclerView = v.findViewById(R.id.notification_recyclerview_admin);
            adapter = new NotificationUserAdapter(getContext(),mList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            lottieAnimationView = v.findViewById(R.id.not_loader_admin);
            lottieAnimationView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

            fetchDataFromFirebase();

            //fetch data here from firebase and load it into database then we can pursue it further
            return v;
        }

        private void fetchDataFromFirebase() {
            FirebaseDatabase.getInstance().getReference("Admin").child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        NotificationPOJO notificationPOJO = snapshot.getValue(NotificationPOJO.class);
                        String title = notificationPOJO.getTitle();
                        String text = notificationPOJO.getBody();
                        long timestamp = notificationPOJO.timeStamp();
                        mList.add(notificationPOJO);

                    }
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    if(mList.size() == 0){
                        mList.add(new NotificationPOJO("No notifications yet",null,null));
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    Collections.reverse(mList);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
