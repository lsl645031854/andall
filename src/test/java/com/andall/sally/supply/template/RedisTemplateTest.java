package com.andall.sally.supply.template;

import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.entity.User;
import com.andall.sally.supply.entity.UserEntity;
import com.andall.sally.supply.mapper.UserEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 2:44 下午 2020/3/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserEntityMapper userEntityMapper;


    @Test
    public void testSelectOne() {
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(1L);
        System.out.println(userEntity);
    }


    @Test
    public void test() {
        Object aw_key = redisTemplate.opsForValue().get("My_Test_Key");
        System.out.println(aw_key);
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("My_Test_Key", "123", 5, TimeUnit.SECONDS);
    }

    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        redisTemplate.opsForList().leftPushAll("My_Test_Key", list);

        List my_test_key = redisTemplate.opsForList().range("My_Test_Key", 0, -1);
        my_test_key.forEach(System.out::println);

    }

    @Test
    public void testList1() {

        Long my_test_key1 = redisTemplate.opsForList().size("My_Test_Key");
        System.out.println(my_test_key1);

        Object my_test_key = redisTemplate.opsForList().index("My_Test_Key", 0);
        System.out.println(my_test_key);
    }

    @Test
    public void test3() {
        redisTemplate.delete("li1");
        //先进后出(左进左出)
        redisTemplate.opsForList().leftPush("li1", "a");
        redisTemplate.opsForList().leftPush("li1", "b");
        redisTemplate.opsForList().leftPush("li1", "c");
        List<Object> range = redisTemplate.opsForList().range("li1", 0, -1);
        for (Object object : range) {
            System.out.println(object);//c b a
        }
        redisTemplate.opsForList().leftPush("li1", "d");
        redisTemplate.opsForList().leftPush("li1", "e");
        redisTemplate.opsForList().leftPush("li1", "f");
        List<Object> range1 = redisTemplate.opsForList().range("li1", 0, -1);
        for (Object object : range1) {
            System.out.println(object);//f e d c b a
        }
    }

    @Test
    public void test4() {
        redisTemplate.opsForList().leftPush("li1", new User("tom", "123456", 12));
        redisTemplate.opsForList().leftPush("li1", new User("mary", "123456", 15));

        List<User> range1 = redisTemplate.opsForList().range("li1", 0, -1);
        range1.forEach(System.out::println);

        User li1 = (User) redisTemplate.opsForList().index("li1", 0);

        redisTemplate.opsForList().leftPop("li1");
        System.out.println(li1);
    }

    @Test
    public void test5() {
        User user1 = new User("tom", "123456", 12);
        User user2 = new User("mary", "123456", 15);

        stringRedisTemplate.opsForHash().put("li2", "user1", JSON.toJSONString(user1));
        stringRedisTemplate.opsForHash().put("li2", "user2", JSON.toJSONString(user2));

        Object o = stringRedisTemplate.opsForHash().get("li2", "user1");
        System.out.println(o);

        Map<Object, Object> li2 = stringRedisTemplate.opsForHash().entries("li2");
        System.out.println(li2);
    }

    @Test
    public void test6() {
        User user1 = new User("tom", "123456", 12);
        User user2 = new User("mary", "123456", 15);

        Map<String, Object> map = new HashMap<>();
        map.put("user1", user1);
        map.put("user2", user2);

        redisTemplate.opsForHash().putAll("li2", map);
        Map<Object, Object> li2 = stringRedisTemplate.opsForHash().entries("li2");
        System.out.println(li2);

        User user = (User) redisTemplate.opsForHash().get("li2", "user2");
        System.out.println(user);
    }

    @Test
    public void testExist() {

    }

}
