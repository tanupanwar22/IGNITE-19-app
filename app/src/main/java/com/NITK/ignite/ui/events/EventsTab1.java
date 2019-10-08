package com.NITK.ignite.ui.events;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.NITK.ignite.DataCommunication;
import com.NITK.ignite.R;
import com.NITK.ignite.UserDataAndEventList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsTab1 extends Fragment {

DataCommunication dataCommunication;
    View v;
    private RecyclerView recyclerView;
    private ArrayList<UserDataAndEventList> eventsArrayList = new ArrayList<>();

    public EventsTab1() {
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

        eventsArrayList = dataCommunication.getAllEventList();
        v = inflater.inflate(R.layout.fragment_events_tab1, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.events_tab1);
        EventsListAdapter adapter = new EventsListAdapter(getContext(),eventsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

}
