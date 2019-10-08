package com.NITK.ignite.ui.schedule;


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

import com.NITK.ignite.DataCommunication;
import com.NITK.ignite.R;

import java.util.ArrayList;

public class day2 extends Fragment {

    private DataCommunication dataCommunication;
    private ArrayList<eventSchedule> day2CompleteSchedule = new ArrayList<>();
    private RecyclerView recyclerView;

    View v;
    public day2() {
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
        day2CompleteSchedule = dataCommunication.getDay2CompleteSchedule();
        v=inflater.inflate(R.layout.day2,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.day2_recycleview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),day2CompleteSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }
}
