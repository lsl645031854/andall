package com.andall.sally.supply.current;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 6:33 下午 2020/3/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskPoolTest {

    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Callable<String> call = () -> {
            return "123";
        };

        Future<String> submit = asyncExecutor.submit(call);
        System.out.println(submit.get());
    }

}
