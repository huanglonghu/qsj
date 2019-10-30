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

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.LoginBody;
import com.example.qsl.bean.LoginResponse;
import com.example.qsl.bean.LoginResponse.DataBean;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.PwdloginBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;

public class PwdLogin extends BaseFragment {
    private PwdloginBinding binding;
    private LoginBody loginBody;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = (PwdloginBinding) DataBindingUtil.inflate(inflater, R.layout.pwdlogin, container, false);
        this.loginBody = new LoginBody();
        this.binding.setLoginBody(this.loginBody);
        this.binding.setPresenter(Presenter.getInstance());
        initlisten();
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initlisten() {
        this.binding.login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(loginBody.getMobile())) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(loginBody.getPassword())) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loginBody.getPassword().length() < 6) {
                    Toast.makeText(getContext(), "您输入的密码长度小于6位", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().login(PwdLogin.this.loginBody).subscribe(
                        str -> {
                            LoginResponse loginResponse = (LoginResponse) GsonUtil.fromJson(str, LoginResponse.class);
                            DataBean data = loginResponse.getData();
                            if (loginResponse.getCode() == 2004) {
                                Toast.makeText(getContext(), "账号密码不存在", Toast.LENGTH_SHORT).show();
                            } else if (loginResponse.getCode() == 0) {
                                if (UserOption.getInstance().querryUser() == null) {
                                    UserBean userBean = new UserBean();
                                    userBean.setAvatar(data.getAvatar());
                                    userBean.setId(Long.valueOf(data.getId()));
                                    userBean.setToken(data.getToken());
                                    userBean.setUserName(data.getUserName());
                                    userBean.setUserType(data.getUserType());
                                    UserOption.getInstance().addUser(userBean);
                                }
                                UserObservable.getInstance().notifyObservers(new EventData(1));
                                Presenter.getInstance().back();
                                Presenter.getInstance().back();
                            } else {
                                Toast.makeText(PwdLogin.this.getContext(), loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }

        });
        this.binding.mobileLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });
    }
}
