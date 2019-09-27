package com.example.qsl.fragment.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.qsl.Interface.HandlerStrategy;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.EditAvatarBody;
import com.example.qsl.bean.UploadPicture;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.FragmentAccountBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.EventType;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.presenter.ActivityResultHandler;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.ImagUtil;
import com.example.qsl.util.LogUtil;
import okhttp3.MultipartBody;

public class AccountMsg extends BaseFragment {

    private FragmentAccountBinding binding;
    private UserBean userBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
            binding.setPresenter(Presenter.getInstance());
            binding.setFragment(this);
            initData();
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
    }

    private void initHead() {
        userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            binding.setUser(userBean);
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

    @Override
    public void initlisten() {
        binding.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
                        Drawable drawable = ImagUtil.circle(getContext(), bitmap);
                        binding.ivHead.setImageDrawable(drawable);
                        HttpUtil.getInstance().upload(filePart, userBean.getAvatar()).subscribe(
                                str -> {
                                    UploadPicture uploadPicture = GsonUtil.fromJson(str, UploadPicture.class);
                                    String url = uploadPicture.getData();
                                    EditAvatarBody editAvatarBody = new EditAvatarBody();
                                    editAvatarBody.setAvatar(url);
                                    LogUtil.log("========avatar========"+url);
                                    editAvatarBody.setId(userBean.getId().intValue());
                                    HttpUtil.getInstance().editHead(editAvatarBody).subscribe(
                                            str2 -> {
                                                userBean.setAvatar(url);
                                                UserOption.getInstance().updateUser(userBean);
                                            }
                                    );
                                }
                        );

                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();

            }
        });


        binding.changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPwd editPwd = new EditPwd();
                Presenter.getInstance().step2fragment(editPwd, "editPwd");
            }
        });


        binding.nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNickName editNickName = new EditNickName();
                Presenter.getInstance().step2fragment(editNickName, "editNickName");
            }
        });


        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {

                int eventType = data.getEventType();
                switch (eventType) {
                    case EventType.EVENTTYPE_EDIT_NICKNAME:
                        userBean = UserOption.getInstance().querryUser();
                        binding.setUser(userBean);
                        break;
                }

            }
        };

        UserObservable.getInstance().register(userObserver);


    }


}
