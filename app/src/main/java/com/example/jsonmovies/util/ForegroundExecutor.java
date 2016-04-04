package com.example.jsonmovies.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;


public class ForegroundExecutor implements Executor {
    private final Executor executor;

    public ForegroundExecutor() {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        executor = new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                mainHandler.post(command);
            }
        };
    }

    @Override
    public void execute(@NonNull  Runnable command) {
        executor.execute(command);
    }
}
