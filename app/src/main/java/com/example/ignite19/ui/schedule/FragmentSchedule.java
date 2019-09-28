package com.example.ignite19.ui.schedule;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.ignite19.R;
import com.example.ignite19.DataCommunication;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmentSchedule extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;

    public static FragmentSchedule newInstance() {
        return new FragmentSchedule();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);
        viewPager.setAdapter(new myViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    class myViewPagerAdapter extends FragmentPagerAdapter {

        String data[] = {"Day 0","Day 1","Day 2"};

        public myViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0)
            {
                return new day0();
            }

            if(position == 1)
            {
                return new day1();
            }

            if(position == 2){
                return new day2();
            }
            return null;
        }

        @Override
        public int getCount() {
            return data.length;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data[position];
        }
    }
}
