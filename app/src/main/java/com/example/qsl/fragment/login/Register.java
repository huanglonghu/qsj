package com.example.qsl.fragment.login;

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

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.LoginResponse;
import com.example.qsl.bean.LoginResponse.DataBean;
import com.example.qsl.bean.RegisterBody;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.RegisterBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.widget.TipDialog2;

public class Register extends BaseFragment {
    private RegisterBinding binding;
    private RegisterBody registerBody;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.register, container, false);
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        String mobile = bundle.getString("mobile");
        this.registerBody = new RegisterBody();
        this.registerBody.setCode(code);
        this.registerBody.setMobile(mobile);
        this.binding.setRegisterBody(this.registerBody);
        initlisten();
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initlisten() {
        this.binding.register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(registerBody.getUserName())) {
                    Toast.makeText(getContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(registerBody.getPassword())) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (registerBody.getPassword().length() < 6) {
                    Toast.makeText(getContext(), "您输入的密码长度小于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().register(registerBody).subscribe(
                        str -> {
                            LoginResponse loginResponse = GsonUtil.fromJson(str, LoginResponse.class);
                            DataBean data = loginResponse.getData();
                            if (data != null) {
                                UserBean userBean = new UserBean();
                                userBean.setAvatar(data.getAvatar());
                                userBean.setId(Long.valueOf(data.getId()));
                                userBean.setToken(data.getToken());
                                userBean.setUserName(data.getUserName());
                                userBean.setUserType(data.getUserType());
                                UserOption.getInstance().addUser(userBean);
                                new TipDialog2(getContext(), new ClickSureListener() {
                                    @Override
                                    public void clickSure() {
                                        Presenter.getInstance().back();
                                        Presenter.getInstance().back();
                                    }
                                }).show();
                            }

                        }

                );

            }
        });
    }
}
