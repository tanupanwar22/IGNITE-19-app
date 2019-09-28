package com.example.ignite19.Admin.SeeParticipants;


import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ignite19.Admin.AdminHomeAcitivity;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeParticipantsAdmin2 extends Fragment {

    private static final String TAG = "zoono";
    AllParticipantsAdapter allParticipantsAdapter;
    String eventName;
    View v;
    HashMap<String,String > map = new HashMap<>();
    RecyclerView recyclerView;
    //TextView eventNameTextView;
    ArrayList<EventWiseParticipantDetail > mList = new ArrayList<>();


    public SeeParticipantsAdmin2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_see_participants_admin2, container, false);
        //eventNameTextView= v.findViewById(R.id.event_namexxx);
        recyclerView = v.findViewById(R.id.recyclerViewxxx);
        eventName = getArguments().getString("eventName").toString();
        //eventNameTextView.setText(eventName);
        ((AdminHomeAcitivity)getActivity()).getSupportActionBar().setTitle(eventName);

         allParticipantsAdapter = new AllParticipantsAdapter(getContext(),mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(allParticipantsAdapter);
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    map.put(snapshot.getKey(),snapshot.child("college_name").getValue().toString());
                    Log.d(TAG, "onDataChange: " + snapshot.getKey() + " " + snapshot.child("college_name").getValue());
                }
                secondListenerForValues(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

    private void secondListenerForValues(final HashMap<String, String > map) {
        final int mapSize = map.size();
        for(final String uuid : map.keySet()){
            Log.d("juliet", "secondListenerForValues: " + uuid);
            FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(eventName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String collegeName = map.get(uuid);
                    Participation mParticipation = dataSnapshot.getValue(Participation.class);
                    EventWiseParticipantDetail.Builder mBuilder = new EventWiseParticipantDetail.Builder(uuid,collegeName);
                    if(mParticipation.getParticipation() == 1){
                        Log.d(TAG, "onDataChange: " + mParticipation.getEvent_name());
                        Log.d(TAG, "onDataChange: " + mParticipation.getParticipant1());
                            mBuilder.setEventName(mParticipation.getEvent_name());
                            if(mParticipation.getParticipant1()!=null){
                                mBuilder.setParticipant1(mParticipation.getParticipant1());
                            }
                            if(mParticipation.getParticipant2()!= null){
                                mBuilder.setParticipant2(mParticipation.getParticipant2());
                            }
                            if(mParticipation.getParticipant3() != null){
                                mBuilder.setParticipant3(mParticipation.getParticipant3());
                            }
                            if(mParticipation.getParticipant4() != null){
                                mBuilder.setParticipant4(mParticipation.getParticipant4());
                            }
                            if(mParticipation.getParticipant5() != null){
                                mBuilder.setParticipant5(mParticipation.getParticipant5());
                            }

                        }

                        EventWiseParticipantDetail eventWiseParticipantDetail = mBuilder.build();
                        mList.add(eventWiseParticipantDetail);
                        allParticipantsAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


}
