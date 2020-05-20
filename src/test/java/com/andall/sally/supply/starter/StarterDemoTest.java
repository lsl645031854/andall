package com.andall.sally.supply.starter;

import com.andall.starter.service.LuckyNumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:13 2020/6/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StarterDemoTest {

    @Autowired
    private LuckyNumService luckyNumService;

    @Test
    public void testDemo() {
        String sally = luckyNumService.getLuckyNum("sally");
        System.out.println(sally);
    }
}
