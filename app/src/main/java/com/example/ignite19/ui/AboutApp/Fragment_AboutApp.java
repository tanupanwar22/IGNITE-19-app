package com.example.ignite19.ui.AboutApp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ignite19.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AboutApp extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_about_app, null);

        TextView tv=root.findViewById(R.id.ourTeam);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(root.getContext(),ourTeam.class));
            }
        });

        return root;
    }

}
