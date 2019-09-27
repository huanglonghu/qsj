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
import com.example.qsl.bean.LoginBody;
import com.example.qsl.bean.LoginResponse;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.PwdloginBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;

public class PwdLogin extends BaseFragment {


    private PwdloginBinding binding;
    private LoginBody loginBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.pwdlogin, container, false);
        loginBody = new LoginBody();
        binding.setLoginBody(loginBody);
        binding.setPresenter(Presenter.getInstance());
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

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(loginBody.getMobile())) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(loginBody.getPassword())) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().login(loginBody).subscribe(
                        str -> {
                            LoginResponse loginResponse = GsonUtil.fromJson(str, LoginResponse.class);
                            LoginResponse.DataBean data = loginResponse.getData();
                            if (loginResponse.getCode() == 2004) {
                                Register register = new Register();
                                Bundle bundle = new Bundle();
                                bundle.putString("code", loginBody.getCode());
                                bundle.putString("mobile", loginBody.getMobile());
                                register.setArguments(bundle);
                                Presenter.getInstance().step2fragment(register, "register");
                            } else if (loginResponse.getCode() == 0) {
                                UserBean userBean = UserOption.getInstance().querryUser();
                                if (userBean == null) {
                                    userBean = new UserBean();
                                    userBean.setAvatar(data.getAvatar());
                                    userBean.setId(data.getId());
                                    userBean.setToken(data.getToken());
                                    userBean.setUserName(data.getUserName());
                                    userBean.setUserType(data.getUserType());
                                    UserOption.getInstance().addUser(userBean);
                                }
                                UserObservable.getInstance().notifyObservers(new EventData(EventType.EVENTTYPE_LOGIN_SUCCESS));
                                Presenter.getInstance().back();
                            }

                            Presenter.getInstance().back();
                        }
                );

            }
        });

    }


}