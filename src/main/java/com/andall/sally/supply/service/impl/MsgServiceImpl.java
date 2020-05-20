package com.andall.sally.supply.service.impl;

import com.andall.sally.supply.constant.MqConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:03 2020/3/11
 */
@Service
@Slf4j
public class MsgServiceImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(
            containerFactory = "multiListenerContainer",
            bindings = @QueueBinding(
                    value = @Queue(value = MqConstant.IMMEDIATE_NODE_QUEUE, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = MqConstant.IMMEDIATE_EXCHANGE_NODE,
                            // 延时发送 利用插件实现
                            type = "x-delayed-message",
                            arguments = {
                                    @Argument(name = "x-delayed-type", value = "topic")}),
//                    非延时的消息交换机
//                    exchange = @Exchange(value = MqConstant.IMMEDIATE_EXCHANGE_NODE, type = ExchangeTypes.TOPIC),
                    key = MqConstant.IMMEDIATE_KEY_NODE_ROUTING_KEY
            )
    )
    public void msgReceive(Map<String, String> content, Channel channel, Message message) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取转发重试次数
        Object count = messageProperties.getHeader("count");
//        int i = 1 / 0;
//        System.out.println(count);
        log.info("收到消息：{}", content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

//        testRetry(content, (int) count);
    }

    private void testRetry(Map<String, String> content, int count) {
        if (count < 3) {
            this.rabbitTemplate.convertAndSend(MqConstant.IMMEDIATE_EXCHANGE_NODE, MqConstant.IMMEDIATE_KEY_NODE_ROUTING_KEY, content, message1 -> {
                message1.getMessageProperties().setDelay(1000);
                message1.getMessageProperties().setHeader("count", count + 1);
                return message1;
            });
        }
    }
}
