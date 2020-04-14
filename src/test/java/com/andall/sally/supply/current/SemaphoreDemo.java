package com.andall.sally.supply.current;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * @Author: lsl
 * @Description: 用信号量循环打印 ABC
 * @Date: Created on 11:20 2020/4/13
 */
public class SemaphoreDemo {
    private static Semaphore semaphoreA = new Semaphore(1);
    private static  Semaphore semaphoreB = new Semaphore(0);
    private static  Semaphore semaphoreC = new Semaphore(0);

    static class printDemo {
        void printA()  {
            print('A',semaphoreA,semaphoreB);
        }

        void printB(){
            print('B',semaphoreB,semaphoreC);
        }
        void printC(){
            print('C',semaphoreC,semaphoreA);
        }

        private void print(char name, Semaphore semaphore,Semaphore nextSemaphore) {
            try {
                for (int i = 0; i < 10; i++) {
                    semaphore.acquire();
                    System.out.println(name +" i:" + i + " ----> "+ Thread.currentThread().getName());
                    nextSemaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        final printDemo printDemo = new printDemo();
        new Thread(printDemo::printA).start();
        new Thread(printDemo::printB).start();
        new Thread(printDemo::printC).start();
    }

    @Test
    public void test() {

    }
}
