package com.wellee.rxjava.rx.schedulers;

public abstract class Scheduler {

    public abstract void scheduleDirect(Runnable r);
}
