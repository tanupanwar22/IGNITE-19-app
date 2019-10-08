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

import com.NITK.ignite.R;
import com.NITK.ignite.DataCommunication;

import java.util.ArrayList;


public class day0 extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private ArrayList<eventSchedule> day0CompleteSchedule = new ArrayList<>();
    DataCommunication dataCommunication;

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
        day0CompleteSchedule = dataCommunication.getDay0CompleteSchedule();
        v=inflater.inflate(R.layout.day0,container,false);
       recyclerView = (RecyclerView)v.findViewById(R.id.day0_recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),day0CompleteSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }


}
