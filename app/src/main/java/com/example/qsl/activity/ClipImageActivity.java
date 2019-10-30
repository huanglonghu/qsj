package com.example.qsl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qsl.R;
import com.example.qsl.customview.ClipViewLayout;
import com.example.qsl.util.LogUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ClipImageActivity extends AppCompatActivity implements OnClickListener {
    private ImageView back;
    private TextView btnCancel;
    private TextView btnOk;
    private ClipViewLayout clipViewLayout2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarFullTransparent();
        setContentView((int) R.layout.activity_clip_image);
        initView();
    }

    protected void setStatusBarFullTransparent() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
    }

    public void initView() {
        this.clipViewLayout2 = (ClipViewLayout) findViewById(R.id.clipViewLayout2);
        this.back = (ImageView) findViewById(R.id.iv_back);
        this.btnCancel = (TextView) findViewById(R.id.btn_cancel);
        this.btnOk = (TextView) findViewById(R.id.bt_ok);
        this.back.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        this.btnOk.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        LogUtil.log("=========uri============" + getIntent().getData());
        this.clipViewLayout2.setVisibility(View.VISIBLE);
        this.clipViewLayout2.setImageSrc(getIntent().getData());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.btn_cancel:
                finish();
                return;
            case R.id.bt_ok:
                generateUriAndReturn();
                return;
            default:
                return;
        }
    }

    private void generateUriAndReturn() {
        Bitmap zoomedCropBitmap = this.clipViewLayout2.clip();
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(CompressFormat.JPEG, 90, outputStream);
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(-1, intent);
            finish();
        }
    }
}
