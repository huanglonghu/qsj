package com.example.qsl.fragment.me;

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
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.LayoutEditNicknameBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.presenter.Presenter;

public class EditNickName extends BaseFragment {

    private LayoutEditNicknameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.layout_edit_nickname, container, false);
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    @Override
    public void initView() {}

    @Override
    public void initlisten() {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = binding.etNickName.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    Toast.makeText(getContext(), "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().editNickName(nickName).subscribe(
                        str -> {
                            UserBean userBean = UserOption.getInstance().querryUser();
                            userBean.setUserName(nickName);
                            UserOption.getInstance().updateUser(userBean);
                            UserObservable.getInstance().notifyObservers(new EventData(EventType.EVENTTYPE_EDIT_NICKNAME));
                            Presenter.getInstance().back();
                        }
                );
            }
        });


    }
}
