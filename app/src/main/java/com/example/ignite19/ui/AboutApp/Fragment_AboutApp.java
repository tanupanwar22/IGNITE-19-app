package com.example.ignite19.ui.AboutApp;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    CardView openSourceCard,aboutTeamCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_about_app, null);
        openSourceCard = root.findViewById(R.id.openSourceCardbtn);
        openSourceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_about_app_to_openSourceLicense);
            }
        });
        return root;
    }

}
