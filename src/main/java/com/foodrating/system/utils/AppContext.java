package com.foodrating.system.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContext {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void shutdownExecutor() {
        executorService.shutdown();
    }
}
