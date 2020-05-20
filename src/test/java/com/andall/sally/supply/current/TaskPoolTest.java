package com.andall.sally.supply.current;

import com.andall.sally.supply.annotation.MsgTypeEnum;
import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.handle.BaseMsgHandler;
import com.andall.sally.supply.strategy.MsgStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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

    @Autowired
    DataSource dataSource;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Callable<String> call = () -> "123";

        Future<String> submit = asyncExecutor.submit(call);
        System.out.println(submit.get());

        ThreadPoolExecutor threadPoolExecutor = asyncExecutor.getThreadPoolExecutor();
        System.out.println("提交任务数"+threadPoolExecutor.getTaskCount());
        System.out.println("完成任务数"+threadPoolExecutor.getCompletedTaskCount() );
        System.out.println("当前有"+threadPoolExecutor.getActiveCount()+"个线程正在处理任务");
        System.out.println("还剩"+threadPoolExecutor.getQueue().size()+"个任务");
        System.out.println("当前可用队列长度"+threadPoolExecutor.getQueue().remainingCapacity());

//        asyncExecutor.shutdown();
        Callable<String> call1 = () -> "456";
        Future<String> submit1 = asyncExecutor.submit(call1);
        System.out.println(submit1.get());
    }

    @Test
    public void testStrategy() throws Exception {
        MsgTypeEnum msgTypeEnum = MsgTypeEnum.getMsgEnum("chatMsg");
        if (msgTypeEnum == null) {
            System.out.println("类型不存在......");
            return;
        }
        BaseMsgHandler handler = MsgStrategy.MSG_STRATEGY_MAP.get(msgTypeEnum);
        handler.handleMsg("哈哈哈哈哈");
    }

    @Test
    public void test1() {
        User user = new User();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            asyncExecutor.submit(() -> user.setAge(finalI));
            asyncExecutor.shutdown();
        }
        System.out.println(user.getAge());
    }

    @Test
    public void testDb() throws SQLException {
        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
    }
}
