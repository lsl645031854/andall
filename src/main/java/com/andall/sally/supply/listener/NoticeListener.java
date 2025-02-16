package com.andall.sally.supply.listener;

import com.andall.sally.supply.event.NoticeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description: 多个监听器，触发可以有顺序， @order， 数值越小表示越优先
 * @Date: Created on 2:24 下午 2020/3/3
 */
@Component
@Slf4j
@Order(1)
public class NoticeListener implements ApplicationListener<NoticeEvent> {

    @Override
    @Async
    public void onApplicationEvent(NoticeEvent noticeEvent) {
        log.info("onApplicationEvent will sleep 2s");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
       log.info("NoticeListener get event, message:{}", noticeEvent.getMessage());
    }
}
