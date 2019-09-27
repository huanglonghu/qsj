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
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.MainBinding;
import com.example.qsl.presenter.Presenter;
import java.util.ArrayList;

public class Main extends BaseFragment {
    private MainBinding binding;
    private ArrayList<BaseFragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.main, container, false);
            binding.setFragment(this);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    public void togglePager(int index) {
        if (index == 2) {
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean == null) {
                Presenter.getInstance().step2Fragment("login");
                return;
            } else {
                binding.mainViewPager.setCurrentItem(index, false);
                binding.setPosition(index);
            }
        } else {
            binding.mainViewPager.post(new Runnable() {
                @Override
                public void run() {
                    binding.mainViewPager.setCurrentItem(index, false);
                }
            });
            binding.setPosition(index);
        }
    }


    @Override
    public void initData() {}

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        Converstation converstation = new Converstation();
        Contacts contacts = new Contacts();
        Me me = new Me();
        fragments.add(converstation);
        fragments.add(contacts);
        fragments.add(me);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments);
        binding.mainViewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void initlisten() {

    }
}
