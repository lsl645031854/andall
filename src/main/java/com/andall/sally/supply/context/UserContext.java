package com.andall.sally.supply.context;

import com.andall.sally.supply.entity.User;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:27 2020/3/9
 */
public class UserContext {

    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_HOLDER.set(user);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }

    public static User getUser() {
        return USER_HOLDER.get();
    }
}
