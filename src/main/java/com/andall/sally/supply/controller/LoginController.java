package com.andall.sally.supply.controller;

import com.andall.sally.supply.common.RestResponse;
import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.event.NoitceEvent;
import com.andall.sally.supply.exception.UserException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("login")
    @ApiOperation("用户登陆")
    public RestResponse login(@RequestBody User user) {
        log.info("登陆用户：{}", user.toString());
        this.applicationEventPublisher.publishEvent(new NoitceEvent(user.getUserName()));
        return RestResponse.success(user.getUserName());
    }

}
