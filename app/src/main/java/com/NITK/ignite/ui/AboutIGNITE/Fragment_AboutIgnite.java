package com.NITK.ignite.ui.AboutIGNITE;


import android.drm.DrmStore;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.NITK.ignite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AboutIgnite extends Fragment {

    View v;
    TextView mTextView;
    EditText mEditText;
    Fragment mFragment;
    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_about_ignite, container, false);

        return v;
    }

}
