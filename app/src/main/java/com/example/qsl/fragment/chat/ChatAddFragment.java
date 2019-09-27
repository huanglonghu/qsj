package com.example.qsl.fragment.chat;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.qsl.Interface.HandlerStrategy;
import com.example.qsl.R;
import com.example.qsl.base.BaseFragment;
import com.example.qsl.databinding.ChatBottomAddBinding;
import com.example.qsl.presenter.ActivityResultHandler;
import java.io.File;

public class ChatAddFragment extends BaseFragment {

    private ChatBottomAddBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(binding == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.chat_bottom_add, container, false);
            initlisten();
        }
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

        binding.photoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @Override
                    public void onActivityResult(File file) {
                        if (chat != null) {
                            chat.sendImgMessage(file.getPath());
                        }
                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO_CHAT).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();
            }
        });

        binding.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCamera();
            }
        });
    }


    //激活相机操作
    private void goCamera() {
        File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(getActivity(), "com.example.qsl.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        new ActivityResultHandler.Builder().requestCode(ActivityResultHandler.REQUEST_TAKE_PHOTO).hadlerStrategy(new HandlerStrategy() {
            @Override
            public void onActivityResult() {
                String photoPath;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath = String.valueOf(cameraSavePath);
                } else {
                    photoPath = uri.getEncodedPath();
                }
                if (chat != null) {
                    chat.sendImgMessage(photoPath);
                }
            }


        }).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();

    }






    private Chat chat;

    public void chat(Chat chat) {
        this.chat = chat;
    }


}
