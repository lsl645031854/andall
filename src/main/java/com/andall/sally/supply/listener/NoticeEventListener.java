package com.andall.sally.supply.listener;

import com.andall.sally.supply.event.NoitceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Description: 多个监听器，触发可以有顺序， @order， 数值越小表示越优先
 * @Date: Created on 3:29 下午 2020/3/3
 */
@Component
@Slf4j
public class NoticeEventListener {

    @EventListener
    @Order(2)
    public void handler(NoitceEvent event) {
        log.info("触发");
    }

}
