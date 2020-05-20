package com.andall.sally.supply.hutool;

import cn.hutool.core.lang.Validator;
import com.andall.sally.supply.entity.User;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 15:56 2020/9/4
 */
public class HuToolValidatorTest {

    @Test
    public void test() {
        User tom = User.builder().passWord("  海贼  ").userName("tom").age(21).build();
        String s = Validator.validateChinese("我是一段zhongwen", "内容中包含非中文");
        System.out.println(s);
    }

}
