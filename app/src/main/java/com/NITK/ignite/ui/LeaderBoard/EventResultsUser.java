package com.NITK.ignite.ui.LeaderBoard;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.NITK.ignite.MainActivity;
import com.NITK.ignite.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventResultsUser extends Fragment {

    List<String> team_name=new ArrayList<>();
    int cnt=0;
    RecyclerView showResults;
    ImageView congo;
    View v;
    LottieAnimationView lottieAnimationView;
    public EventResultsUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_event_results_user, container, false);
        showResults=v.findViewById(R.id.selected_team_list_recyclerview2);
        lottieAnimationView = v.findViewById(R.id.mlottie2);
        lottieAnimationView.setVisibility(View.GONE);

        //Intent i= getIntent();
        //String event_name=i.getStringExtra("event");
        String event_name = getArguments().get("event").toString();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(event_name);
        showResults.setLayoutManager(new LinearLayoutManager(getContext()));

        congo=v.findViewById(R.id.congo2);
        Glide.with(getContext()).load(R.drawable.congo).into(congo);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("LeaderBoards").child(event_name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                for(DataSnapshot d:dataSnapshot.getChildren()) {
                    team_name.add(d.child("college_name").getValue().toString());

                }
                if(team_name.size() == 0){

                    ArrayList<String> mList = new ArrayList<>();
                    mList.add("No data available yet");
                    lottieAnimationView.setVisibility(View.GONE);
                    showResults.setAdapter(new showTeamsAdapter(mList,getContext()));
                }
                else {
                    call();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    public void call(){
        lottieAnimationView.setVisibility(View.GONE);
        showResults.setAdapter(new showTeamsAdapter(team_name,getContext()));
    }
}
