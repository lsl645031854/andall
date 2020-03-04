package com.andall.sally.supply.current;

import java.util.concurrent.*;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:22 上午 2020/3/4
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CyclicBarrier cb = new CyclicBarrier(2); // 阻塞子线程，全部到达某个状态后执行
        CountDownLatch cd = new CountDownLatch(2); // 阻塞主线程。减到0，主线程执行

        executorService.submit(() -> {
            System.out.println("第一个线程准备执行");
            try {
                cb.await();
                cd.countDown();
                System.out.println("第一个线程执行");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            System.out.println("第二个线程准备执行");
            try {
                cb.await();
                cd.countDown();
                System.out.println("第二个线程执行");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        cd.await();
        System.out.println("主线程");
    }
}
