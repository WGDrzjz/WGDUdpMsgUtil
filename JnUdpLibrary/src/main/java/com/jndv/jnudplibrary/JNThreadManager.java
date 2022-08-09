package com.jndv.jnudplibrary;

import android.os.Handler;
import android.os.Looper;

import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;

public final class JNThreadManager {

    private final static EasyThread io;//
    public static EasyThread getIO () {
        return io;
    }

    static {
        io = EasyThread.Builder.createFixed(6).setName("IO").setPriority(7).setCallback(new DefaultCallback()).build();
    }

    private static class DefaultCallback implements Callback {

        @Override
        public void onError(String threadName, Throwable t) {

        }

        @Override
        public void onCompleted(String threadName) {

        }

        @Override
        public void onStart(String threadName) {

        }
    }

    public static void onMainHandler(Runnable runnable){
        new Handler(Looper.getMainLooper()).post(runnable);
    }

}
