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
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ignite19.Admin.AdminAddTeam.AdminRegisterTeam;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.LoginActivity;
import com.example.ignite19.MainActivity;
import com.example.ignite19.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment implements View.OnClickListener {


    private CardView addTeamButton,updateLeaderBoardButton,updateEventTimingButton,seeParticipantsButton,sendNotificationButton;
    AdminDataCommunication dataCommunication;
    ArrayList<String > eventList = new ArrayList<>();
    LottieAnimationView adminUpdateLeaderBoard,adminCreateTeam,adminUpdateEventTiming,adminSeeParticipants,adminSendNotification;
    ArrayList<String > selectedNames = new ArrayList<>();



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

        sendNotificationButton = view.findViewById(R.id.btn_admin_send_notification);
        adminSendNotification = view.findViewById(R.id.loader_admin_xx);

        sendNotificationButton.setOnClickListener(this);

        updateEventTimingButton = view.findViewById(R.id.button_admin_update_event_timing);
        addTeamButton.setOnClickListener(this);
        updateEventTimingButton.setOnClickListener(this);
        updateLeaderBoardButton.setOnClickListener(this);
        eventList = dataCommunication.getEventNames();

        adminCreateTeam = view.findViewById(R.id.lottie_admin_createTeam);
        adminSeeParticipants = view.findViewById(R.id.lottie_admin_see_participants);
        adminUpdateEventTiming = view.findViewById(R.id.lottie_admin_updateEventTime);
        adminUpdateLeaderBoard = view.findViewById(R.id.lottie_admin_updateleaderBoard);

        adminCreateTeam.setImageResource(R.drawable.ic_check);
        adminSeeParticipants.setAnimation("loader.json");
        adminUpdateLeaderBoard.setAnimation("loader.json");
        adminUpdateEventTiming.setAnimation("loader.json");

        adminSendNotification.setAnimation("loader.json");
        adminSendNotification.playAnimation();

        adminSeeParticipants.playAnimation();
        adminUpdateLeaderBoard.playAnimation();
        adminUpdateEventTiming.playAnimation();



        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!dataCommunication.getFlag1Status() || !dataCommunication.getFlag2Status());


                adminSeeParticipants.post(new Runnable() {
                    @Override
                    public void run() { adminSeeParticipants.setImageResource(R.drawable.ic_check);


                    }
                });



                adminUpdateLeaderBoard.post(new Runnable() {

                    @Override
                    public void run() {

                        adminUpdateLeaderBoard.setImageResource(R.drawable.ic_check);


                    }
                });
                adminSendNotification.post(new Runnable() {
                    @Override
                    public void run() {
                        adminSendNotification.setImageResource(R.drawable.ic_check);
                    }
                });



                adminUpdateEventTiming.post(new Runnable() {
                    @Override
                    public void run() {

                        adminUpdateEventTiming.setImageResource(R.drawable.ic_check);

                    }
                });


            }
        }).start();
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

                final AlertDialog.Builder mDialog = new AlertDialog.Builder(getContext());
                mDialog.setIcon(R.drawable.ic_signout);
                mDialog.setTitle("Add Team");
                mDialog.setMessage("You will get logged out in the process.");
                mDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), AdminRegisterTeam.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(intent);
                    }
                });
                mDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mDialog.show();
                break;
            case R.id.button_admin_update_event_timing:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setIcon(R.drawable.ic_update_leaderboard);
                mBuilder.setTitle("Select Event :");
                //loading animation can be implemented here
                if(eventList.size() != 0) {
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice,eventList);
                    mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    mBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            Bundle bundle = new Bundle();
                            bundle.putString("eventName",strName);
                            Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_adminUpdateEventTiming,bundle);
                        }
                    });
                    mBuilder.show();
                }
                else{
                    Toasty.info(getContext(),"list empty,wait for data to load",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_admin_update_leaderboard:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                builderSingle.setIcon(R.drawable.ic_update_leaderboard);
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
                Toasty.info(getContext(),"list empty,wait for data to load",Toast.LENGTH_LONG).show();
            }
                break;

            case R.id.see_participants_btn:
                AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(getContext());
                mBuilder2.setIcon(R.drawable.ic_participants);
                mBuilder2.setTitle("Select Event :");
                //loading animation can be implemented here
                if(eventList.size() != 0) {
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice,eventList);
                    mBuilder2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    mBuilder2.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            Bundle bundle = new Bundle();
                            bundle.putString("eventName",strName);
                            Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_seeParticipantsAdmin2,bundle);
                        }
                    });
                    mBuilder2.show();
                }
                else{
                    Toasty.info(getContext(),"list empty,wait for data to load",Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.btn_admin_send_notification:
                final ArrayList<String> college_names = dataCommunication.getCollegeNames();



                if(eventList.size() != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    String[] collegeNames = new String[college_names.size()];
                    final boolean[] checkedCollegeNames = new boolean[college_names.size()];
                    for(int i = 0 ; i < college_names.size();++i){
                        collegeNames[i]  = college_names.get(i);
                        checkedCollegeNames[i] = false;
                    }
                    builder.setMultiChoiceItems(collegeNames, checkedCollegeNames, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            // Update the current focused item's checked status
                             checkedCollegeNames[which] = isChecked;

                            // Get the current focused item
                            String currentItem = college_names.get(which);
                        }
                    });

                    // Specify the dialog is not cancelable
                    builder.setCancelable(false);

                    // Set a title for alert dialog
                    builder.setTitle("Select Institutes");
                    builder.setIcon(R.drawable.ic_notification);

                    // Set the positive/yes button click listener
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click positive button
                            //  tv.setText("Your preferred colors..... \n");
                            selectedNames.clear();
                          for (int i = 0; i < checkedCollegeNames.length; i++) {
                                boolean checked = checkedCollegeNames[i];
                                if (checked) {
                                    //  tv.setText(tv.getText() + colorsList.get(i) + "\n");
                                    selectedNames.add(college_names.get(i));
                                }
                            }
                          //lets go to next fragment with data
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("college_names",selectedNames);
                            Toasty.info(getContext(),selectedNames.size() + " Institutes selected",Toasty.LENGTH_LONG ).show();
                            Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_sendNotification,bundle);
                        }
                    });

                    // Set the negative/no button click listener
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click the negative button

                        }
                    });

                    // Set the neutral/cancel button click listener
                    builder.setNeutralButton("select All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when click the neutral button
                            Toasty.info(getContext(),"All Institutes selected " , Toasty.LENGTH_LONG).show();
                            selectedNames = college_names;
                            Bundle bundle2 = new Bundle();
                            bundle2.putStringArrayList("college_names",college_names);
                            Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_sendNotification,bundle2);


                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();
                  }
   //     });
    //}
                else{
                    Toasty.info(getContext(),"list empty,wait for data to load",Toast.LENGTH_LONG).show();
                }
                break;


            default:
                break;
        }
    }

}
