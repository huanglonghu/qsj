package com.example.qsl.fragment.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.qsl.Interface.HandlerStrategy;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.bean.EditAvatarBody;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.bean.UploadPicture;
import com.example.qsl.bean.UserMsg;
import com.example.qsl.catche.Loader.RxImageLoader;
import com.example.qsl.constant.Constants;
import com.example.qsl.database.entity.UserBean;
import com.example.qsl.database.option.UserOption;
import com.example.qsl.databinding.FragmentAccountBinding;
import com.example.qsl.net.HttpUtil;
import com.example.qsl.observable.EventData;
import com.example.qsl.observable.UserObservable;
import com.example.qsl.observable.UserObserver;
import com.example.qsl.presenter.ActivityResultHandler.Builder;
import com.example.qsl.presenter.Presenter;
import com.example.qsl.util.GsonUtil;
import com.example.qsl.util.ImagUtil;
import okhttp3.MultipartBody.Part;

public class AccountMsg extends BaseFragment {
    private FragmentAccountBinding binding;
    private UserBean userBean;

    @Nullable
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

    public void initData() {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            if (userBean.getUserType() == 2) {
                HttpUtil.getInstance().getUserMsg(Constants.userId).subscribe(
                        srtr -> {
                            setPhoneNumber(userBean, srtr);
                        }
                );
            } else if (userBean.getUserType() == 3) {
                HttpUtil.getInstance().getCustomerMsg(Constants.userId).subscribe(
                        str -> {
                            setPhoneNumber(userBean, str);
                        }
                );
            }
        }

    }

    private void setPhoneNumber(UserBean userBean, String srtr) {
        UserMsg userMsg = GsonUtil.fromJson(srtr, UserMsg.class);
        int code = userMsg.getCode();
        if (code == 0) {
            UserMsg.DataBean data = userMsg.getData();
            String mobile = data.getMobile();
            userBean.setPhoneNumber(mobile);
            binding.phoneNumber.setText(mobile);
            UserOption.getInstance().updateUser(userBean);
        }
    }

    public void initView() {
        initHead();
    }

    private void initHead() {
        this.userBean = UserOption.getInstance().querryUser();
        if (this.userBean != null) {
            this.binding.setUser(this.userBean);
            String url = ImagUtil.handleUrl(this.userBean.getAvatar());
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            binding.ivHead.setImageDrawable(ImagUtil.circle(getContext(), imageBean.getBitmap()));
                        }
                );
            }
        }
    }

    public void initlisten() {
        this.binding.head.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new Builder().hadlerStrategy(new HandlerStrategy() {
                    @SuppressLint({"CheckResult"})
                    public void onActivityResult(Part filePart, Bitmap bitmap) {
                        AccountMsg.this.binding.ivHead.setImageDrawable(ImagUtil.circle(AccountMsg.this.getContext(), bitmap));
                        HttpUtil.getInstance().upload(filePart, userBean.getAvatar()).subscribe(
                                str -> {
                                    String url = ((UploadPicture) GsonUtil.fromJson(str, UploadPicture.class)).getData();
                                    EditAvatarBody editAvatarBody = new EditAvatarBody();
                                    editAvatarBody.setAvatar(url);
                                    editAvatarBody.setId(AccountMsg.this.userBean.getId().intValue());
                                    HttpUtil.getInstance().editHead(editAvatarBody).subscribe(
                                            a -> {
                                                AccountMsg.this.userBean.setAvatar(url);
                                                UserOption.getInstance().updateUser(AccountMsg.this.userBean);
                                                UserObservable.getInstance().notifyObservers(new EventData(3));
                                            }
                                    );
                                }
                        );
                    }
                }).requestCode(101).intent(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI)).activity((AppCompatActivity) AccountMsg.this.getActivity()).build().startActivityForResult();
            }
        });
        this.binding.changePsw.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Presenter.getInstance().step2fragment(new EditPwd(), "editPwd");
            }
        });
//        binding.nickName.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                Presenter.getInstance().step2fragment(new EditNickName(), "editNickName");
//            }
//        });
        UserObservable.getInstance().register(new UserObserver<EventData>() {
            public void onUpdate(UserObservable<EventData> userObservable, EventData data) {
                switch (data.getEventType()) {
                    case 2:
                        AccountMsg.this.userBean = UserOption.getInstance().querryUser();
                        AccountMsg.this.binding.setUser(AccountMsg.this.userBean);
                        return;
                    default:
                        return;
                }
            }
        });
    }
}
