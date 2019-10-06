package com.example.ignite19.ui.eventRegistration;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import es.dmoral.toasty.Toasty;
import static androidx.constraintlayout.widget.Constraints.TAG;
public class FragmentEventRegistration extends Fragment {
    DataCommunication dataCommunication;
    FloatingActionButton floatingActionButton;
    TextView eventDescTextView;
    RegisterEventsAdapter registerEventsAdapter;
    View alertView;
    TextView numberOfParticipantsTextView;
    ArrayList<Participation> registeredList = new ArrayList<>();
    AlertDialog alertDialog;
    CheckBox one,two,three,four,five;
    String uuid;
    UserDetail userDetail;
    RecyclerView recyclerView;
    ArrayList<Participation> notRegisteredList = new ArrayList<>();
   ArrayList<String> eventNamesForDropDown = new ArrayList<>();
   LottieAnimationView lottieAnimationView;
    View view;
    public static FragmentEventRegistration newInstance() {
        return new FragmentEventRegistration();
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
        outState.putParcelable("userDetail",userDetail);
        outState.putString("uuid",uuid);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_event_registration, container, false);
            if(savedInstanceState!=null){
                userDetail = savedInstanceState.getParcelable("userDetail");
                uuid = savedInstanceState.getString("uuid");
            }
            else {
                userDetail = dataCommunication.getUserDetail();
                uuid = dataCommunication.getUUID();
            }
            recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view_registered_events);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            registerEventsAdapter = new RegisterEventsAdapter(getContext(),registeredList);
             recyclerView.setAdapter(registerEventsAdapter);
            floatingActionButton = view.findViewById(R.id.floatingActionButton);
            lottieAnimationView = view.findViewById(R.id.lottie_event_reg);
            lottieAnimationView.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogFragment();
            }
        });

            //without these, items will keep getting multiplied by two
            registeredList.clear();
            registerEventsAdapter.notifyDataSetChanged();
            //
            FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        Participation.Builder p = snapshot.getValue(Participation.Builder.class);
                        if (p.getEvent_name() != null && p.getParticipation() == 1) {
                            switch (p.getEvent_name()) {

                                case "Agomotto's Amet":
                                    p.setEventIconURI(R.drawable.agomotto_amet_ic);
                                    break;

                                case "Death's Head":
                                    p.setEventIconURI(R.drawable.deaths_head_ic);

                                    break;

                                case  "Scattegories":
                                    p.setEventIconURI(R.drawable.scattergorries_ic);
                                    break;


                                case "C for Codetta":
                                    p.setEventIconURI(R.drawable.c_for_codetta_ic);
                                    break;

                                case "Captain Algorika":
                                    p.setEventIconURI(R.drawable.captain_algorika_ic);
                                    break;
                                case "Cerebro":
                                    p.setEventIconURI(R.drawable.cerebro_ic);
                                    break;
                                case "Code Infinity":
                                    p.setEventIconURI(R.drawable.code_infinity_ic);
                                    break;

                                case "Dormammu's Loop":
                                    p.setEventIconURI(R.drawable.dormammu_loop_ic);
                                    break;

                                case "Krypthon":
                                    p.setEventIconURI(R.drawable.krypthon_ic);
                                    break;
                                case "Merc with the Mouth":
                                    p.setEventIconURI(R.drawable.merc_with_the_mouth);
                                    break;
                                case "ORM Master":
                                    p.setEventIconURI(R.drawable.orm_master_ic);
                                    break;
                                case "Rage of Ultron":
                                    p.setEventIconURI(R.drawable.rage_of_ultron_ic);
                                    break;

                                case "The Search of Gauntlet":
                                    p.setEventIconURI(R.drawable.search_of_the_gauntlet_ic);
                                    break;


                                case "Nexus":
                                    p.setEventIconURI(R.drawable.nexus_poster_ic2);
                                    break;


                                case "End Game":
                                    p.setEventIconURI(R.drawable.endgame_icon);
                                    break;

                                case "The Last Crusade":
                                    p.setEventIconURI(R.drawable.the_last_crusade_ic);
                                    break;



                                default:
                                    Log.d(TAG, "onDataChange: some error has occured inside switch case of database listener");
                                    break;
                            }
                            registeredList.add(0,p.build());
                            registerEventsAdapter.notifyItemInserted(0);

                        }
                        else{
                           // Toasty.error(getContext(),"Failed to get event Description",Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onDataChange: Failed to get event Description" );
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        return view;
    }

    private void openDialogFragment() {
        //remove all items from not registered list
        notRegisteredList.clear();
        eventNamesForDropDown.clear();
        FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        Participation.Builder p = snapshot.getValue(Participation.Builder.class);
                        if (p.getEvent_name() != null && p.getParticipation() == 0) {
                            eventNamesForDropDown.add(p.getEvent_name());
                            switch (p.getEvent_name()) {

                                case "Agomotto's Amet":
                                    String desctexta="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Agomotto's Amet desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexta=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexta);
                                    p.setNumberOfParticipants(2);
                                    p.setEventIconURI(R.drawable.agomotto_amet_ic);

                                    break;

                                case "Death's Head":
                                    String desctextd="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Death's Head desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextd=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextd);
                                    p.setNumberOfParticipants(5);
                                    p.setEventIconURI(R.drawable.deaths_head_ic);
                                    break;



                                case "Scattegories":
                                    String desctexts="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Scattegories desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexts=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexts);
                                    p.setNumberOfParticipants(4);
                                    p.setEventIconURI(R.drawable.scattergorries_ic);
                                    break;


                                case "C for Codetta":
                                    String desctextc="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("C for Codetta desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextc=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextc);
                                    p.setNumberOfParticipants(2);
                                    p.setEventIconURI(R.drawable.c_for_codetta_ic);
                                    break;

                                case "Captain Algorika":
                                    String desctextca="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Captain Algorika desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextca=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextca);
                                    p.setNumberOfParticipants(2);
                                    p.setEventIconURI(R.drawable.captain_algorika_ic);
                                    break;
                                case "Cerebro":
                                    String desctextce="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Cerebro desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextce=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextce);
                                    p.setNumberOfParticipants(2);
                                    p.setEventIconURI(R.drawable.cerebro_ic);
                                    break;
                                case "Code Infinity":
                                    String desctextci="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Code Infinity desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextci=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextci);
                                    p.setNumberOfParticipants(3);
                                    p.setEventIconURI(R.drawable.code_infinity_ic);
                                    break;

                                case "Dormammu's Loop":
                                    String desctextdl="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Dormammu's Loop desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextdl=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextdl);
                                    p.setNumberOfParticipants(3);
                                    p.setEventIconURI(R.drawable.dormammu_loop_ic);
                                    break;

                                case "Krypthon":
                                    String desctextk="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Krypthon desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextk=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextk);

                                    p.setNumberOfParticipants(5);
                                    p.setEventIconURI(R.drawable.krypthon_ic);
                                    break;
                                case "Merc with the Mouth":
                                    String desctextm="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Merc with the Mouth desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextm=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextm);

                                   p.setNumberOfParticipants(2);

                                    p.setEventIconURI(R.drawable.merc_with_the_mouth);
                                    break;
                                case "ORM Master":
                                    String desctexto="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("ORM Master desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexto=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexto);

                                    p.setNumberOfParticipants(2);
                                    p.setEventIconURI(R.drawable.orm_master_ic);
                                    break;
                                case "Rage of Ultron":
                                    String desctextr="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Rage of Ultron desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctextr=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctextr);

                                    p.setNumberOfParticipants(3);
                                    p.setEventIconURI(R.drawable.rage_of_ultron_ic);
                                    break;

                                case "The Search of Gauntlet":

                                    String desctexttsg="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("The Search of Gauntlet desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexttsg=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexttsg);

                                    p.setNumberOfParticipants(4);
                                    p.setEventIconURI(R.drawable.search_of_the_gauntlet_ic);
                                    break;


                                case "Nexus":
                                    String desctexttsgx1="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("Nexus desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexttsgx1=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexttsgx1);
                                    p.setNumberOfParticipants(5);
                                    p.setEventIconURI(R.drawable.nexus_poster_ic2);
                                    break;


                                case "End Game":
                                    String desctexttsgx12="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("End Game desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexttsgx12=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexttsgx12);
                                    p.setNumberOfParticipants(1);
                                    p.setEventIconURI(R.drawable.endgame_icon);
                                    break;


                                case "The Last Crusade":
                                    String desctexttsgx123="";
                                    try  {
                                        InputStream is = getActivity().getAssets().open("The Last Crusade Desc.TXT");
                                        int size=is.available();
                                        byte[] buffer=new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        desctexttsgx123=new String(buffer);
                                    }
                                    catch (IOException ex){
                                        ex.printStackTrace();
                                    }
                                    p.setEventDesc(desctexttsgx123);
                                    p.setNumberOfParticipants(5);
                                    p.setEventIconURI(R.drawable.the_last_crusade_ic);
                                    break;


                                default:
                                    Log.d(TAG, "onDataChange: some error has occured inside switch case of database listener");
                                    break;
                            }
                            notRegisteredList.add(0,p.build());

                        }
                        else{
                          Log.d(TAG, "onDataChange: Failed to get event Description" );
                        }
                    }

                   afterFirebase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void afterFirebase(){

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        alertView = inflater.inflate(R.layout.dialog_register_event, null);
        eventDescTextView = alertView.findViewById(R.id.event_desc_text_view_in_dialog);
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
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String event_name_x = eventNamesForDropDown.get(i);
                for(Participation m : notRegisteredList){
                    if(m.getEvent_name().equalsIgnoreCase(event_name_x)){
                        eventDescTextView.setText(m.getEventDesc());
                        numberOfParticipantsTextView.setText(String.valueOf(m.getNumberOfParticipants()));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(alertView);
        builder.setPositiveButton("ADD", null);
        builder.setNegativeButton("CANCEL", null);
        builder.setTitle("Event Registration");
        builder.setIcon(R.drawable.ic_eventssvg);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button c = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        c.setTextColor(getResources().getColor(R.color.materialGreen));
        b.setTextColor(getResources().getColor(R.color.materialGreen));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mSpinner.getSelectedItem() == null){
                    Toasty.error(getContext(),"you have already registered in all the events",Toasty.LENGTH_SHORT).show();
                }
                else{
                    lottieAnimationView.setVisibility(View.VISIBLE);

                    String event_name =   mSpinner.getSelectedItem().toString();
                    int xnumberOfParticipants = Integer.valueOf(numberOfParticipantsTextView.getText().toString());
                    int count = findCheckedItems();
                    if(count == xnumberOfParticipants){
                        //great
                        addSelectedItemsToFirebase(event_name,xnumberOfParticipants);

                        eventNamesForDropDown.remove(event_name);
                        adapter.notifyDataSetChanged();

                    }
                    else{
                        lottieAnimationView.setVisibility(View.INVISIBLE);

                        Toasty.info(getContext(), "Please select correct number of participants", Toast.LENGTH_SHORT).show();
                    }
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

       for(Participation m : notRegisteredList)
       {
           if(event_name.equalsIgnoreCase(m.getEvent_name())){
               registeredList.add(m);
           }
       }

        //now store participation in database
        FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(event_name).setValue(participation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toasty.success(getContext(),"Registered successfully",Toast.LENGTH_LONG).show();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
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