package com.andall.sally.supply.utils;

import com.andall.sally.supply.config.KafkaProperties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:12 下午 2021/7/7
 */
@Component
public class KafkaSendUtil {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSendUtil.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaProperties kafkaProperties;

    public void sendMessage(String topic, String data) {
        logger.info("KafkaSendUtil.sendMessage topic:{}, data:{}", topic, data);
        Map<String, String> context = MDC.getCopyOfContextMap();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                MDC.setContextMap(context);
                logger.info("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
                MDC.clear();
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                MDC.setContextMap(context);
                logger.info("kafka sendMessage success topic = {}, data = {}", topic, data);
                MDC.clear();
            }
        });
    }

    public boolean checkKafka() {
        try {
            Properties pro1 = new Properties();
            pro1.put("bootstrap.servers", kafkaProperties.getHost());
            pro1.put("key.serializer", StringSerializer.class.getName());
            pro1.put("value.serializer", StringSerializer.class.getName());
            pro1.put("max.block.ms", 3000); // 超时时间
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(pro1);
            List<PartitionInfo> partitions = producer.partitionsFor("TOPIC_MP_POINT_1");
            logger.info("生产者创建OK，partitionSize={}", partitions.size());
            producer.close();

            Properties pro2 = new Properties();
            pro2.put("bootstrap.servers", kafkaProperties.getHost());
            pro2.put("request.timeout.ms", 3000); // 超时时间
            pro2.put("session.timeout.ms", 2000);
            pro2.put("fetch.max.wait.ms", 2000);
            pro2.put("heartbeat.interval.ms", 1000);
            pro2.put("key.deserializer", StringDeserializer.class.getName());
            pro2.put("value.deserializer", StringDeserializer.class.getName());
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(pro2);
            Set<String> topics = consumer.listTopics().keySet();
            logger.info("消费者创建OK，topicSize={}", topics.size());
            consumer.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
