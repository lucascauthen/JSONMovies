package com.example.jsonmovies.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class BackgroundExecutor implements Executor {
    private final Executor executor;

    public BackgroundExecutor() {
        executor = Executors.newFixedThreadPool(10);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        executor.execute(command);
    }
}
