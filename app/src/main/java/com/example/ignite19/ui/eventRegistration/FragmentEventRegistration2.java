package com.example.ignite19.ui.eventRegistration;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.DataCommunication;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;
import com.example.ignite19.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class FragmentEventRegistration2 extends Fragment {

    DataCommunication dataCommunication;
    FloatingActionButton floatingActionButton;
    ArrayList<UserDataAndEventList> userDataAndEventLists;
    ArrayList<UserDataAndEventList> registeredEventList  = new ArrayList<>();
    ArrayList<Participation> participationArrayList;

    TextView eventDescTextView;
    RegisterEventsAdapter registerEventsAdapter;
    View alertView;
    TextView numberOfParticipantsTextView;
    AlertDialog alertDialog;
    CheckBox one,two,three,four,five;
    HashMap<String,Integer> map = new HashMap<>();
    String uuid;

    UserDetail userDetail;
    RecyclerView recyclerView;
    View view;
    List<String > eventNamesForDropDown;


    public static FragmentEventRegistration2 newInstance() {
        return new FragmentEventRegistration2();
    }

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList",userDataAndEventLists);
        outState.putParcelableArrayList("participationArrayList",participationArrayList);
        outState.putParcelable("userDetail",userDetail);
        outState.putString("uuid",uuid);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_registration, container, false);
        if(savedInstanceState!=null){
            userDataAndEventLists = savedInstanceState.getParcelableArrayList("mList");
            userDetail = savedInstanceState.getParcelable("userDetail");
            participationArrayList = savedInstanceState.getParcelableArrayList("participationArrayList");
            uuid = savedInstanceState.getString("uuid");
        }
        else {
            userDetail = dataCommunication.getUserDetail();
            userDataAndEventLists = dataCommunication.getAllEventList();
            participationArrayList = dataCommunication.getUserParticipationDetails();
            uuid = dataCommunication.getUUID();
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view_registered_events);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);


        registerEventsAdapter = new RegisterEventsAdapter(getContext(),registeredEventList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(registerEventsAdapter);

        for(Participation x : participationArrayList){
            for(UserDataAndEventList y: userDataAndEventLists){
                if(x.getEvent_name().equalsIgnoreCase(y.getEvent_name())){
                    if(x.getParticipation() == 0){
                        map.put(y.getEvent_name(),y.getNumber_of_participants());
                    }
                    else{
                        registeredEventList.add(y);
                            registerEventsAdapter.notifyItemInserted(0);
                       // registeredEventHashSet.add(y);
                        //  registeredEventList.add(y);

                    }
                }
            }
        }

        eventNamesForDropDown = new ArrayList<>(map.keySet());
        //  registerEventsAdapter.notifyDataSetChanged();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogFragment();
            }
        });

        return view;
    }



    private void openDialogFragment() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        alertView = inflater.inflate(R.layout.dialog_register_event, null);
        eventDescTextView = alertView.findViewById(R.id.event_desc_text_view_in_dialog);
        alertView.setBackgroundColor(getResources().getColor(R.color.igniteSecondary));

        one =  alertView.findViewById(R.id.checkbox1_team_participant);
        two =  alertView.findViewById(R.id.checkbox2_team_participant);
        three = alertView.findViewById(R.id.checkbox3_team_participant);
        four = alertView.findViewById(R.id.checkbox4_team_participant);
        five =  alertView.findViewById(R.id.checkbox5_team_participant);
        numberOfParticipantsTextView = (TextView) alertView.findViewById(R.id.textView4_number_of_participants);
        final Spinner mSpinner;
        mSpinner =  alertView.findViewById(R.id.spinner_unregistered_events);
        one.setText(userDetail.getTeam_leader());
        two.setText(userDetail.getTeam_member1());
        three.setText(userDetail.getTeam_member2());
        four.setText(userDetail.getTeam_member3());
        five.setText(userDetail.getTeam_member4());


        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(Objects.requireNonNull(getContext(),"context cannot be null here"),  android.R.layout.simple_spinner_dropdown_item,eventNamesForDropDown);
        adapter.setDropDownViewResource( android.R.layout.simple_list_item_checked);
        mSpinner.setAdapter(adapter);
        //mSpinner.setOnItemSelectedListener(mListener);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // String event_name_X = unRegisteredEvents.get(i).toString();
                String event_name_x = eventNamesForDropDown.get(i).toString();
                numberOfParticipantsTextView.setText(map.get(event_name_x).toString());
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GREEN);
                ((TextView)adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
                //
                String event_desc;
                for(UserDataAndEventList m : userDataAndEventLists){
                    if(m.getEvent_name().equalsIgnoreCase(event_name_x)){
                        eventDescTextView.setText(m.getEvent_description());
                        break;
                    }
                }
                // eventDescTextView.setText(map.get);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextView textView = new TextView(getContext());
        textView.setText("Event Registration");
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        //textView.drawabl
        // textView.setBackgroundColor(Color.CYAN);
        textView.setTextColor(getResources().getColor(R.color.contrasText));
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eventssvg, 0, 0, 0);



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyDialogTheme).setView(alertView);
        builder.setPositiveButton("ADD", null);
        builder.setNegativeButton("CANCEL", null);
        //builder.setTitle("Event Registration")
        builder.setCustomTitle(textView);

        //builder.setIcon(R.drawable.ic_eventssvg);
        alertDialog = builder.create();
        alertDialog.show();


        alertDialog.setCancelable(false);
        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button c = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        c.setTextColor(Color.GREEN);
        b.setTextColor(Color.GREEN);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name = mSpinner.getSelectedItem().toString();
                int xnumberOfParticipants = Integer.valueOf(numberOfParticipantsTextView.getText().toString());
                int count = findCheckedItems();
                if(count == xnumberOfParticipants){
                    //great
                    addSelectedItemsToFirebase(event_name,xnumberOfParticipants);
                    //map.remove(event_name);
                    //unRegisteredEvents.remove(event_name);
                    eventNamesForDropDown.remove(event_name);
                    adapter.notifyDataSetChanged();

                }
                else{
                    Toasty.info(getContext(), "Please select correct number of participants", Toast.LENGTH_SHORT).show();
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void addSelectedItemsToFirebase(String event_name,int numberOfParticipants) {
        ArrayList<String> selectedNames = new ArrayList<>();
        Participation.Builder p = new Participation.Builder(1,event_name);
        if(one.isChecked()){
            selectedNames.add(one.getText().toString());
        }
        if(two.isChecked())
            selectedNames.add(two.getText().toString());
        if(three.isChecked())
            selectedNames.add(three.getText().toString());
        if(four.isChecked())
            selectedNames.add(four.getText().toString());
        if(five.isChecked())
            selectedNames.add(five.getText().toString());

        if(numberOfParticipants == 1){
            p.setParticipant1(selectedNames.get(0));
        }
        if(numberOfParticipants == 2){
            p.setParticipant1(selectedNames.get(0));
            p.setParticipant2(selectedNames.get(1));
        }
        if(numberOfParticipants == 3){
            p.setParticipant1(selectedNames.get(0));
            p.setParticipant2(selectedNames.get(1));
            p.setParticipant3(selectedNames.get(2));
        }
        if(numberOfParticipants == 4){
            p.setParticipant1(selectedNames.get(0));
            p.setParticipant2(selectedNames.get(1));
            p.setParticipant3(selectedNames.get(2));
            p.setParticipant4(selectedNames.get(3));
        }
        if(numberOfParticipants == 5){
            p.setParticipant1(selectedNames.get(0));
            p.setParticipant2(selectedNames.get(1));
            p.setParticipant3(selectedNames.get(2));
            p.setParticipant4(selectedNames.get(3));
            p.setParticipant5(selectedNames.get(4));
        }
        Participation participation = p.build();


        for(UserDataAndEventList m:userDataAndEventLists){
            if(event_name.equalsIgnoreCase(m.getEvent_name())){
                registeredEventList.add(m);
                registerEventsAdapter.notifyItemInserted(0);
                //registeredEventHashSet.add(m);
            }
        }
        //now store participation in database
        FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(event_name).setValue(participation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toasty.success(getContext(),"Registered successfully",Toast.LENGTH_LONG).show();
                    registerEventsAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }
                else{
                    Toasty.error(getContext(),"failed to register, Try again",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private int findCheckedItems() {
        int count = 0;
        if(one.isChecked()){
            count++;
        }
        if(two.isChecked()){
            count++;

        }
        if(three.isChecked()){
            count++;
        }
        if(four.isChecked()){
            ++count;
        }
        if(five.isChecked()){
            ++count;
        }
        return count;
    }
}
