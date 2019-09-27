package com.example.qsl.fragment.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.LoginResponse;
import com.example.qsl.bean.RegisterBody;
import com.example.qsl.constant.Constants;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.RegisterBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;

public class Register extends BaseFragment {

    private RegisterBinding binding;
    private RegisterBody registerBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register, container, false);
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        String mobile = bundle.getString("mobile");
        registerBody = new RegisterBody();
        registerBody.setCode(code);
        registerBody.setMobile(mobile);
        binding.setRegisterBody(registerBody);
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {


    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(registerBody.getUserName())) {
                    Toast.makeText(getContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(registerBody.getNickName())) {
                    Toast.makeText(getContext(), "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(registerBody.getPassword())) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().register(registerBody).subscribe(
                        str -> {
                            LoginResponse loginResponse = GsonUtil.fromJson(str, LoginResponse.class);
                            LoginResponse.DataBean data = loginResponse.getData();
                            if (data != null) {
                                UserBean userBean = new UserBean();
                                userBean.setAvatar(data.getAvatar());
                                userBean.setId(data.getId());
                                userBean.setToken(data.getToken());
                                userBean.setUserName(data.getUserName());
                                userBean.setUserType(data.getUserType());
                                UserOption.getInstance().addUser(userBean);
                                Presenter.getInstance().back();
                                Presenter.getInstance().back();
                            }

                        }
                );


            }
        });

    }
}
