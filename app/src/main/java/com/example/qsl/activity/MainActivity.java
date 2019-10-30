package com.example.qsl.activity;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.fragment.chat.Chat;
import com.example.qsl.fragment.login.Login;
import com.example.qsl.fragment.main.Main;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.ActivityResultHandler;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.presenter.Presenter.Builder;
import com.example.qsl.util.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends AppCompatActivity {
    private Main main;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView((int) R.layout.activity_main);
        HttpUtil.getInstance().init(this);
        FragmentManager fm = getSupportFragmentManager();
        new Builder().context(this).fragmentManager(fm).build();
        if (main == null) {
            main = (Main) Presenter.getInstance().getFragment("main");
        }
        boolean isLogin = getIntent().getBooleanExtra("isLogin", false);
        fm.beginTransaction().replace(R.id.container, this.main).commit();
        if (!isLogin) {
            main.togglePager(2);
        }
        requestPermission();
    }




    private void requestPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
    }


    protected void onSaveInstanceState(Bundle outState) {
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtil.log(requestCode + "==============result============" + resultCode);
        ActivityResultHandler.getInstance().handler(requestCode, resultCode, data);
    }

    public void showStatusBar() {
        getWindow().setStatusBarColor(-1);
    }

    public void hideStatusBar() {
        getWindow().setStatusBarColor(Color.parseColor("#0159eb"));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment instanceof Main) {
                Intent home = new Intent("android.intent.action.MAIN");
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory("android.intent.category.HOME");
                startActivity(home);
                return true;
            } else if (fragment instanceof Login) {
                fragment.onKeyDown();
                return true;
            } else if (fragment instanceof Chat) {
                fragment.onKeyDown();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.log("==========MainActivityStart===========");
       // setStyleBasic2();

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.log("==========MainActivityStop===========");
      //  setStyleBasic();
        // JPushInterface.resumePush(getApplicationContext());
    }
}
