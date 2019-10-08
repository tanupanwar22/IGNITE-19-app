package com.NITK.ignite.ui.schedule;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> listfragment=new ArrayList<>();
    private final List<String> listtitles = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listtitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitles.get(position);
    }





    public void AddFragment(Fragment fragment,String title)
    {
        listfragment.add(fragment);
        listtitles.add(title);
    }
}
