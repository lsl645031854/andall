package com.andall.sally.supply.service.impl;

import com.andall.sally.supply.entity.UserEntity;
import com.andall.sally.supply.mapper.UserEntityMapper;
import com.andall.sally.supply.service.UserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 18:13 2020/3/11
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public List<UserEntity> getAll(int pageNum) {
        AtomicInteger pageN = new AtomicInteger(pageNum);
        PageHelper.startPage(pageN.intValue(), 100);
        List<UserEntity> all = userEntityMapper.getAll();
        log.info("第{}次循环，处理数据量:{}", pageNum, all.size());
        int i = pageN.incrementAndGet();
        if (all.size() == 100) {
            getAll(i);
        }
        return null;
    }
}
