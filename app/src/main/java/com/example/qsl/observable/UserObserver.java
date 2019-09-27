package com.example.qsl.observable;

public interface UserObserver<T> {

    void onUpdate(UserObservable<T> observable, T data);
}
