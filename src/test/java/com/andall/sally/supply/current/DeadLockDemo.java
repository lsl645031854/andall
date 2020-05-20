package com.andall.sally.supply.current;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:20 2020/11/8
 */
public class DeadLockDemo {

    public static void main(String[] args){
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(() -> {
            synchronized (lockA) {
                System.out.println("AAAA");
                try {
                    TimeUnit.MICROSECONDS.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (lockB) {
                    System.out.println("AAABBB");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println("BBBB");
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (lockA) {
                    System.out.println("BBBBAAA");
                }
            }
        }).start();
    }

}
