package com.example.qsl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.qsl.activity.MainActivity;

public abstract class BaseFragment extends Fragment {
    public MainActivity activity;

    public abstract void initData();

    public abstract void initView();

    public abstract void initlisten();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    public void onKeyDown() {
    }
}
