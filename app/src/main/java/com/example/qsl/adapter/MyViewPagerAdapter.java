package com.example.qsl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.qsl.base.BaseFragment;
import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> fragments = new ArrayList();

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public int getCount() {
        return this.fragments.size();
    }
}
