package com.example.ignite19.ui.events;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class myViewPagerAdapter extends FragmentPagerAdapter {

    String data[] = {"first","second"};

    public myViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        if(position == 0)
        {
            return new EventsTab0();
        }

        if(position == 1)
        {
            return new EventsTab1();
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