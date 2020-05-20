package com.andall.sally.supply.current;

class AirConditioner {
    private int number = 0;

    synchronized void increment() throws Exception {

        while (number != 0) {
            this.wait();
        }

        number++;

        System.out.println(Thread.currentThread().getName() + "\t" + number);

        this.notifyAll();
    }

    synchronized void decrement() throws Exception {
        while (number == 0) {
            this.wait();
        }

        number--;

        System.out.println(Thread.currentThread().getName() + "\t" + number);

        this.notifyAll();
    }
}
/**
 * @Author: lsl
 * @Description: 线程间通信
 * 高内聚低耦合，线程操作资源类
 * 判断干活通知
 * 多线程交互中，必须防止多线程的虚假唤醒 即判断使用while不能用if
 * @Date: Created on 17:19 2020/8/9
 */
public class ThreadWaitNotifyDemo {

    public static void main(String[] args)  {
        AirConditioner airConditioner = new AirConditioner();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BBB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CCC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "DDD").start();
    }


}
