package com.example.ignite19.ui.events;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsTab0 extends Fragment {


    View v;
    private RecyclerView recyclerView;
    private ArrayList<UserDataAndEventList> eventsArrayList = new ArrayList<>();
    DataCommunication dataCommunication;

    public EventsTab0() {
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
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventsArrayList = dataCommunication.getAllEventList();
        HashSet hs = new HashSet();
        hs.addAll(eventsArrayList);
        eventsArrayList.clear();
        eventsArrayList.addAll(hs);
        v = inflater.inflate(R.layout.fragment_events_tab0, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.events_tab0);
        EventsListAdapter adapter = new EventsListAdapter(getContext(),eventsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

}
