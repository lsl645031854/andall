package com.andall.sally.supply.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author: lsl
 * @Description: 四大内置函数式接口
 * @Date: Created on 15:12 2019/10/5
 */
public class LambdaTest3 {

    @Test
    public void test1() {
        // 消费型接口 consume
        Consumer<String> consumer = (x) -> {
            x = x + " hello";
            System.out.println(x);
        };
        consumer.accept("厚德载物");

    }
    @Test
    public void test2() {
        // 供给型接口  get的时候才会执行方法
        Random random = new Random();
        List<Integer> numList = getNumList(1, () -> {
            System.out.println("开始获取随机数");
            int i = random.nextInt(10);
            System.out.println("结束获取随机数");
            return i;
        });
        numList.forEach(System.out::println);
    }
    private List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            System.out.println("执行get开始");
            Integer item = sup.get();
            System.out.println("执行get结束");
            list.add(item);
        }
        return list;
    }
    @Test
    public void test3() {
        // 函数型接口
        Function<String, String> function = String::trim;
        String apply = function.apply("\t\t\tdad  ");
        System.out.println(apply);
    }

    @Test
    public void test4() {
        // 断言型接口
        Predicate<Integer> predicate = x -> (x * x) > 4;
        boolean test = predicate.test(5);
        System.out.println(test);
    }
}
