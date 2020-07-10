package com.wellee.rxjava.rx;


import androidx.annotation.NonNull;

import com.wellee.rxjava.rx.functions.Function;
import com.wellee.rxjava.rx.oprators.ObservableJust;
import com.wellee.rxjava.rx.oprators.ObservableMap;
import com.wellee.rxjava.rx.oprators.ObservableObserveOn;
import com.wellee.rxjava.rx.oprators.ObservableSubscribeOn;
import com.wellee.rxjava.rx.schedulers.Scheduler;

public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> just(T item) {
        return onAssembly(new ObservableJust<T>(item));
    }

    private static <T> Observable<T> onAssembly(Observable<T> source) {
        return source;
    }

    @Override
    public void subscribe(@NonNull Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);


    public <R> Observable<R> map(Function<T, R> function) {
        return onAssembly(new ObservableMap<>(this, function));
    }

    public Observable<T> subscribeOn(Scheduler scheduler) {
        return onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
    }


    public Observable<T> observeOn(Scheduler scheduler) {
        return onAssembly(new ObservableObserveOn<T>(this, scheduler));
    }
}
