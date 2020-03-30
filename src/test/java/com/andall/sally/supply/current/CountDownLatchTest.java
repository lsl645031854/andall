package com.andall.sally.supply.current;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 *
 * CyclicBarrier与CountDownLatch的区别：
 *
 * CountDownLatch阻塞的是主线程
 * CountDownLatch的计数器只能使用一次也就是只能递减
 * CyclicBarrier阻塞的是子线程
 * CyclicBarrier的计数器可以使用reset()方法重置
 *
 *
 * @Date: Created on 10:46 上午 2020/3/4
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
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
            System.out.println("进入t3 线程，并且等待...");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("t3线程进行后续的执行操作...");
        }, "t3");

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

        countDownLatch.await();
        System.out.println("操作结束");
        executorService.shutdown();
    }
}
