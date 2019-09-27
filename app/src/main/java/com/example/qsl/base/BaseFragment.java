package com.example.qsl.base;


import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {


    public abstract void initData();

    public abstract void initView();

    public abstract void initlisten();

    public void onKeyDown() {}




}
