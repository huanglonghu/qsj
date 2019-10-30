package com.example.qsl.bean;

import android.graphics.Bitmap;

public class ImageBean {
    private Bitmap bitmap;
    private String url;

    public ImageBean(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
