package com.example.qsl.Interface;

import android.graphics.Bitmap;
import java.io.File;
import okhttp3.MultipartBody.Part;

public abstract class HandlerStrategy {
    public void onActivityResult(String text) {
    }

    public void onActivityResult(Part filePart, Bitmap bitmap) {
    }

    public void onActivityResult() {
    }

    public void onActivityResult(File file) {
    }
}
