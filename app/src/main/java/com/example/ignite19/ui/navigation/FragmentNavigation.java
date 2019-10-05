package com.example.ignite19.ui.navigation;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ignite19.R;
import com.example.ignite19.ui.schedule.RecyclerViewAdapter;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

public class FragmentNavigation extends Fragment {

    View v;
    RecyclerView mRecylerView;
    ArrayList<VenueList > venue_list = new ArrayList<>();
    public static FragmentNavigation newInstance() {
        return new FragmentNavigation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //add the venues here to the venue_list arrayList
        //this is local data . Changing data in firebase won't affect it
        venue_list.add(new VenueList("Central Computer Center (CCC)",13.009490,74.795780));
        venue_list.add(new VenueList("Lecture Hall Complex A(LHC-A, ATB)",13.009661,74.793849));
        venue_list.add(new VenueList("Lecture Hall Complex B(LHC-B, NTB)",13.009319,74.794500));
        venue_list.add(new VenueList("Lecture Hall Complex C(LHC-C)",13.010462,74.792499));
        venue_list.add(new VenueList("Pavilion",13.011202,74.794678));
       // venue_list.add(new VenueList("Rest Rooms",13.007833,74.794848));
        venue_list.add(new VenueList("SACA Lab",13.009699,74.794659));
        venue_list.add(new VenueList("Trishul Block Mess",13.007429,74.794230));

        v=inflater.inflate(R.layout.fragment_navigation,container,false);
        mRecylerView = (RecyclerView)v.findViewById(R.id.venue_list_recycler_view);
        NavigationRecyclerAdapter adapter = new NavigationRecyclerAdapter(getContext(),venue_list);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NoInternetDialog noInternetDialog= new NoInternetDialog.Builder(v.getContext()).build();

        mRecylerView.setAdapter(adapter);
        return v;
    }



}
