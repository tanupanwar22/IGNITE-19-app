package com.NITK.ignite.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.NITK.ignite.DataCommunication;
import com.NITK.ignite.R;
import com.NITK.ignite.UserDataAndEventList;

import java.util.ArrayList;


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

            eventsArrayList = dataCommunication.getAllEventList();

        v = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.events_tabx);
        EventsListAdapter adapter = new EventsListAdapter(getContext(),eventsArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}