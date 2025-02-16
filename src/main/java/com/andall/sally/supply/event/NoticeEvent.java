package com.andall.sally.supply.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 2:19 下午 2020/3/3
 */
@Slf4j
public class NoticeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 277529295943419873L;
    private final String message;

    public NoticeEvent(String message) {
        super(message);
        this.message = message;
        log.info("add event success, message:{}", message);
    }

    public String getMessage() {
        return this.message;
    }


}
