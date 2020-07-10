package com.wellee.rxjava.rx.oprators;

import android.util.Log;

import com.wellee.rxjava.rx.Observable;
import com.wellee.rxjava.rx.Observer;
import com.wellee.rxjava.rx.schedulers.Scheduler;

public class ObservableSubscribeOn<T> extends Observable<T> {

    private final Observable<T> source;
    private final Scheduler scheduler;

    public ObservableSubscribeOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        scheduler.scheduleDirect(new SchedulerTask<>(source, observer));
    }

    private static class SchedulerTask<T> implements Runnable {

        private Observable<T> source;
        private Observer<? super T> observer;

        public SchedulerTask(Observable<T> source, Observer<? super T> observer) {
            this.source = source;
            this.observer = observer;
        }

        @Override
        public void run() {
            Log.e("ObservableObserveOn", "thread name = " + Thread.currentThread().getName());
            source.subscribe(observer);
        }
    }
}
