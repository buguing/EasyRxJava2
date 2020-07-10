package com.wellee.rxjava.rx.schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class IoScheduler extends Scheduler {

    private ExecutorService service;

    public IoScheduler() {
        service = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
    }

    @Override
    public void scheduleDirect(Runnable r) {
        service.execute(r);
    }
}
