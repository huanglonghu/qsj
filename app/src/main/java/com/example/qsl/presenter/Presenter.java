package com.example.qsl.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.fragment.login.Login;
import com.example.qsl.fragment.main.Main;

import java.util.HashMap;

public class Presenter {

    private FragmentManager fm;
    private Context context;

    private Presenter() {
        fragmentMap = new HashMap<>();
    }

    private static Presenter defaultInstance;

    public static Presenter getInstance() {
        Presenter presenter = defaultInstance;
        if (defaultInstance == null) {
            synchronized (Presenter.class) {
                if (defaultInstance == null) {
                    presenter = new Presenter();
                    defaultInstance = presenter;
                }
            }
        }
        return presenter;
    }

    public void step2fragment(BaseFragment fragment, String stack) {
        fm.beginTransaction().replace(R.id.container, fragment).addToBackStack(stack).commit();
    }


    public void step2Fragment(String name) {
        fm.beginTransaction().replace(R.id.container, getFragment(name)).addToBackStack(name).commit();
    }

    private HashMap<String, BaseFragment> fragmentMap;

    public BaseFragment getFragment(String name) {
        if (fragmentMap.get(name) == null) {
            BaseFragment fragment = null;
            switch (name) {
                case "main":
                    fragment = new Main();
                    break;
                case "login":
                    fragment = new Login();
                    break;
            }
            fragmentMap.put(name, fragment);
            return fragment;
        } else {
            return fragmentMap.get(name);
        }
    }

    public void back() {
        fm.popBackStack();
    }

    private void init(Builder builder) {
        this.fm = builder.fm;
        this.context = builder.context;
    }


    public static class Builder {
        private FragmentManager fm;
        private Context context;

        public Builder fragmentManager(FragmentManager fm) {
            this.fm = fm;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Presenter build() {
            getInstance().init(this);
            return defaultInstance;
        }
    }


    public DisplayCutout isAndroidP(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && Build.VERSION.SDK_INT >= 28) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null)
                return windowInsets.getDisplayCutout();
        }
        return null;
    }

}
