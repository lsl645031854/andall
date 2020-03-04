package com.andall.sally.supply.current;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:46 上午 2020/3/4
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.submit(() -> {
            System.out.println("进入t1线程。。。");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("t1线程初始化完毕，通知t3线程继续操作！");
            countDownLatch.countDown();
        }, "t1");

        executorService.submit(() -> {
            System.out.println("进入t2线程。。。");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("t2线程初始化完毕，通知t3线程继续操作！");
            countDownLatch.countDown();
        }, "t2");

        executorService.submit(() -> {
            System.out.println("进入t3 线程，并且等待...");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("t3线程进行后续的执行操作...");
        }, "t3");

        executorService.shutdown();
    }
}
