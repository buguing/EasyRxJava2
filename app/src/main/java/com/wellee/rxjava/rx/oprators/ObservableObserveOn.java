package com.wellee.rxjava.rx.oprators;

import androidx.annotation.NonNull;

import com.wellee.rxjava.rx.Observable;
import com.wellee.rxjava.rx.Observer;
import com.wellee.rxjava.rx.schedulers.Scheduler;

public final class ObservableObserveOn<T> extends Observable<T> {

    private final Observable<T> source;
    private final Scheduler scheduler;

    public ObservableObserveOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        source.subscribe(new ObserveOnObserver<T>(observer, scheduler));
    }

    private static class ObserveOnObserver<T> implements Observer<T>, Runnable {

        private Observer<? super T> observer;
        private Scheduler scheduler;
        private T value;

        public ObserveOnObserver(Observer<? super T> observer, Scheduler scheduler) {
            this.observer = observer;
            this.scheduler = scheduler;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onError(@NonNull Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public void onNext(@NonNull T item) {
            this.value = item;
            scheduler.scheduleDirect(this);
        }

        @Override
        public void run() {
            observer.onNext(value);
        }
    }
}
