package com.example.ignite19.ui.SeeParticipants;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ignite19.Admin.SeeParticipants.AllParticipantsAdapter;
import com.example.ignite19.Admin.SeeParticipants.EventWiseParticipantDetail;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.DataCollectionConfigStorage;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeParticipantsUser2 extends Fragment {


    RecyclerView recyclerView;
    AllParticipantsAdapter mAdapter;
    ArrayList<EventWiseParticipantDetail> eventWiseParticipantDetailArrayList = new ArrayList<>();

    String college_name;
    View v;
    String uuid;
    DataCommunication dataCommunication;
    NoInternetDialog noInternetDialog;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (DataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    public SeeParticipantsUser2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        noInternetDialog= new NoInternetDialog.Builder(getContext()).build();
        // Inflate the layout for this fragment
        uuid = dataCommunication.getUUID();
        v = inflater.inflate(R.layout.fragment_see_participants_user2, container, false);
        recyclerView = v.findViewById(R.id.see_paricipants_recycler_view_user);

        mAdapter = new AllParticipantsAdapter(getContext(),eventWiseParticipantDetailArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(mAdapter);

        FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Participation mParticipation = snapshot.getValue(Participation.class);
                    EventWiseParticipantDetail.Builder mBuilder = new EventWiseParticipantDetail.Builder(uuid,mParticipation.getEvent_name());
                    mBuilder.setEventName(mParticipation.getEvent_name());

                    if(mParticipation.getParticipation() == 1){
                        if(mParticipation.getParticipant1() != null){
                            mBuilder.setParticipant1(mParticipation.getParticipant1());
                        }
                        if(mParticipation.getParticipant2() != null){
                            mBuilder.setParticipant2(mParticipation.getParticipant2());
                        }
                        if(mParticipation.getParticipant3() != null){
                            mBuilder.setParticipant3(mParticipation.getParticipant3());
                        }
                        if (mParticipation.getParticipant4() != null){
                            mBuilder.setParticipant4(mParticipation.getParticipant4());
                        }
                        if (mParticipation.getParticipant5() != null){
                            mBuilder.setParticipant5(mParticipation.getParticipant5());
                        }
                    }
                    EventWiseParticipantDetail mDetail = mBuilder.build();
                    eventWiseParticipantDetailArrayList.add(mDetail);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}
