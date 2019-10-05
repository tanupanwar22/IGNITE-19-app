package com.example.ignite19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ignite19.ui.LeaderBoard.showTeamsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showResults extends AppCompatActivity {
    List<String> team_name=new ArrayList<>();
    int cnt=0;
    RecyclerView showResults;
    ImageView congo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        showResults=findViewById(R.id.selected_team_list_recyclerview2);
        Intent i=getIntent();
        String event_name=i.getStringExtra("event");
        showResults.setLayoutManager(new LinearLayoutManager(this));

        congo=findViewById(R.id.congo2);
        Glide.with(showResults.this).load(R.drawable.congo).into(congo);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("LeaderBoards").child(event_name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()) {
                    team_name.add(d.child("college_name").getValue().toString());
                    cnt++;
                    if(cnt==3)
                        call();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void call(){
        showResults.setAdapter(new showTeamsAdapter(team_name,showResults.this));
    }
}
