package com.example.ignite19.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.MainActivity;
import com.example.ignite19.MyAnimations;
import com.example.ignite19.R;
import com.example.ignite19.UserDetail;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment implements View.OnClickListener {

    DataCommunication dataCommunication;
    Boolean flag1Status;
    Boolean flag2Status;
    LottieAnimationView eventLoader,eventRegistrationLoader,seeParticipantsLoader,leaderboardLoader;
    ArrayList<String> eventList;
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
        eventList = dataCommunication.getEventNames();


        eventLoader = view.findViewById(R.id.lottie_events_loader_animation);
        leaderboardLoader = view.findViewById(R.id.lottie_leaderboard);
        eventRegistrationLoader = view.findViewById(R.id.lottie_events_registration_loader_animation);
        seeParticipantsLoader = view.findViewById(R.id.lottie_see_participants_animation);
        eventLoader.setAnimation("loader.json");
        eventRegistrationLoader.setAnimation("loader.json");
        seeParticipantsLoader.setAnimation("loader.json");
        leaderboardLoader.setAnimation("loader.json");
        leaderboardLoader.playAnimation();
        eventLoader.playAnimation();
        eventRegistrationLoader.playAnimation();
        seeParticipantsLoader.playAnimation();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!dataCommunication.getFirstListenerFlagStatus() || !dataCommunication.getSecondListenerFlagStatus());


                eventLoader.post(new Runnable() {
                    @Override
                    public void run() { eventLoader.setAnimation("greentick.json");
                        eventLoader.playAnimation();

                    }
                });



                seeParticipantsLoader.post(new Runnable() {

                    @Override
                    public void run() {

                        seeParticipantsLoader.setAnimation("greentick.json");
                        seeParticipantsLoader.playAnimation();

                    }
                });



                eventRegistrationLoader.post(new Runnable() {
                    @Override
                    public void run() {

                        eventRegistrationLoader.setAnimation("greentick.json");
                        eventRegistrationLoader.playAnimation();
                    }
                });

                leaderboardLoader.post(new Runnable() {
                    @Override
                    public void run() {
                    leaderboardLoader.setAnimation("greentick.json");
                    leaderboardLoader.playAnimation();
                    }
                });
            }
        }).start();




        CardView eventsBtn = view.findViewById(R.id.button_events);
        CardView navigationBtn = view.findViewById(R.id.button_navigation);
        CardView eventRegistrationBtn = view.findViewById(R.id.button_event_registration);
        CardView leaderBoardBtn = view.findViewById(R.id.button_leaderboards);
        CardView scheduleBtn = view.findViewById(R.id.user_see_schedule);
        CardView seeTeam=view.findViewById(R.id.user_see_event_player_distribution);
        eventsBtn.setOnClickListener(this);
        scheduleBtn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
        leaderBoardBtn.setOnClickListener(this);
        eventRegistrationBtn.setOnClickListener(this);
        seeTeam.setOnClickListener(this);


    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.button_events:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_events);
                }
                else{
                    Toasty.info(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }

               // Navigation.findNavController(view).navigate(R.id.);
                break;
            case R.id.button_navigation:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_navigation);
                break;
            case R.id.button_leaderboards:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_fragmentLeaderBoard);
                }
                else{
                    Toasty.info(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_event_registration:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_event_registration);
                }
                else{
                    Toasty.info(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.user_see_event_player_distribution:
                flag1Status = dataCommunication.getFirstListenerFlagStatus();
                flag2Status = dataCommunication.getSecondListenerFlagStatus();
                if(flag1Status && flag2Status){
                    Navigation.findNavController(view).navigate(R.id.action_nav_home_to_seeParticipantsUser2);
                }
                else{
                    Toasty.info(getContext(),"Please wait for data to load",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.user_see_schedule:
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_schedule);
            default:
                break;
        }
    }

}