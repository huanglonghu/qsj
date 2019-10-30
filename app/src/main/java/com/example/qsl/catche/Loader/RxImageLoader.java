package com.example.qsl.catche.Loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.catche.Creator.RequestCreator;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxImageLoader {

    static RxImageLoader singleton;
    private String mUrl;
    private RequestCreator requestCreator;

    //防止用户可以创建该对象
    private RxImageLoader(Builder builder) {
        requestCreator = new RequestCreator(builder.mContext);
    }

    public static RxImageLoader with(Context context) {
        if (singleton == null) {
            synchronized (RxImageLoader.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public RxImageLoader load(String url) {
        this.mUrl = url;
        return singleton;
    }

    public void into(final ImageView imageView, int type) {
        Observable
                .concat(
                        requestCreator.getImageFromMemory(mUrl),
                        requestCreator.getImageFromDisk(mUrl),
                        requestCreator.getImageFromNetwork(mUrl)
                )
                .first(new ImageBean(null, mUrl)).toObservable()
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        if (imageBean.getBitmap() != null) {
                            if (type == 1) {
                                imageView.setBackground(new BitmapDrawable(imageBean.getBitmap()));
                            } else {
                                imageView.setImageDrawable(new BitmapDrawable(imageBean.getBitmap()));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "onComplete");
                    }
                });
    }


    public Observable<ImageBean> getBitmap(String url) {

        return Observable.concat(
                requestCreator.getImageFromMemory(url),
                requestCreator.getImageFromDisk(url),
                requestCreator.getImageFromNetwork(url)
        ).first(new ImageBean(null, url)).toObservable();


    }


    public Bitmap getBitmapFromMemory(String mUrl) {
        Bitmap bitmapFromCache = requestCreator.memoryCacheObservable.getBitmapFromCache(mUrl);
        return bitmapFromCache;
    }

    public Bitmap getBitmapFromDisk(String mUrl) {
        ImageBean dataFromCache = requestCreator.diskCacheObservable.getDataFromCache(mUrl);
        return dataFromCache.getBitmap();
    }

    public void catche(ImageBean imageBean) {
        requestCreator.memoryCacheObservable.putDataToCache(imageBean);
    }


    public static class Builder {

        private Context mContext;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public RxImageLoader build() {
            return new RxImageLoader(this);
        }
    }
}
