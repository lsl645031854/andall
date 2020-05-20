package com.andall.sally.supply.mapper;

import com.andall.sally.supply.entity.UserGroupEntity;
import com.andall.sally.supply.entity.UserGroupEntityKey;

import java.util.List;

public interface UserGroupEntityMapper {
    int deleteByPrimaryKey(UserGroupEntityKey key);

    int insert(UserGroupEntity record);

    int insertSelective(UserGroupEntity record);

    UserGroupEntity selectByPrimaryKey(UserGroupEntityKey key);

    int updateByPrimaryKeySelective(UserGroupEntity record);

    int updateByPrimaryKey(UserGroupEntity record);

    List<UserGroupEntity> selectAll();
}