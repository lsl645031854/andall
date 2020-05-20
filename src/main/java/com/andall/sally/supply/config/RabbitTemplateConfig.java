package com.andall.sally.supply.config;

import com.andall.sally.supply.handle.RabbitTemplateConfirmCallbackHandle;
import com.andall.sally.supply.handle.RabbitTemplateReturnCallbackHandle;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:29 2020/6/11
 */
@Configuration
public class RabbitTemplateConfig {

    @Autowired
    private Environment env;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;


    @Bean("multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        this.factoryConfigurer.configure(factory, this.connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers((Integer)this.env.getProperty("spring.rabbitmq.listener.concurrency", Integer.TYPE));
        factory.setMaxConcurrentConsumers((Integer)this.env.getProperty("spring.rabbitmq.listener.max-concurrency", Integer.TYPE));
        factory.setPrefetchCount((Integer)this.env.getProperty("spring.rabbitmq.listener.prefetch", Integer.TYPE));
        return factory;
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setConfirmCallback(new RabbitTemplateConfirmCallbackHandle());
        template.setMandatory(true);
        template.setReturnCallback(new RabbitTemplateReturnCallbackHandle());
//        template.setRetryTemplate(attemptsRetry());
        return template;
    }

}
