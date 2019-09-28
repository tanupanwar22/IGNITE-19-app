package com.example.ignite19.ui;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignite19.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeParticipants extends Fragment {

    private Button eventbutton;
    DatabaseReference databaseReference;
    private  String strName;
    private Button  search;
    private int count=0;
    LinearLayout mLinear;
    HashMap<String,Integer> event_participant_count_map=new HashMap<>();
    List<String> list=new ArrayList<>();



    public SeeParticipants() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_participants, container, false);


        strName = "";
        eventbutton = view.findViewById(R.id.selecteventbuttonid);
        search=view.findViewById(R.id.searcheventbuttonid);
        mLinear=view.findViewById(R.id.ll);

        event_participant_count_map.put("Nexus",5);
        event_participant_count_map.put("Death's Head",2);
        event_participant_count_map.put("Agomotto's Amet",2);
        event_participant_count_map.put("Captain Algorika",3);
        event_participant_count_map.put("Krypthon",2);
        event_participant_count_map.put("Bidding",2);
        event_participant_count_map.put("Brain Storm",3);
        event_participant_count_map.put("C for Codetta",2);
        event_participant_count_map.put("Cerebro",2);
        event_participant_count_map.put("Code Infinity",2);
        event_participant_count_map.put("Debugging",3);
        event_participant_count_map.put("Dormammu's Loop",2);
        event_participant_count_map.put("Merc with the Mouth",2);
        event_participant_count_map.put("ORM Master",3);
        event_participant_count_map.put("Rage of Ultron",3);
        event_participant_count_map.put("Relay Coding",3);
        event_participant_count_map.put("Scattegories",2);
        event_participant_count_map.put("Strange's Bugs",2);
        event_participant_count_map.put("Tesseract Endeavour",3);
        event_participant_count_map.put("The Search of the Gauntlet",5);
        event_participant_count_map.put("Workshop",5);



        eventbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());

                builderSingle.setTitle("Select One Name:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Nexus");
                arrayAdapter.add("Death's Head");
                arrayAdapter.add("Agomotto's Amet");
                arrayAdapter.add("Captain Algorika");
                arrayAdapter.add("Krypthon");
                arrayAdapter.add("Bidding");
                arrayAdapter.add("Brain Storm");
                arrayAdapter.add("C for Codetta");
                arrayAdapter.add("Cerebro");
                arrayAdapter.add("Code Infinity");
                arrayAdapter.add("Debugging");
                arrayAdapter.add("Dormammu's Loop");
                arrayAdapter.add("Merc with the Mouth");
                arrayAdapter.add("ORM Master");
                arrayAdapter.add("Rage of Ultron");
                arrayAdapter.add("Relay Coding");
                arrayAdapter.add("Scattegories");
                arrayAdapter.add("Strange's Bugs");
                arrayAdapter.add("Tesseract Endeavour");
                arrayAdapter.add("The Search of the Gauntlet");
                arrayAdapter.add("Workshop");


                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }


                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        strName = arrayAdapter.getItem(which);
                        eventbutton.setText(strName);
                    }
                });
                builderSingle.show();
            }

        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinear.removeAllViews();
                list.clear();
                search(view);
            }
        });


        return  view;
    }



    public void print(View view)
    {
        final int N = list.size(); // total number of textviews to add
        int count2=0;
        final TextView[] myTextViews = new TextView[N]; // create an empty array;

        for (int i = 0; i < N; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(getContext());

            // set some properties of rowTextView or something
            rowTextView.setText("\t\t\t\t\t"+String.valueOf(i+1)+"\t\t\t"+list.get(i));
            rowTextView.setTextSize(25);
            // add the textview to the linearlayout
            mLinear.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
    }
    public void search(final View view)
    {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("participation").child(strName);
        //list.add(strName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {

                    if(!d.getKey().equals("event_name")&&!d.getKey().equals("participation"))
                    {
                        list.add(d.getValue().toString());
                        count++;
                        if(count==event_participant_count_map.get(strName))
                        {
                            print(view);
                            count=0;
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // print();
    }





}
