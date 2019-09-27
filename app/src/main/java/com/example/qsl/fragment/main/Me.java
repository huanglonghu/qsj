package com.example.qsl.fragment.main;

import android.content.pm.PackageInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qsl.Interface.ClickSureListener;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.VersionMsg;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.MeBinding;
import com.example.qsl.fragment.login.Login;
import com.example.qsl.fragment.me.AccountMsg;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.DataCleanManager;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.widget.TipDialog;

import cn.jpush.im.android.api.JMessageClient;

public class Me extends BaseFragment {


    private MeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.me, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

        initHead();
        String cacheSize = getCacheSize();
        binding.cacheSize.setText(cacheSize + "");

    }

    private void initHead() {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            String avatar = userBean.getAvatar();
            String url = ImagUtil.handleUrl(avatar);
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            Bitmap bitmap = imageBean.getBitmap();
                            Drawable circle = ImagUtil.circle(getContext(), bitmap);
                            binding.ivHead.setImageDrawable(circle);
                        }
                );
            }
        }
    }

    private double currentVersion;

    @Override
    public void initlisten() {
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMsg accountMsg = new AccountMsg();
                Presenter.getInstance().step2fragment(accountMsg, "accountMsg");
            }
        });

        try {
            String packageName = getActivity().getPackageName();
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(packageName, 0);
            currentVersion = Double.parseDouble(packageInfo.versionName);
            binding.version.setText(currentVersion + "");
        } catch (Exception e) {

        }
        binding.checkVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().getVersion().subscribe(
                        str -> {
                            VersionMsg versionMsg = GsonUtil.fromJson(str, VersionMsg.class);
                            double versionName = versionMsg.getVersionName();
                            if (currentVersion < versionName) {
                                Toast.makeText(getContext(), "有新版本" + versionName + "可更新", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "当前版本是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


        binding.cleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        DataCleanManager.clearAllCache(getContext());
                        binding.cacheSize.setText("0");
                    }
                }, "你确定要清除缓存吗？").show();
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().exit().subscribe(
                        str -> {
                            UserOption.getInstance().delteUser();
                            Toast.makeText(getContext(), "成功退出登录", Toast.LENGTH_SHORT).show();
                            Presenter.getInstance().back();
                            JMessageClient.logout();
                            Presenter.getInstance().step2Fragment("login");
                        }
                );
            }
        });


    }

    //获取缓存大小
    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


}
