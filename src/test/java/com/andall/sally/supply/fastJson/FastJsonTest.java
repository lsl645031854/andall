package com.andall.sally.supply.fastJson;

import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

//    @Test
//    public void test3() {
//        List<User> list = Lists.newArrayList();
//        list.add(new User("tom", "111"));
//        list.add(new User("mary", "222"));
//        list.add(new User("rose", "333"));
//        String s = JSON.toJSONString(list);
//        System.out.println(s);
//        List<String> list1 = JSON.parseArray(s, String.class);
//        System.out.println(list1.size());
//    }
//
//    @Test
//    public void test4() {
//        Map<String, User> map = new HashMap<>();
//        map.put("1", new User("tom", "111"));
//        map.put("2", new User("mary", "222"));
//        String s = JSON.toJSONString(map);
//        System.out.println(s);
//        Map map1 = JSON.parseObject(s, Map.class);
//        map1.forEach((k, v) -> {
//            String s1 = JSON.toJSONString(v);
//            User user = JSON.parseObject(s1, User.class);
//            System.out.println(user);
//        });
//    }

    @Test
    public void test5() {
       List<String> list = new ArrayList<>();
       list.add("11");
       list.add("");
        long count = list.stream().filter(StringUtils::isBlank).count();
        System.out.println(count);
    }

    @Test
    public void test6() throws Exception {
        User user = new User();
        User tom = user.builder().passWord("  海贼  ").userName("tom").age(21).build();
        System.out.println(tom.toString());
        trimBean(tom);
        System.out.println(tom.toString());
    }


    public void trimBean(Object model) throws Exception {
        //int a = 1/0;
        Class clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getGenericType().toString().equals("class java.lang.String")) {
                Method getMethod = clazz.getMethod("get" + getMethodName(field.getName()));
                String value = (String) getMethod.invoke(model);// 调用getter方法获取属性值
                if (StringUtils.isNotBlank(value)) {
                    value = StringUtils.trimToEmpty(value);
                    field.setAccessible(true);
                    field.set(model, value);
                }
            }
        }
    }

    private String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
