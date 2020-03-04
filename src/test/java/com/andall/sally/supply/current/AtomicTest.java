package com.andall.sally.supply.current;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:00 下午 2020/3/4
 */
public class AtomicTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private ExecutorService service = Executors.newFixedThreadPool(5);
    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            service.submit(this::incr);
        }
        System.out.println(atomicInteger.get());
        service.shutdown();
    }

    private int incr() {
        return atomicInteger.incrementAndGet();
    }
}
