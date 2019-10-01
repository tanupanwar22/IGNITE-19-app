package com.example.ignite19.ui.Feedback;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ignite19.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;

import am.appwise.components.ni.NoInternetDialog;
import es.dmoral.toasty.Toasty;
import me.zhanghai.android.materialedittext.MaterialEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Feedback extends Fragment {
private int website_rating,ignite_rating,app_rating,hospitality_rating;
private String website_desc,ignite_desc,app_desc,hospitality_desc,clg_name;

    public Fragment_Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_feedback, null);
        NoInternetDialog noInternetDialog= new NoInternetDialog.Builder(root.getContext()).build();

        final MaterialEditText ignite_desc_var,website_desc_var,app_desc_var,hospitality_desc_var;
        Button submit_btn=root.findViewById(R.id.feedback_submit_btn);
        ignite_desc_var=root.findViewById(R.id.username_editText);
        app_desc_var=root.findViewById(R.id.app_desc_tv);
        hospitality_desc_var=root.findViewById(R.id.hospitality_desc_tv);
        website_desc_var=root.findViewById(R.id.website_desc_tv);

        website_rating=ignite_rating=app_rating=hospitality_rating=-1;


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clg_name=dataSnapshot.child("college_name").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SmileRating smileRatingIgnite =  root.findViewById(R.id.smile_rating_Ignite);


        smileRatingIgnite.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                switch (smiley) {
                    case SmileRating.BAD:
                            ignite_rating=2;
                        break;
                    case SmileRating.GOOD:

                        ignite_rating=4;
                        break;
                    case SmileRating.GREAT:

                        ignite_rating=5;
                        break;
                    case SmileRating.OKAY:

                        ignite_rating=3;
                        break;
                    case SmileRating.TERRIBLE:

                        ignite_rating=1;
                        break;
                }
            }
        });

        SmileRating smileRatingWebsite =  root.findViewById(R.id.smile_rating_website);


        smileRatingWebsite.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                switch (smiley) {
                    case SmileRating.BAD:
                        website_rating=2;
                        break;
                    case SmileRating.GOOD:

                        website_rating=4;
                        break;
                    case SmileRating.GREAT:

                        website_rating=5;
                        break;
                    case SmileRating.OKAY:

                        website_rating=3;
                        break;
                    case SmileRating.TERRIBLE:

                        website_rating=1;
                        break;

                }
            }
        });


        SmileRating smileRatingApp =  root.findViewById(R.id.smile_rating_app);


        smileRatingApp.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                switch (smiley) {
                    case SmileRating.BAD:
                        app_rating=2;
                        break;
                    case SmileRating.GOOD:

                        app_rating=4;
                        break;
                    case SmileRating.GREAT:

                        app_rating=5;
                        break;
                    case SmileRating.OKAY:

                        app_rating=3;
                        break;
                    case SmileRating.TERRIBLE:

                        app_rating=1;
                        break;

                }
            }
        });



        SmileRating smileRatingHospitality =  root.findViewById(R.id.smile_rating_hospitality);


        smileRatingHospitality.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                switch (smiley) {
                    case SmileRating.BAD:
                        hospitality_rating=2;
                        break;
                    case SmileRating.GOOD:

                        hospitality_rating=4;
                        break;
                    case SmileRating.GREAT:

                        hospitality_rating=5;
                        break;
                    case SmileRating.OKAY:

                        hospitality_rating=3;
                        break;
                    case SmileRating.TERRIBLE:

                        hospitality_rating=1;
                        break;

                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                website_desc=website_desc_var.getText().toString();
                ignite_desc=ignite_desc_var.getText().toString();
                app_desc=app_desc_var.getText().toString();
                hospitality_desc=hospitality_desc_var.getText().toString();
                if(website_rating!=-1 && ignite_rating!=-1 && app_rating!= -1 && hospitality_rating!=-1 && !website_desc.isEmpty() && !ignite_desc.isEmpty() && !app_desc.isEmpty() && !hospitality_desc.isEmpty()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Feedbacks").child(clg_name);

                    HashMap<String, Object> result = new HashMap<>();
                    result.put("website_rating", String.valueOf(website_rating));
                    result.put("ignite_rating", String.valueOf(ignite_rating));
                    result.put("app_rating", String.valueOf(app_rating));
                    result.put("hospitality_rating", String.valueOf(hospitality_rating));
                    result.put("website_views", String.valueOf(website_desc));
                    result.put("ignite_views", String.valueOf(ignite_desc));
                    result.put("app_views", String.valueOf(app_desc));
                    result.put("hospitality_views", String.valueOf(hospitality_desc));
                    reference.updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toasty.success(getContext(), "Feedback Submitted.", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_nav_feedback_to_nav_home);
                        }
                    });

                }
                else{
                    Toasty.info(getContext(),"Please Give Feedback for each mentioned field.",Toast.LENGTH_LONG).show();
                }
            }
        });


        return root;
    }

}
