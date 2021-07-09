package com.andall.sally.supply.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties(KafkaProperties.class)
@Configuration
@EnableKafka
public class KafkaConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private KafkaProperties kafkaProperties;

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // 连接kafka主机
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getHost()); // 192.168.49.212:9092
        // 发送失败不重试
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        // 当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算（而不是消息个数）
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 该参数指定了生产者在发送批次之前等待更多消息加入批次的时间。KafkaProducer 会在批次填满或 linger.ms
        // 达到上限时把批次发送出去。
        // 单位ms，默认0
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 缓冲区大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    private Map<String, Object> customerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // 连接kafka主机
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getHost());
        // 自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 自动提交间隔(单位ms，默认5000)
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 4);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // notify部署多台服务器，如果对于kafka里面的一条消息，notify每台服务器都需要消费，这里需要配多个groupid
        // (一般不需要)
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumerGroupId());
        return props;
    }

    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(customerConfigs());
    }

    @Bean(name = "notifyKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(4);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> createKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Kafka配置 ==== HOST ==== " + kafkaProperties.getHost());
    }

}
