package com.example.ignite19;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenSourceLicense extends Fragment {

    private ArrayList<OpenSourcePOJO> mList = new ArrayList<>();
    View v;
    public OpenSourceLicense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mList.add(new OpenSourcePOJO("Toasty","https://github.com/GrenderG/Toasty","https://github.com/GrenderG/Toasty/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("Glide","https://github.com/bumptech/glide","https://github.com/bumptech/glide/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("MaterialEditText","https://github.com/zhanghai/MaterialEditText","https://github.com/zhanghai/MaterialEditText/blob/master/README.md"));
        mList.add(new OpenSourcePOJO("Smiley Rating","https://github.com/sujithkanna/SmileyRating","https://github.com/sujithkanna/SmileyRating/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("CircularImageView","https://github.com/lopspower/CircularImageView","https://github.com/lopspower/CircularImageView/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("LoadingView","https://github.com/ldoublem/LoadingView","https://github.com/ldoublem/LoadingView/blob/master/README.md"));
        mList.add(new OpenSourcePOJO("Lottie for Android, iOS, React Native, Web, and Windows","https://github.com/airbnb/lottie-android","https://github.com/airbnb/lottie-android/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("Android-SpinKit","https://github.com/ybq/Android-SpinKit","https://github.com/ybq/Android-SpinKit/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("SubmitButton","https://github.com/SparkYuan/SubmitButton","https://github.com/SparkYuan/SubmitButton/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("Icons","By freepik,ultimatearm,smashicons and iconnice  ","https://flaticon.com"));
        mList.add(new OpenSourcePOJO("Lottie Loader and green tick","By Jojo Lafrite and Lottie Files","https://lottiefiles.com"));
        mList.add(new OpenSourcePOJO("No Internet Dialog","https://github.com/appwise-labs/NoInternetDialog","https://github.com/appwise-labs/NoInternetDialog/blob/master/LICENSE"));
        mList.add(new OpenSourcePOJO("Timeline-View","https://github.com/vipulasri/Timeline-View","https://github.com/vipulasri/Timeline-View/blob/master/LICENSE"));


        v =  inflater.inflate(R.layout.fragment_open_source_license, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.xx_recycler_view);
        OpenSourceClassAdapter adapter = new OpenSourceClassAdapter(mList,getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

}
