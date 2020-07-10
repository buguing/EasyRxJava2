package com.wellee.rxjava.rx;

import androidx.annotation.NonNull;

public interface ObservableSource<T> {

    void subscribe(@NonNull Observer<? super T> observer);
}
