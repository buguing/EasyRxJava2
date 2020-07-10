package com.wellee.rxjava.rx.schedulers;

import android.os.Handler;
import android.os.Message;

public class MainScheduler extends Scheduler {

    private Handler handler;

    public MainScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void scheduleDirect(Runnable r) {
        Message message = Message.obtain(handler, r);
        handler.sendMessage(message);
    }
}
