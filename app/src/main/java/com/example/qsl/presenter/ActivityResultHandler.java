package com.example.qsl.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.qsl.Interface.HandlerStrategy;
import com.example.qsl.activity.ClipImageActivity;
import com.example.qsl.util.FileUtil;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ActivityResultHandler {
    // 处理各种界面跳转的问题
    private FragmentManager fragmentManager;
    public static final int REQUEST_SELECT_CONTACTS = 100;//联系人
    public static final int REQUEST_SELECT_PHOTO = 101;//选择图片
    public static final int REQUEST_CROP_PHOTO = 102; //裁剪图片呢
    public static final int REQUEST_ANALY_PHOTO = 103; //解析图片
    public static final int REQUEST_SCAN = 104;//扫一扫
    public static final int REQUEST_BACK = 105;
    public static final int REQUEST_TAKE_PHOTO = 106;
    public static final int REQUEST_SELECT_PHOTO_CHAT = 107;

    private int RESULT_OK = 0xA1;
    private Intent intent;
    private int requestCode;
    private AppCompatActivity activity;
    private HandlerStrategy handlerStrategy;

    private ActivityResultHandler() {
    }

    private static ActivityResultHandler defaultInstance;

    public static ActivityResultHandler getInstance() {
        ActivityResultHandler activityResultHandler = defaultInstance;
        if (defaultInstance == null) {
            synchronized (ActivityResultHandler.class) {
                if (defaultInstance == null) {
                    activityResultHandler = new ActivityResultHandler();
                    defaultInstance = activityResultHandler;
                }
            }
        }
        return activityResultHandler;
    }

    private void init(Builder builder) {
        this.activity = builder.activity;
        this.intent = builder.intent;
        this.requestCode = builder.requestCode;
        this.handlerStrategy = builder.handlerStrategy;
        fragmentManager = activity.getSupportFragmentManager();
    }

    private void init(AppCompatActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }


    public void startActivityForResult() {
        activity.startActivityForResult(intent, requestCode);
    }

    public static class Builder {
        private Intent intent;
        private int requestCode;
        private AppCompatActivity activity;
        private HandlerStrategy handlerStrategy;

        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder activity(AppCompatActivity activity) {
            this.activity = activity;
            return this;
        }

        public Builder hadlerStrategy(HandlerStrategy handlerStrategy) {
            this.handlerStrategy = handlerStrategy;
            return this;
        }

        public ActivityResultHandler build() {
            getInstance().init(this);
            return defaultInstance;
        }

        public ActivityResultHandler build2(AppCompatActivity activity) {
            getInstance().init(activity);
            return defaultInstance;
        }
    }


    public void handler(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
        }
        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
                if (uri == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(activity, ClipImageActivity.class);
                intent.putExtra("type", 2);//
                intent.setData(uri);
                activity.startActivityForResult(intent, REQUEST_CROP_PHOTO);
                break;
            case REQUEST_CROP_PHOTO:
                if (uri == null) {
                    return;
                }
                String cropImagePath = FileUtil.getRealFilePathFromUri(activity, uri);
                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                File file = new File(cropImagePath);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

                handlerStrategy.onActivityResult(filePart, bitMap);
                break;
            case REQUEST_TAKE_PHOTO: {
                handlerStrategy.onActivityResult();
            }
            break;
            case REQUEST_SELECT_PHOTO_CHAT:
                if (uri == null) {
                    return;
                }
                String imgPath = FileUtil.getRealFilePathFromUri(activity, uri);
                File imgFile = new File(imgPath);
                handlerStrategy.onActivityResult(imgFile);
                break;

        }

    }


}
