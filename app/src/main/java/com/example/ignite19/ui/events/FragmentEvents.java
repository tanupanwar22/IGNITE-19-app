package com.example.ignite19.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;
import com.example.ignite19.ui.schedule.eventSchedule;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;


public class FragmentEvents extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<UserDataAndEventList> eventsArrayList = new ArrayList<>();
    DataCommunication dataCommunication;
    View v;

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
    public static FragmentEvents newInstance() {
        return new FragmentEvents();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList",eventsArrayList);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        if(savedInstanceState != null){
            eventsArrayList = savedInstanceState.getParcelableArrayList("mList");
        }
        else{
            eventsArrayList = dataCommunication.getAllEventList();
            HashSet hs = new HashSet();
            hs.addAll(eventsArrayList);
            eventsArrayList.clear();
            eventsArrayList.addAll(hs);
        }

        v = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.events_tabx);
        EventsListAdapter adapter = new EventsListAdapter(getContext(),eventsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}