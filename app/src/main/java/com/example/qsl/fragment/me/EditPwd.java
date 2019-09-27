package com.example.qsl.fragment.me;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.ChangePsw;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.EditPwdBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.ImagUtil;

import org.json.JSONObject;

public class EditPwd extends BaseFragment {

    private EditPwdBinding binding;
    private ChangePsw changePsw;
    private UserBean userBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.edit_pwd, container, false);
            binding.setPresenter(Presenter.getInstance());
            initData();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
        userBean = UserOption.getInstance().querryUser();
        String avatar = userBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.circle(getContext(), imageBean.getBitmap());
                        binding.head.setImageDrawable(drawable);
                    }
            );
        }
        changePsw = new ChangePsw();
        binding.setChangePsw(changePsw);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(changePsw.getOldPassword())) {
                    Toast.makeText(getContext(), "请填写原密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(changePsw.getPassword())) {
                    Toast.makeText(getContext(), "请填写新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(binding.surePsw.getText().toString())) {
                    Toast.makeText(getContext(), "请确认新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!changePsw.getPassword().equals(binding.surePsw.getText().toString())) {
                    Toast.makeText(getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().changePsw(changePsw).subscribe(
                        str -> {
                            JSONObject jo = new JSONObject(str);
                            int code = jo.getInt("code");
                            if (code == 0) {
                                Toast.makeText(getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                Presenter.getInstance().back();
                            } else {
                                String msg = jo.getString("msg");
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }
}
