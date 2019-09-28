package com.example.ignite19.ui.AboutEvent;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ignite19.Admin.AdminHomeAcitivity;
import com.example.ignite19.DataCommunication;
import com.example.ignite19.DateTimeConverter;
import com.example.ignite19.MainActivity;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutEvent extends Fragment {
    DataCommunication dataCommunication;

    TextView eventDateTextView,eventVenueTextView;
    ImageView eventImageView;
    TextView eventDescriptionTextView,eventRulesTextView;
    ArrayList<UserDataAndEventList> userDataAndEventLists = new ArrayList<>();
    View v;
    String eventName = null;
    UserDataAndEventList dataForThisEvent;
    public AboutEvent() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments().getString("eventName")!=null){
            eventName = getArguments().getString("eventName");
        }

        String eventDesc = null;
        String eventRules = null;
        if(getArguments().getString("eventdesc") != null){
            eventDesc = getArguments().getString("eventdesc");
            eventRules = getArguments().getString("eventrules");
        }


        userDataAndEventLists = dataCommunication.getAllEventList();
        for(UserDataAndEventList m: userDataAndEventLists){
            if(eventName.equalsIgnoreCase(m.getEvent_name())){
                dataForThisEvent = m;
                break;
            }
        }
        v = inflater.inflate(R.layout.fragment_about_event, container, false);
        eventDescriptionTextView=v.findViewById(R.id.event_desc_about_event);
        eventRulesTextView=v.findViewById(R.id.event_rules_about_event);
        eventDateTextView = v.findViewById(R.id.about_page_date_time_textview);
        eventImageView = v.findViewById(R.id.about_page_image_view);
        eventVenueTextView = v.findViewById(R.id.about_page_venue_textview);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(eventName);

        eventDateTextView.setText(DateTimeConverter.changeDateFormat(dataForThisEvent.getEvent_date()));
        eventVenueTextView.setText(dataForThisEvent.getEvent_venue());
        eventImageView.setImageResource(dataForThisEvent.getEvent_image_uri());
        eventDescriptionTextView.setText(eventDesc);
        eventRulesTextView.setText(eventRules);
        return v;
    }

}
