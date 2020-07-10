package com.wellee.rxjava.rx.oprators;

import androidx.annotation.NonNull;

import com.wellee.rxjava.rx.functions.Function;
import com.wellee.rxjava.rx.Observable;
import com.wellee.rxjava.rx.Observer;

public class ObservableMap<T, R> extends Observable<R> {

    private Observable<T> source;
    private Function<? super T, ? extends R> function;

    public ObservableMap(Observable<T> source, Function<? super T, ? extends R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }

    private static class MapObserver<T, R> implements Observer<T> {

        private Observer<? super R> observer;
        private Function<? super T, ? extends R> function;

        public MapObserver(Observer<? super R> observer, Function<? super T, ? extends R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(@NonNull T item) {
            try {
                R applyR = function.apply(item);
                observer.onNext(applyR);
            } catch (Throwable e) {
                observer.onError(e);
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
