package com.NITK.ignite.Admin.SeeLeaderBoardAdmin;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.NITK.ignite.Admin.AdminDataCommunication;
import com.NITK.ignite.R;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeLeaderBoardAdmin extends Fragment {

    AdminDataCommunication dataCommunication;

    ArrayList<String > collegeNames = new ArrayList<>();
    NoInternetDialog noInternetDialog;
    RecyclerView results_recyclerview;
    int cnt=0;
    public SeeLeaderBoardAdmin() {
        // Required empty public constructor
    }

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("mList",collegeNames);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null){
            collegeNames = savedInstanceState.getStringArrayList("mList");
        }else{
            collegeNames = dataCommunication.getEventNames();
        }

        final View v = inflater.inflate(R.layout.fragment_see_leader_board_admin, container, false);
        results_recyclerview = v.findViewById(R.id.results_recyclerview2x);
        results_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        results_recyclerview.setAdapter(new AdminResultsAdapter(getContext(), collegeNames));
        noInternetDialog= new NoInternetDialog.Builder(v.getContext()).build();

        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(noInternetDialog!=null)
            noInternetDialog.onDestroy();
    }
}
