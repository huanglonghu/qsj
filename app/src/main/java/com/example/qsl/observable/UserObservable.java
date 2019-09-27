package com.example.qsl.observable;

import java.util.ArrayList;
import java.util.List;

public class UserObservable<T> {

    private UserObservable() {
    }

    private static UserObservable defaultInstance;

    public static UserObservable getInstance() {
        UserObservable userObservable = defaultInstance;
        if (defaultInstance == null) {
            synchronized (UserObservable.class) {
                if (defaultInstance == null) {
                    userObservable = new UserObservable();
                    defaultInstance = userObservable;
                }
            }
        }
        return userObservable;
    }

    List<UserObserver<T>> mObservers = new ArrayList<UserObserver<T>>();

    public void register(UserObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mObservers.contains(observer))
                mObservers.add(observer);
        }
    }

    public synchronized void unregister(UserObserver observer) {
        mObservers.remove(observer);
    }

    public void notifyObservers(T data) {
        for (UserObserver<T> observer : mObservers) {
            observer.onUpdate(this, data);
        }
    }
}
