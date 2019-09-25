package com.example.ignite19.Admin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUpdateEventTiming extends Fragment {

int cnt=0;
    String[] enames=new String[21];
    AlertDialog.Builder builderSingle;
    String event_name;
    Button select_event;
    EditText edt;
    List<String> temp=new ArrayList<>();
    public AdminUpdateEventTiming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View v=inflater.inflate(R.layout.fragment_admin_update_event_timing, container, false);



        //builderSingle.setIcon(R.drawable.ic_launcher);




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
        edt=v.findViewById(R.id.update_time_edt);
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

}
