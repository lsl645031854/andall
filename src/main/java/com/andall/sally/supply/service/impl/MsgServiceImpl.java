package com.andall.sally.supply.service.impl;

import com.andall.sally.supply.constant.MqConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:03 2020/3/11
 */
@Service
@Slf4j
public class MsgServiceImpl {

    @RabbitListener(
            containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = MqConstant.IMMEDIATE_NODE_QUEUE, durable = "true", autoDelete = "false"),
                    exchange = @Exchange(value = MqConstant.IMMEDIATE_EXCHANGE_NODE,
                            // 延时发送 利用插件实现
                            type = "x-delayed-message",
                            arguments = {
                                    @Argument(name = "x-delayed-type", value = "topic")}),
                    key = MqConstant.IMMEDIATE_KEY_NODE_ROUTING_KEY
            )
    )
    public void msgReceive(Map<String, String> content, Channel channel, Message message) throws IOException {
        log.info("收到消息：{}", content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
