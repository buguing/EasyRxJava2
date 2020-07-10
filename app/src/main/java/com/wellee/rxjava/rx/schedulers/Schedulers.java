package com.wellee.rxjava.rx.schedulers;

import android.os.Handler;
import android.os.Looper;

public abstract class Schedulers {

    private static final Scheduler IO;

    private static final Scheduler MAIN;

    static {
        IO = IoHolder.DEFAULT;
        MAIN = MainHolder.MAIN_THREAD;
    }

    static final class IoHolder {
        static final Scheduler DEFAULT = new IoScheduler();
    }

    static final class MainHolder {
        static final Scheduler MAIN_THREAD = new MainScheduler(new Handler(Looper.getMainLooper()));
    }

    public static Scheduler io() {
        return IO;
    }

    public static Scheduler mainThread() {
        return MAIN;
    }
}
