package com.example.qsl.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.qsl.R;
import com.example.qsl.fragment.main.Main;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.ActivityResultHandler;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtil.getInstance().init(this);
        FragmentManager fm = getSupportFragmentManager();
        new Presenter.Builder().context(this).fragmentManager(fm).build();
        Main main = new Main();
        fm.beginTransaction().replace(R.id.container, main).commit();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtil.log(requestCode + "==============result============" + resultCode);
        ActivityResultHandler.getInstance().handler(requestCode, resultCode, data);

    }
}
