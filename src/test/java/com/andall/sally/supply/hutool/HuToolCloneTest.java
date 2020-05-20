package com.andall.sally.supply.hutool;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:09 2020/7/15
 */
public class HuToolCloneTest {
    private static final Logger LOG = LoggerFactory.getLogger(HuToolCloneTest.class);

    @Test
    public void testSerializable() throws Exception {
        DogToy toy = new DogToy("骨头");
        byte[] serialize = serialize(toy);
        System.out.println(Arrays.toString(serialize));

        DogToy toy1 = unSerialize(serialize, DogToy.class);
        System.out.println(toy1);
    }

    @Test
    public void test() {
        // 浅克隆
        Dog dog = new Dog("carry", 3);
        dog.setDogToy(new DogToy("骨头"));
        System.out.println(dog);

        Dog clone = dog.clone();

        System.out.println(dog.getDogToy() == clone.getDogToy());  // true
    }

    @Test
    public void test1() {
        // 深克隆
        Dog dog = new Dog("carry", 3);
        dog.setDogToy(new DogToy("骨头"));
        Dog clone = ObjectUtil.cloneByStream(dog);

        System.out.println(dog.getDogToy() == clone.getDogToy()); // false
    }

    /**
     * 对象序列化为字节数组
     */
    private static byte[] serialize(Object obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        LOG.info("对象obj：【" + obj + "】序列化serStr：【" + bytes + "】");

        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bytes;
    }

    /**
     * 字节数组 反序列化为 对象
     */
    private static <T> T unSerialize (byte[] bytes, Class<T> tClass) throws Exception {
        if (tClass == null) {
            throw new RuntimeException("类型不可为null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        LOG.info("对象obj：【" + obj + "】反序列化serStr：【" + obj + "】");

        String jsonString = JSON.toJSONString(obj);

        objectInputStream.close();
        byteArrayInputStream.close();
        return JSON.parseObject(jsonString, tClass);
    }

}
