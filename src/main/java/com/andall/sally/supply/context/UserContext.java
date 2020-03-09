package com.andall.sally.supply.context;

import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.entity.UserEntity;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:27 2020/3/9
 */
public class UserContext {

    private static final ThreadLocal<UserEntity> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(UserEntity user) {
        USER_HOLDER.set(user);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }

    public static UserEntity getUser() {
        return USER_HOLDER.get();
    }
}
