package com.example.qsl.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qsl.R;
import com.example.qsl.adapter.MyViewPagerAdapter;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.base.LazyLoadFragment;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.MainBinding;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;

import java.util.ArrayList;

public class Main extends LazyLoadFragment {
    private MainBinding binding;
    private ArrayList<BaseFragment> fragments;
    private int position;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.main, container, false);
            binding.setFragment(this);
            initView();
            initlisten();
        }
        return this.binding.getRoot();
    }

    public void togglePager(final int index) {
        if (index != 2) {
            binding.mainViewPager.post(new Runnable() {
                public void run() {
                    Main.this.binding.mainViewPager.setCurrentItem(index, false);
                }
            });
            binding.setPosition(index);
            position = index;
        } else if (UserOption.getInstance().querryUser() == null) {
            Presenter.getInstance().step2Fragment("login");
        } else {
            binding.mainViewPager.setCurrentItem(index, false);
            binding.setPosition(index);
            position = index;
        }
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void initData() {
    }

    public void initView() {
        fragments = new ArrayList();
        Converstation converstation = new Converstation();
        Contacts contacts = new Contacts();
        Me me = new Me();
        fragments.add(converstation);
        fragments.add(contacts);
        fragments.add(me);
        binding.mainViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), this.fragments));
        binding.mainViewPager.setOffscreenPageLimit(2);
    }

    public void initlisten() {
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void loadData() {

    }
}
