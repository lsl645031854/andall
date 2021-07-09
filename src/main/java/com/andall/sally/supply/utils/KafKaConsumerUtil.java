package com.andall.sally.supply.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:47 下午 2021/7/7
 */
@Component
@Slf4j
public class KafKaConsumerUtil {

    @KafkaListener(topics = { "test"}, containerFactory = "notifyKafkaListenerContainerFactory")
    public void consumeMsg(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info("开始kafka消费: {}", record.value().toString());
    }


}
