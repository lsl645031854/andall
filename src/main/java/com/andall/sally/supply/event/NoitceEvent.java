package com.andall.sally.supply.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 2:19 下午 2020/3/3
 */
@Slf4j
public class NoitceEvent extends ApplicationEvent {

    private String message;

    public NoitceEvent(String message) {
        super(message);
        this.message = message;
        log.info("add event success, message:{}", message);
    }

    public String getMessage() {
        return this.message;
    }


}
