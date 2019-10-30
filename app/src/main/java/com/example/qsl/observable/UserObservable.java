package com.example.qsl.observable;

import java.util.ArrayList;
import java.util.List;

public class UserObservable<T> {
    private static UserObservable defaultInstance;
    List<UserObserver<T>> mObservers = new ArrayList();

    private UserObservable() {
    }

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

    public void register(UserObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!this.mObservers.contains(observer)) {
                this.mObservers.add(observer);
            }
        }
    }

    public synchronized void unregister(UserObserver observer) {
        this.mObservers.remove(observer);
    }

    public void notifyObservers(T data) {
        for (UserObserver<T> observer : this.mObservers) {
            observer.onUpdate(this, data);
        }
    }
}
