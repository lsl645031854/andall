package com.andall.sally.supply.current;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: lsl
 * @Description: ReentrantLock  Condition
 *      顺序打印 AAA BBB CCC
 * @Date: Created on 10:31 2020/8/31
 */

class Print {
    private Integer num = 1; // 标志位 1:打印A 2：打印B 3：打印C
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    void print5() {
        try {
            lock.lock();
            while (num != 1) {
                c1.await();
            }
            num = 2;
            c2.signal();
            System.out.println(Thread.currentThread().getName() + "\t AAA");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void print10 () {
        try {
            lock.lock();
            while (num != 2) {
                c2.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t BBB");
            num = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void print15 () {
        try {
            lock.lock();
            while (num != 3) {
                c3.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t CCC");
            num = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void commonPrint(String str) {
        int currentNum = 0;
        int nextNum = 0;
        Condition currentCondition = null;
        Condition nextCondition = null;
        try {
            lock.lock();
            if (str.equalsIgnoreCase("AAA")) {
                currentNum = 1;
                nextNum = 2;
                currentCondition = c1;
                nextCondition = c2;
            } else if (str.equalsIgnoreCase("BBB")) {
                currentNum = 2;
                nextNum = 3;
                currentCondition = c2;
                nextCondition = c3;
            } else if (str.equalsIgnoreCase("CCC")) {
                currentNum = 3;
                nextNum = 1;
                currentCondition = c3;
                nextCondition = c1;
            }
            while (num != currentNum) {
                assert currentCondition != null;
                currentCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t " + str);
            num = nextNum;
            assert nextCondition != null;
            nextCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ConditionDemo {

    public static void main(String[] args) {
        Print print = new Print();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
//                print.print5();
                print.commonPrint("AAA");
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
//                print.print10();
                print.commonPrint("BBB");
            }
        }, "t2").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
//                print.print15();
                print.commonPrint("CCC");
            }
        }, "t3").start();
    }
}
