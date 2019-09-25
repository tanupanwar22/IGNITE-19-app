package com.example.ignite19.ui.schedule;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;

import java.util.ArrayList;

public class day1 extends Fragment {
    private DataCommunication dataCommunication;
    private RecyclerView recyclerView;
    private ArrayList<eventSchedule> day1CompleteSchedule = new ArrayList<>();
  View v;
    public day1() {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        day1CompleteSchedule = dataCommunication.getDay1CompleteSchedule();
        v =inflater.inflate(R.layout.day1,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.day1_recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),day1CompleteSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }
}
