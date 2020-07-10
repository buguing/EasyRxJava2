package com.wellee.rxjava.rx.functions;


import androidx.annotation.NonNull;

public interface Function<T, R> {

    R apply(@NonNull T t) throws Exception;
}
