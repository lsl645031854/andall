package com.andall.sally.supply.fastJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.andall.sally.supply.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:15 下午 2020/3/3
 */
@Slf4j
public class FastJsonTest {

    @Test
    public void test1() {
        User user = User.builder().userName("tom").passWord("asdf").build();
        String jsonString = JSON.toJSONString(user);
        log.info(jsonString);
    }

    @Test
    public void test2() {
        User user = User.builder().userName("tom").passWord("asdf").build();
        String jsonString = JSON.toJSONString(user);
        User parseObject = JSON.parseObject(jsonString, User.class);
        log.info(parseObject.toString());
    }

    @Test
    public void test3() {
        List<User> list = Lists.newArrayList();
        list.add(new User("tom", "111"));
        list.add(new User("mary", "222"));
        list.add(new User("rose", "333"));
        String s = JSON.toJSONString(list);
        System.out.println(s);
        List<String> list1 = JSON.parseArray(s, String.class);
        System.out.println(list1.size());
    }

    @Test
    public void test4() {
        Map<String, User> map = new HashMap<>();
        map.put("1", new User("tom", "111"));
        map.put("2", new User("mary", "222"));
        String s = JSON.toJSONString(map);
        System.out.println(s);
        Map map1 = JSON.parseObject(s, Map.class);
        map1.forEach((k, v) -> {
            String s1 = JSON.toJSONString(v);
            User user = JSON.parseObject(s1, User.class);
            System.out.println(user);
        });
    }
}
