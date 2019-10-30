package com.example.qsl.fragment.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qsl.R;
import com.example.qsl.activity.MainActivity;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.AdBinding;

public class AdActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setStatusBarColor(Color.parseColor("#0159eb"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad);

        View apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdActivity.this, MainActivity.class);
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean != null) {
                    intent.putExtra("isLogin", true);
                }
                startActivity(intent);
            }
        });

    }
}
