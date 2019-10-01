package com.example.ignite19.ui.LeaderBoard;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ignite19.DataCommunication;
import com.example.ignite19.R;
import com.example.ignite19.UserDataAndEventList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLeaderBoard extends Fragment {
    DataCommunication dataCommunication;

    ArrayList<UserDataAndEventList> userDataAndEventLists = new ArrayList<>();
public List<String> results_event_name_list=new ArrayList<>();
RecyclerView results_recyclerview;
int cnt=0;
    public FragmentLeaderBoard() {
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList",userDataAndEventLists);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null){
            userDataAndEventLists = savedInstanceState.getParcelableArrayList("mList");
        }else{
            userDataAndEventLists = dataCommunication.getAllEventList();
        }

        results_event_name_list.clear();
        for (UserDataAndEventList m : userDataAndEventLists) {
            results_event_name_list.add(m.getEvent_name());
        }
        final View v = inflater.inflate(R.layout.fragment_leader_board, container, false);

        results_recyclerview = v.findViewById(R.id.results_recyclerview);

        results_recyclerview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        results_recyclerview.setAdapter(new resultsAdapter(getContext(), results_event_name_list));
        NoInternetDialog noInternetDialog= new NoInternetDialog.Builder(v.getContext()).build();

        return v;
    }
}
