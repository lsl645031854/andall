package com.andall.sally.supply.service.impl;

import com.andall.sally.supply.utils.ThreadTaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.FutureTask;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 17:01 2020/9/29
 */
@Service
@Slf4j
public class LogService {
    public void log() {
        log.debug("==============================================");
        ThreadTaskUtils.run(this::run);
        FutureTask<String> futureTask = new FutureTask<String>(this::call);
        ThreadTaskUtils.run(futureTask);
        try {
            log.debug("===================: {}", futureTask.get());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("==============================================");
    }

    private String call() {
        log.debug("11111111111");
        return "3333";
    }

    private void run() {
        log.debug("222222222222222");
    }

}
