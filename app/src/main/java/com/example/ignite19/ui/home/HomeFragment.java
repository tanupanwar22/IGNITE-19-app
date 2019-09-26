package com.example.ignite19.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    DataCommunication dataCommunication;
    Boolean flag1Status;
    Boolean flag2Status;
    ImageView bg_gif;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //navController = Navigation.findNavController(view);

        Button eventsBtn = view.findViewById(R.id.button_events);
        Button navigationBtn = view.findViewById(R.id.button_navigation);
        Button eventRegistrationBtn = view.findViewById(R.id.button_event_registration);
        Button leaderBoardBtn = view.findViewById(R.id.button_leaderboards);
        Button seeTeam=view.findViewById(R.id.user_see_event_player_distribution);
        eventsBtn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
        leaderBoardBtn.setOnClickListener(this);
        eventRegistrationBtn.setOnClickListener(this);
        seeTeam.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_events:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_events);
                }
                else{
                    Toast.makeText(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }

               // Navigation.findNavController(view).navigate(R.id.);
                break;
            case R.id.button_navigation:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_navigation);
                break;
            case R.id.button_leaderboards:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_fragmentLeaderBoard);
                break;
            case R.id.button_event_registration:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_event_registration);
                }
                else{
                    Toast.makeText(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.user_see_event_player_distribution:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_seeParticipants);
                break;
            default:
                break;
        }
    }
}