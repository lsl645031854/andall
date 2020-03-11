package com.andall.sally.supply.controller;

import com.andall.sally.supply.common.RestResponse;
import com.andall.sally.supply.constant.MqConstant;
import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.entity.UserEntity;
import com.andall.sally.supply.event.NoitceEvent;
import com.andall.sally.supply.exception.UserException;
import com.andall.sally.supply.req.SearchReq;
import com.andall.sally.supply.service.UserService;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lsl
 * @Description: 登陆
 * @Date: Created on 10:40 上午 2020/2/29
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "登陆")
@Slf4j
public class LoginController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("login")
    @ApiOperation("用户登陆")
    public RestResponse login(@RequestBody User user) {
        log.info("登陆用户：{}", user.toString());
        this.applicationEventPublisher.publishEvent(new NoitceEvent(user.getUserName()));
        return RestResponse.success(user.getUserName());
    }

    @PostMapping("msg")
    @ApiOperation("发送消息")
    public RestResponse sendMsg(@RequestBody User user) {
        Map<String, String> param = Maps.newHashMap();
        param.put("name", user.getUserName());
        log.info("发送一条消息。。。。。");
        this.rabbitTemplate.convertAndSend(MqConstant.IMMEDIATE_EXCHANGE_NODE, MqConstant.IMMEDIATE_KEY_NODE_ROUTING_KEY, param, message -> {
            message.getMessageProperties().setDelay(10000);
            return message;
        });
        return RestResponse.success();
    }

    @PostMapping("all")
    @ApiOperation("获取全部用户")
    public RestResponse getAll() {
        userService.getAll(1);
        return RestResponse.success();
    }

}
