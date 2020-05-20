package com.andall.sally.supply.handle;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:25 2020/6/11
 */
@Component
public class RabbitTemplateReturnCallbackHandle implements RabbitTemplate.ReturnCallback {

    /**
     * 实现ReturnCallback并重写returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey)回调方法，
     * 可以确认消息从EXchange路由到Queue失败。注意：这里的回调是一个失败回调，只有消息从Exchange路由到Queue失败才会回调这个方法。
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message : "+message);
        System.out.println("消息主体 message : "+replyCode);
        System.out.println("描述："+replyText);
        System.out.println("消息使用的交换器 exchange : "+exchange);
        System.out.println("消息使用的路由键 routing : "+routingKey);
    }
}
