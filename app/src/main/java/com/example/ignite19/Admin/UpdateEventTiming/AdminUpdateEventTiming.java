package com.example.ignite19.Admin.UpdateEventTiming;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignite19.Admin.AdminDataCommunication;
import com.example.ignite19.Admin.AdminHomeAcitivity;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.DateTimeConverter;
import com.example.ignite19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminUpdateEventTiming extends Fragment implements View.OnClickListener {

    int cnt = 0;
    String[] enames = new String[21];
    AlertDialog.Builder builderSingle;
    AdminDataCommunication dataCommunication;

    Button updateTiming;
    String event_date;
    TextView eventNameTextView, eventTimeTextView;
    HashMap<String, String> eventNameWithDate = new HashMap<>();
    String event_date_time;

    String event_name;
    EditText edt;
    List<String> temp = new ArrayList<>();


    public AdminUpdateEventTiming() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (AdminDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("eventList", eventNameWithDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            eventNameWithDate = (HashMap<String, String>) savedInstanceState.get("eventList");
        } else {

            eventNameWithDate = dataCommunication.getEventWithDateAndTime();
        }
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_admin_update_event_timing, container, false);
        eventNameTextView = v.findViewById(R.id.textView8_event_name);
        eventTimeTextView = v.findViewById(R.id.textView8_event_time);
        updateTiming = v.findViewById(R.id.admin_update_time_btn);
        event_name = getArguments().getString("eventName");
        event_date_time = eventNameWithDate.get(event_name);
        event_date = DateTimeConverter.convertDateTimeToDate(event_date_time);
        eventNameTextView.setText(event_name);
        eventTimeTextView.setText(event_date_time);
        edt = v.findViewById(R.id.update_time_edt);
        updateTiming.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.admin_update_time_btn:
                String newTime  = edt.getText().toString();
                if(TextUtils.isEmpty(newTime) || !checkTimeFormat(newTime)){
                    Toast.makeText(getContext(),"Please enter time properly",Toast.LENGTH_LONG).show();
                }
            else {
                    String temp = newTime;
                    newTime = event_date + " " + temp + ":00";
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("EventDesc").child(event_name);
                    databaseReference.child("event_date").setValue(newTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(getContext(),"Time updated Successfully",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(),"Time update failed,try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
        }
    }

    private boolean checkTimeFormat(String newTime) {

        String regex = "^[0-2][0-9]:[0-5][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)newTime);
        return matcher.matches();
    }
}


        /*
        select_event=v.findViewById(R.id.admin_update_event_select_event);
        select_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("EventDesc");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                            temp.add(d.getKey());
                            Toast.makeText(v.getContext(),"",Toast.LENGTH_LONG);
                            cnt++;
                            if(cnt==21)
                                call(v);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    Button update=v.findViewById(R.id.admin_update_time_btn);
    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String time=edt.getText().toString();
            if(time.length()==5) {
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("EventDesc").child(event_name);
                Map<String, String> newpost = new HashMap<>();
                newpost.put("event_name", event_name);
                newpost.put("event_date",time );
                reference1.setValue(newpost).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(v.getContext(),"Time Updated",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(v.getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
            else{
                Toast.makeText(v.getContext(),"Enter in 24hr HH:MM format",Toast.LENGTH_LONG).show();
            }
        }
    });




    return v;
    }

    public void call(View v)
    {AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Select Event");
        for(int i=0;i<temp.size();i++)
            enames[i]=temp.get(i);

// add a list
        builder.setItems(enames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select_event.setText(enames[which]);
                event_name=enames[which];
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
*/

