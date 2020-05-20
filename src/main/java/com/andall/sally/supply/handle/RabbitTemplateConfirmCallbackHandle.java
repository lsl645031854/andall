package com.andall.sally.supply.handle;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:23 2020/6/11
 */
@Component
public class RabbitTemplateConfirmCallbackHandle implements RabbitTemplate.ConfirmCallback {

    /**
     *
     * 实现ConfirmCallback并重写confirm(CorrelationData correlationData, boolean ack, String cause)回调方法，可以确认消息是否发送到Exchange。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识：" + correlationData);
        System.out.println("确认结果："+ ack);
        System.out.println("失败原因："+ cause);
    }
}
