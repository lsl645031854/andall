package com.andall.sally.supply.service;

import com.andall.sally.supply.entity.UserEntity;

import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 18:12 2020/3/11
 */
public interface UserService {
    List<UserEntity> getAll(int pageNum);
}
