package com.example.qsl.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
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
    private static Presenter defaultInstance;
    private Context context;
    private FragmentManager fm;
    private HashMap<String, BaseFragment> fragmentMap = new HashMap();

    public static class Builder {
        private Context context;
        private FragmentManager fm;

        public Builder fragmentManager(FragmentManager fm) {
            this.fm = fm;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Presenter build() {
            Presenter.getInstance().init(this);
            return Presenter.defaultInstance;
        }
    }

    private Presenter() {
    }

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
        this.fm.beginTransaction().replace(R.id.container, fragment).addToBackStack(stack).commit();
    }

    public void step2Fragment(String name) {
        this.fm.beginTransaction().replace(R.id.container, getFragment(name)).addToBackStack(name).commit();
    }

    public BaseFragment getFragment(String name) {
        if (fragmentMap.get(name) != null) {
            return fragmentMap.get(name);
        }
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
    }

    public void back() {
        this.fm.popBackStack();
    }

    private void init(Builder builder) {
        this.fm = builder.fm;
        this.context = builder.context;
    }

    public DisplayCutout isAndroidP(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null && VERSION.SDK_INT >= 28) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                return windowInsets.getDisplayCutout();
            }
        }
        return null;
    }
}
