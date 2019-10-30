package com.example.qsl.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.LayoutEditNicknameBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;

public class EditNickName extends BaseFragment {
    private LayoutEditNicknameBinding binding;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            this.binding = DataBindingUtil.inflate(inflater, R.layout.layout_edit_nickname, container, false);
            initlisten();
        }
        activity.showStatusBar();
        return binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initlisten() {
        this.binding.save.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String nickName = EditNickName.this.binding.etNickName.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    Toast.makeText(EditNickName.this.getContext(), "请输入昵称", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.getInstance().editNickName(nickName).subscribe(
                            a->{
                                UserBean userBean = UserOption.getInstance().querryUser();
                                userBean.setUserName(nickName);
                                UserOption.getInstance().updateUser(userBean);
                                UserObservable.getInstance().notifyObservers(new EventData(2));
                                Presenter.getInstance().back();
                            }
                    );
                }
            }


        });
    }

    public void onDestroy() {
        super.onDestroy();
        this.activity.hideStatusBar();
    }
}
