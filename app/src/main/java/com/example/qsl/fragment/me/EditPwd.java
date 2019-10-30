package com.example.qsl.fragment.me;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.example.qsl.bean.ChangePsw;
import com.example.qsl.bean.ImageBean;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.binding == null) {
            this.binding = (EditPwdBinding) DataBindingUtil.inflate(inflater, R.layout.edit_pwd, container, false);
            this.binding.setPresenter(Presenter.getInstance());
            initData();
            initlisten();
        }
        return this.binding.getRoot();
    }

    public void initData() {
        this.userBean = UserOption.getInstance().querryUser();
        String url = ImagUtil.handleUrl(this.userBean.getAvatar());
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        binding.head.setImageDrawable(ImagUtil.circle(getContext(), imageBean.getBitmap()));
                    }
            );
        }
        this.changePsw = new ChangePsw();
        this.binding.setChangePsw(this.changePsw);
    }


    public void initView() {
    }

    public void initlisten() {
        this.binding.complete.setOnClickListener(new OnClickListener() {
            @SuppressLint({"CheckResult"})
            public void onClick(View v) {
                if (TextUtils.isEmpty(EditPwd.this.changePsw.getOldPassword())) {
                    Toast.makeText(EditPwd.this.getContext(), "请填写原密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(EditPwd.this.changePsw.getPassword())) {
                    Toast.makeText(EditPwd.this.getContext(), "请填写新密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(EditPwd.this.binding.surePsw.getText().toString())) {
                    Toast.makeText(EditPwd.this.getContext(), "请确认新密码", Toast.LENGTH_SHORT).show();
                } else if (EditPwd.this.changePsw.getPassword().equals(EditPwd.this.binding.surePsw.getText().toString())) {
                    HttpUtil.getInstance().changePsw(EditPwd.this.changePsw).subscribe(
                            str -> {
                                JSONObject jo = new JSONObject(str);
                                if (jo.getInt("code") == 0) {
                                    Toast.makeText(EditPwd.this.getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                    Presenter.getInstance().back();
                                    return;
                                }
                                Toast.makeText(EditPwd.this.getContext(), jo.getString(NotificationCompat.CATEGORY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                    );
                } else {
                    Toast.makeText(EditPwd.this.getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
