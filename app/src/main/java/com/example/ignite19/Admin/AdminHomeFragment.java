package com.example.ignite19.Admin;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.ignite19.Admin.AdminAddTeam.AdminRegisterTeam;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.LoginActivity;
import com.example.ignite19.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment implements View.OnClickListener {


    private CardView addTeamButton,updateLeaderBoardButton,updateEventTimingButton,signout,seeParticipantsButton;
    AdminDataCommunication dataCommunication;
    ArrayList<String > eventList = new ArrayList<>();



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

    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTeamButton = view.findViewById(R.id.button_admin_add_team);
        updateLeaderBoardButton = view.findViewById(R.id.button_admin_update_leaderboard);
        seeParticipantsButton = view.findViewById(R.id.see_participants_btn);
        seeParticipantsButton.setOnClickListener(this);

        updateEventTimingButton = view.findViewById(R.id.button_admin_update_event_timing);
        addTeamButton.setOnClickListener(this);
        updateEventTimingButton.setOnClickListener(this);
        updateLeaderBoardButton.setOnClickListener(this);
        eventList = dataCommunication.getEventNames();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.button_admin_add_team:
                Intent i = new Intent(getActivity(), AdminRegisterTeam.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                FirebaseAuth.getInstance().signOut();
                startActivity(i);
                break;
            case R.id.button_admin_update_event_timing:
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_adminUpdateEventTiming);
                break;
            case R.id.button_admin_update_leaderboard:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                builderSingle.setIcon(R.drawable.ic_clock);
                builderSingle.setTitle("Select Event :");

                //loading animation can be implemented here
                if(eventList.size() != 0) {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice,eventList);
                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                               String strName = arrayAdapter.getItem(which);
                            Bundle bundle = new Bundle();
                            bundle.putString("eventName",strName);
                            Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_adminUpdateLeaderBoard,bundle);
                        }
                    });
                    builderSingle.show();
                }
                else{
                Toast.makeText(getContext(),"list empty,wait for data to load",Toast.LENGTH_LONG).show();
            }
                break;

            case R.id.see_participants_btn:
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_seeParticipantsAdmin);
                break;

            default:
                break;
        }
    }

}
