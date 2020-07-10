package com.wellee.rxjava.rx;

import androidx.annotation.NonNull;

public interface Observer<T> {

    void onSubscribe();

    void onNext(@NonNull T item);

    void onError(@NonNull Throwable e);

    void onComplete();
}
