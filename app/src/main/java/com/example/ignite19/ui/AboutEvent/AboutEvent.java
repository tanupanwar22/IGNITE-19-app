package com.example.ignite19.ui.AboutEvent;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ignite19.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutEvent extends Fragment {

    TextView event_name,event_desc,event_rules;
    View v;
    public AboutEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_about_event, container, false);
        event_name = (TextView)v.findViewById(R.id.event_name_about_event);
        event_desc=v.findViewById(R.id.event_desc_about_event);
        event_rules=v.findViewById(R.id.event_rules_about_event);

        event_name.setText(getArguments().getString("eventName"));
        event_desc.setText(getArguments().getString("eventdesc"));
        event_rules.setText(getArguments().getString("eventrules"));

        return v;
    }

}
