package com.xcxcxcxcx.export.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author XCXCXCXCX
 * @date 2020/8/24 11:13 上午
 */
public class ConcurrentTestUtils {

    private final static Executor EXECUTOR = Executors.newCachedThreadPool();

    /**
     * 并发执行
     * @param runnable
     * @param threadNum
     */
    public static void concurrentExecute(Runnable runnable, int threadNum) {
        CyclicBarrier barrier = new CyclicBarrier(threadNum, () -> {
            System.out.println("并发线程准备就绪");
        });
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            EXECUTOR.execute(() -> {
                try {
                    barrier.await();
                    runnable.run();
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
