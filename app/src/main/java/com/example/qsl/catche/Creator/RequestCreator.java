package com.example.qsl.catche.Creator;

import android.content.Context;
import com.example.qsl.bean.ImageBean;
import com.example.qsl.catche.catcheObservable.DiskCacheObservable;
import com.example.qsl.catche.catcheObservable.MemoryCacheObservable;
import com.example.qsl.catche.catcheObservable.NetworkCacheObservable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class RequestCreator {
    public DiskCacheObservable diskCacheObservable;
    public MemoryCacheObservable memoryCacheObservable = new MemoryCacheObservable();
    public NetworkCacheObservable networkCacheObservable;

    public RequestCreator(Context context) {
        this.diskCacheObservable = new DiskCacheObservable(context);
        this.networkCacheObservable = new NetworkCacheObservable();
    }

    public Observable<ImageBean> getImageFromMemory(String url) {
        return this.memoryCacheObservable.getImage(url).filter(new Predicate<ImageBean>() {
            public boolean test(@NonNull ImageBean imageBean) throws Exception {
                return imageBean.getBitmap() != null;
            }
        });
    }

    public Observable<ImageBean> getImageFromDisk(String url) {
        return this.diskCacheObservable.getImage(url).filter(new Predicate<ImageBean>() {
            public boolean test(@NonNull ImageBean imageBean) throws Exception {
                return imageBean.getBitmap() != null;
            }
        }).doOnNext(new Consumer<ImageBean>() {
            public void accept(@NonNull ImageBean imageBean) throws Exception {
                RequestCreator.this.memoryCacheObservable.putDataToCache(imageBean);
            }
        });
    }

    public Observable<ImageBean> getImageFromNetwork(String url) {
        return this.networkCacheObservable.getImage(url).filter(new Predicate<ImageBean>() {
            public boolean test(@NonNull ImageBean imageBean) throws Exception {
                return imageBean.getBitmap() != null;
            }
        }).doOnNext(new Consumer<ImageBean>() {
            public void accept(@NonNull ImageBean imageBean) throws Exception {
                RequestCreator.this.diskCacheObservable.putDataToCache(imageBean);
                RequestCreator.this.memoryCacheObservable.putDataToCache(imageBean);
            }
        });
    }
}
