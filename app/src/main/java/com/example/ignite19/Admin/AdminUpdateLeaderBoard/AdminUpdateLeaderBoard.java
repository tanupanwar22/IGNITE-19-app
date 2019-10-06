



package com.example.ignite19.Admin.AdminUpdateLeaderBoard;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ignite19.Admin.AdminDataCommunication;
import com.example.ignite19.Admin.AdminHomeAcitivity;
import com.example.ignite19.Admin.NotificationSender;
import com.example.ignite19.Admin.NotificationSenderAdmin;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUpdateLeaderBoard extends Fragment implements View.OnClickListener {

    AdminDataCommunication dataCommunication;
    View v;
    RecyclerView recyclerView;
    ArrayList<String> college_names = new ArrayList<>();
    String eventName;
    EditText numberOfTeams;
    Button submitButton;
    DisplayCollegeNamesAdapter adapter;
    int numberOfCollegesToSelect;
    LottieAnimationView lottieAnimationView;
    HashMap<String,String> uuidList = new HashMap<>();


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

    public AdminUpdateLeaderBoard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("mList",college_names);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState!=null){
            college_names = savedInstanceState.getStringArrayList("mList");
        }
        else {
            college_names = dataCommunication.getCollegeNames();

        }
        uuidList = dataCommunication.getAllUUIDs();
        eventName = getArguments().getString("eventName");
        ((AdminHomeAcitivity)getActivity()).getSupportActionBar().setTitle(eventName);

        v = inflater.inflate(R.layout.fragment_admin_update_leader_board, container, false);
        submitButton = (Button)v.findViewById(R.id.button_submit_update_event);
        numberOfTeams = (EditText)v.findViewById(R.id.editText_numbers_to_select);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_admin_update_leader_board);
        submitButton.setOnClickListener(this);
        lottieAnimationView = v.findViewById(R.id.lottiell);
        lottieAnimationView.setVisibility(View.INVISIBLE);


        adapter = new DisplayCollegeNamesAdapter(getContext(),college_names,eventName);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.button_submit_update_event:
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                ArrayList<String > updatedCollegeList = new ArrayList<>();
                //this is a modified college list adjusted according to drag and drop
                updatedCollegeList = adapter.getCollege_name();

                ArrayList<String > tempList = new ArrayList<>();

                String numOfCOlleges = numberOfTeams.getText().toString();
                if(TextUtils.isEmpty(numOfCOlleges)){
                    Toasty.info(getContext(),"Enter number of colleges to select",Toast.LENGTH_LONG).show();
                }
                else{
                    numberOfCollegesToSelect = Integer.valueOf(numOfCOlleges);
                    if(updatedCollegeList.size() >= numberOfCollegesToSelect){
                        //lets work here
                        for(int i = 0 ; i < numberOfCollegesToSelect;++i){
                            tempList.add((i+1) + ".    " + updatedCollegeList.get(i));
                        }
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,tempList);
                        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                        builderSingle.setIcon(R.drawable.ic_update_leaderboard);
                        builderSingle.setTitle("Confirmation :");
                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                builderSingle.show();
                            }
                        });
                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        final ArrayList<String> finalUpdatedCollegeList = updatedCollegeList;
                        builderSingle.setPositiveButton("I confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //upload data to firebase
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LeaderBoards").child(eventName);
                                databaseReference.removeValue();
                                lottieAnimationView.setVisibility(View.VISIBLE);

                                for(int x = 0 ; x < numberOfCollegesToSelect; ++x){
                                    String key = databaseReference.push().getKey();
                                    LeaderBoardDataPOJO data = new LeaderBoardDataPOJO(x+1, finalUpdatedCollegeList.get(x));
                                    //for sending notification
                                    String topicName = finalUpdatedCollegeList.get(x).replaceAll(" ","_").toLowerCase();
                                    String uuid= uuidList.get(topicName);
                                    NotificationSender.uploadToFirebase(getContext(),"Congratulations","Your team has been shortlisted in: " + eventName + ". All the best for upcoming rounds.",topicName,uuid);
                                    databaseReference.child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                                Toasty.success(getContext(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                                Toasty.error(getContext(),"Try again",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                                //send shortlistedTeam details to admin
                                String collgeListString = null;
                                for(int ix = 0 ; ix < finalUpdatedCollegeList.size();++ix){
                                    collgeListString += (finalUpdatedCollegeList.get(ix) + "\n");
                                }
                                NotificationSenderAdmin.uploadToFirebase(getContext(),"Shortlist for " + eventName,collgeListString,"admin_xx","00");









                                Navigation.findNavController(view).navigate(R.id.action_adminUpdateLeaderBoard_to_adminHomeFragment);


                            }
                        });
                        builderSingle.show();
                    }
                    else{
                        Toasty.info(getContext(),"Check your input",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }




}
