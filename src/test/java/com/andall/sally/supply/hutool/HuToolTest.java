package com.andall.sally.supply.hutool;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:51 2020/9/4
 */
public class HuToolTest {

    @Test
    public void test() {
        String s1 = IdUtil.randomUUID();
        String s = IdUtil.simpleUUID();

        System.out.println(s1);
        System.out.println(s);
    }

    @Test
    public void test1() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 2);
        long l = snowflake.nextId();
        System.out.println(l);
    }


    @Test
    public void test2() {
        String string = RandomUtil.randomString(5);
        System.out.println(string);

        String string1 = RandomUtil.randomString("m9UM!pNbj^q_xxKa", 20);
        System.out.println(string1);
    }
    
   

}
