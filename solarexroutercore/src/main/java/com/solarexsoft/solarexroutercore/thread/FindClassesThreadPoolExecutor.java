package com.solarexsoft.solarexroutercore.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 18:25/2020/3/3
 *    Desc:
 * </pre>
 */

public class FindClassesThreadPoolExecutor {
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "solarex-find-classes-" + atomicInteger.getAndIncrement());
        };
    };

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MAX_CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final long IDLE_THREAD_TIME = 30;

    public static ThreadPoolExecutor newThreadPoolExecutor(int corePoolSize) {
        if (corePoolSize == 0) {
            return null;
        }
        corePoolSize = Math.min(corePoolSize, MAX_CORE_POOL_SIZE);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                corePoolSize,
                IDLE_THREAD_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(64),
                sThreadFactory
        );
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}
