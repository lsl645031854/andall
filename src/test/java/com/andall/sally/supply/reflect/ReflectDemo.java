package com.andall.sally.supply.reflect;

import com.andall.sally.supply.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: lsl
 * @Description: 反射测试
 * @Date: Created on 15:24 2020/9/4
 */
public class ReflectDemo {


    @Test
    public void testInvoke() throws Exception {
        User user = new User("tom", "tom", 12);
        Class<? extends User> aClass = user.getClass();
        Method method = aClass.getDeclaredMethod("sayWhat", String.class);
        method.setAccessible(true);
        Object o = method.invoke(user, "世界和平");
        System.out.println(o);
    }

    @Test
    public void testField() throws Exception {
        User user = new User("tom", "tom", 12);
        Class<? extends User> aClass = user.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object o = declaredField.get(user);
            System.out.println("field :" + declaredField.getName() + ", value: " + o + ", type: " + declaredField.getGenericType());
        }
    }

    @Test
    public void test6() throws Exception {
        User tom = User.builder().passWord("  海贼  ").userName("tom").age(21).build();
        System.out.println(tom.toString());
        trimBean(tom);
        System.out.println(tom.toString());
    }


    private void trimBean(Object model) throws Exception {
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
