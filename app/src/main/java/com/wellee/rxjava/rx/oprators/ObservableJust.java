package com.wellee.rxjava.rx.oprators;

import com.wellee.rxjava.rx.Observable;
import com.wellee.rxjava.rx.Observer;

public class ObservableJust<T> extends Observable<T> {

    private T item;

    public ObservableJust(T item) {
        this.item = item;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ScalarDisposable<T> sd = new ScalarDisposable<>(observer, item);
        observer.onSubscribe();
        sd.run();
    }

    private static class ScalarDisposable<T> {

        private Observer<? super T> observer;
        private T item;

        public ScalarDisposable(Observer<? super T> observer, T item) {
            this.observer = observer;
            this.item = item;
        }


        public void run() {
            try {
                observer.onNext(item);
                observer.onComplete();
            } catch (Throwable e) {
                observer.onError(e);
            }
        }
    }
}
