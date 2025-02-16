package com.andall.sally.supply.listener;

import com.andall.sally.supply.event.NoticeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl 嘎嘎嘎
 * @Description: 多个监听器，触发可以有顺序， @order， 数值越小表示越优先 哈哈哈
 * @Date: Created on 3:29 下午 2020/3/3
 */
@Component
@Slf4j
public class NoticeEventListener {

    @EventListener
    @Order(2)
    public void handler(NoticeEvent event) {
        log.info("触发");
    }

}
