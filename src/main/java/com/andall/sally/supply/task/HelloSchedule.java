package com.andall.sally.supply.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:47 下午 2020/3/3
 */
@Component
@Slf4j
public class HelloSchedule {

    /**
     * 每分钟执行.
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeTask() {
        log.info("11111");
    }
}
