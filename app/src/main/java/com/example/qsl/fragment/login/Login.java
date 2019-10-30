package com.example.qsl.fragment.login;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
import com.example.qsl.databinding.LoginBinding;
import com.example.qsl.fragment.main.Main;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.LogUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

public class Login extends BaseFragment {
    private LoginBinding binding;
    private LoginBody loginBody;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.login, container, false);
        this.loginBody = new LoginBody();
        this.binding.setLoginBody(this.loginBody);
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
                if (TextUtils.isEmpty(Login.this.loginBody.getMobile())) {
                    Toast.makeText(Login.this.getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Login.this.loginBody.getCode())) {
                    Toast.makeText(Login.this.getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.getInstance().login(Login.this.loginBody).subscribe(
                            str -> {
                                LoginResponse loginResponse = (LoginResponse) GsonUtil.fromJson(str, LoginResponse.class);
                                DataBean data = loginResponse.getData();
                                if (loginResponse.getCode() == 2004) {
                                    Register register = new Register();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("code", Login.this.loginBody.getCode());
                                    bundle.putString("mobile", Login.this.loginBody.getMobile());
                                    register.setArguments(bundle);
                                    Presenter.getInstance().step2fragment(register, "register");
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
                                } else {
                                    Toast.makeText(Login.this.getContext(), loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            }
        });
        this.binding.sendYzm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(Login.this.loginBody.getMobile())) {
                    Toast.makeText(Login.this.getContext(), "请输入电话号码", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.getInstance().getCode(Login.this.loginBody.getMobile()).subscribe(
                            str -> {
                                JSONObject jo = new JSONObject(str);
                                if (jo.getInt("code") == 0) {
                                    Login.this.countDownTime();
                                    return;
                                }
                                Toast.makeText(Login.this.getContext(), jo.getString(NotificationCompat.CATEGORY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                    );
                }
            }
        });
        this.binding.accountLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Presenter.getInstance().step2fragment(new PwdLogin(), "pwdlogin");
            }
        });
    }

    public void onKeyDown() {
        Main main = (Main) Presenter.getInstance().getFragment("main");
        int position = main.getPosition();
        UserBean userBean = UserOption.getInstance().querryUser();
        LogUtil.log(userBean + "==========AAAAAAAAA===========" + position);
        if (userBean == null) {
            main.togglePager(0);
            Presenter.getInstance().back();
            return;
        }
        Presenter.getInstance().back();
    }

    public void countDownTime() {
        this.binding.sendYzm.setEnabled(false);
        Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
            public void accept(Long aLong) throws Exception {
                Login.this.binding.sendYzm.setText("重新发送 " + String.valueOf(60 - aLong.longValue()) + " 秒");
            }
        }).doOnComplete(new Action() {
            public void run() throws Exception {
                Login.this.binding.sendYzm.setText("获取验证码");
                Login.this.binding.sendYzm.setEnabled(true);
            }
        }).subscribe();
    }
}
