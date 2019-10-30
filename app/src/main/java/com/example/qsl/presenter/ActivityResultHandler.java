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
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

public class ActivityResultHandler {
    public static final int REQUEST_ANALY_PHOTO = 103;
    public static final int REQUEST_BACK = 105;
    public static final int REQUEST_CROP_PHOTO = 102;
    public static final int REQUEST_SCAN = 104;
    public static final int REQUEST_SELECT_CONTACTS = 100;
    public static final int REQUEST_SELECT_PHOTO = 101;
    public static final int REQUEST_TAKE_PHOTO = 106;
    public static final int REQUEST_SELECT_PHOTO_CHAT = 107;
    public static final int REQUEST_SELECT_PHOTO_QRCODE = 108;
    private static ActivityResultHandler defaultInstance;
    private int RESULT_OK = 161;
    private AppCompatActivity activity;
    private FragmentManager fragmentManager;
    private HandlerStrategy handlerStrategy;
    private Intent intent;
    private int requestCode;

    public static class Builder {
        private AppCompatActivity activity;
        private HandlerStrategy handlerStrategy;
        private Intent intent;
        private int requestCode;

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
            ActivityResultHandler.getInstance().init(this);
            return ActivityResultHandler.defaultInstance;
        }

        public ActivityResultHandler build2(AppCompatActivity activity) {
            ActivityResultHandler.getInstance().init(activity);
            return ActivityResultHandler.defaultInstance;
        }
    }

    private ActivityResultHandler() {
    }

    public static ActivityResultHandler getInstance() {
        ActivityResultHandler activityResultHandler = defaultInstance;
        if (defaultInstance == null) {
            synchronized (ActivityResultHandler.class) {
                if (defaultInstance == null) {
                    ActivityResultHandler activityResultHandler2 = new ActivityResultHandler();
                    defaultInstance = activityResultHandler2;
                    activityResultHandler = activityResultHandler2;
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
        this.fragmentManager = this.activity.getSupportFragmentManager();
    }

    private void init(AppCompatActivity activity) {
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public void startActivityForResult() {
        this.activity.startActivityForResult(this.intent, this.requestCode);
    }

    public void handler(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
        }
        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
                if (uri != null) {
                    Intent intent = new Intent();
                    intent.setClass(this.activity, ClipImageActivity.class);
                    intent.putExtra("type", 2);
                    intent.setData(uri);
                    this.activity.startActivityForResult(intent, 102);
                    return;
                }
                return;
            case REQUEST_CROP_PHOTO :
                if (uri != null) {
                    String cropImagePath = FileUtil.getRealFilePathFromUri(this.activity, uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    File file = new File(cropImagePath);
                    this.handlerStrategy.onActivityResult(Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)), bitMap);

                    return;
                }
                return;
            case REQUEST_TAKE_PHOTO :
                this.handlerStrategy.onActivityResult();
                return;
            case REQUEST_SELECT_PHOTO_CHAT:
                if (uri != null) {
                    this.handlerStrategy.onActivityResult(new File(FileUtil.getRealFilePathFromUri(this.activity, uri)));
                    return;
                }
                return;
            case REQUEST_SELECT_PHOTO_QRCODE:
                if (uri != null) {
                    String cropImagePath = FileUtil.getRealFilePathFromUri(this.activity, uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    File file = new File(cropImagePath);
                    this.handlerStrategy.onActivityResult(Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)), bitMap);
                    return;
                }
                break;

        }
    }
}
