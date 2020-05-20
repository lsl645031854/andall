package com.andall.sally.supply.hutool;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 17:40 2020/7/15
 */
public class HuToolStrTest {


    @Test
    public void test() {
        String template = "{}爱{}，就像老鼠爱大米";
        String str = StrUtil.format(template, "我", "你");
        System.out.println(str);

    }

}
