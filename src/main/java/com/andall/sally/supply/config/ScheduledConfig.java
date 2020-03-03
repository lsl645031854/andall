package com.andall.sally.supply.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务并行执行
 **/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(asyncExecutor);
    }

}
