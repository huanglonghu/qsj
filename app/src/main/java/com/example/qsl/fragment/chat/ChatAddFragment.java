package com.example.qsl.fragment.chat;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qsl.Interface.HandlerStrategy;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.databinding.ChatBottomAddBinding;
import com.example.qsl.presenter.ActivityResultHandler.Builder;
import com.example.qsl.util.LogUtil;

import java.io.File;

public class ChatAddFragment extends BaseFragment {
    private ChatBottomAddBinding binding;
    private Chat chat;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.binding == null) {
            this.binding = (ChatBottomAddBinding) DataBindingUtil.inflate(inflater, R.layout.chat_bottom_add, container, false);
            initlisten();
        }
        return this.binding.getRoot();
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initlisten() {
        this.binding.photoGraph.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new Builder().hadlerStrategy(new HandlerStrategy() {
                    public void onActivityResult(File file) {
                        if (chat != null) {
                            LogUtil.log("=======sendImg========");
                            chat.sendImgMessage(file.getPath());
                        }
                    }
                }).requestCode(107).intent(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI)).activity((AppCompatActivity) ChatAddFragment.this.getActivity()).build().startActivityForResult();
            }
        });
        this.binding.takePhoto.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatAddFragment.this.goCamera();
            }
        });
    }

    private void goCamera() {
        Uri uri;
        final File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getActivity(), "com.example.qsl.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra("output", uri);
        new Builder().requestCode(106).hadlerStrategy(new HandlerStrategy() {
            public void onActivityResult() {
                String photoPath;
                if (VERSION.SDK_INT >= 24) {
                    photoPath = String.valueOf(cameraSavePath);
                } else {
                    photoPath = uri.getEncodedPath();
                }
                if (ChatAddFragment.this.chat != null) {
                    ChatAddFragment.this.chat.sendImgMessage(photoPath);
                }
            }
        }).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();
    }

    public void chat(Chat chat) {
        this.chat = chat;
    }
}
