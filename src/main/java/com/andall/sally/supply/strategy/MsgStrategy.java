package com.andall.sally.supply.strategy;

import com.andall.sally.supply.annotation.MsgTypeEnum;
import com.andall.sally.supply.annotation.TypeAnnotation;
import com.andall.sally.supply.handle.BaseMsgHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 21:15 2020/11/19
 */
@Component
public class MsgStrategy implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static final Map<MsgTypeEnum, BaseMsgHandler> MSG_STRATEGY_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    private void initMsgStrategyMap() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(TypeAnnotation.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            TypeAnnotation annotation = entry.getValue().getClass().getAnnotation(TypeAnnotation.class);
            MsgTypeEnum msgTypeEnum = annotation.value();
            MSG_STRATEGY_MAP.put(msgTypeEnum, (BaseMsgHandler) entry.getValue());
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
