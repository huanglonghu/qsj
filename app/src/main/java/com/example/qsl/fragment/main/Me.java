package com.example.qsl.fragment.main;

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

import cn.jpush.im.android.api.JMessageClient;

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.base.LazyLoadFragment;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.bean.VersionMsg;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.MeBinding;
import com.example.qsl.fragment.me.AccountMsg;
import com.example.qsl.fragment.me.Qrcode;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.DataCleanManager;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.util.LogUtil;
import com.example.qsl.widget.TipDialog;
import com.example.qsl.widget.TipDialog2;

public class Me extends LazyLoadFragment {
    private MeBinding binding;
    private double currentVersion;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            this.binding = (MeBinding) DataBindingUtil.inflate(inflater, R.layout.me, container, false);
            this.binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
        }
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
        initHead();
        this.binding.cacheSize.setText(getCacheSize() + "");
    }

    private void initHead() {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            String url = ImagUtil.handleUrl(userBean.getAvatar());
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            binding.ivHead.setImageDrawable(ImagUtil.circle(getContext(), imageBean.getBitmap()));
                        }
                );
            }
            binding.setUserType(userBean.getUserType());
        }
    }


    public void initlisten() {
        this.binding.account.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Presenter.getInstance().step2fragment(new AccountMsg(), "accountMsg");
            }
        });
        try {
            this.currentVersion = Double.parseDouble(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
            this.binding.version.setText(this.currentVersion + "");
        } catch (Exception e) {
        }
        this.binding.checkVersion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HttpUtil.getInstance().getVersion().subscribe(
                        str -> {
                            double versionName = ((VersionMsg) GsonUtil.fromJson(str, VersionMsg.class)).getVersionName();
                            if (Me.this.currentVersion < versionName) {
                                Toast.makeText(Me.this.getContext(), "有新版本" + versionName + "可更新", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Me.this.getContext(), "当前版本是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        }

                );
            }
        });
        this.binding.cleanCache.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new TipDialog(Me.this.getContext(), new ClickSureListener() {
                    public void clickSure() {
                        DataCleanManager.clearAllCache(Me.this.getContext());
                        Me.this.binding.cacheSize.setText("0");
                    }
                }, "你确定要清除缓存吗？").show();
            }
        });
        this.binding.exit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HttpUtil.getInstance().exit().subscribe(
                        str -> {
                            UserOption.getInstance().delteUser();
                            Toast.makeText(Me.this.getContext(), "成功退出登录", Toast.LENGTH_SHORT).show();
                            JMessageClient.logout();
                            UserObservable.getInstance().notifyObservers(new EventData(4));
                            Presenter.getInstance().step2Fragment("login");
                        }
                );
            }

        });
        UserObservable.getInstance().register(new UserObserver<EventData>() {
            public void onUpdate(UserObservable<EventData> userObservable, EventData data) {
                switch (data.getEventType()) {
                    case 1:
                        initHead();
                        break;
                    case 3:
                        String url = ImagUtil.handleUrl(UserOption.getInstance().querryUser().getAvatar());
                        if (!TextUtils.isEmpty(url)) {
                            RxImageLoader.with(Me.this.getContext()).getBitmap(url).subscribe(
                                    imageBean -> {
                                        binding.ivHead.setImageDrawable(ImagUtil.circle(getContext(), imageBean.getBitmap()));
                                    }

                            );
                        }
                        break;
                }
            }
        });


        binding.qrcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Qrcode qrcode = new Qrcode();
                Presenter.getInstance().step2fragment(qrcode, "qrcode");
            }
        });


    }

    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    protected void loadData() {

    }
}
