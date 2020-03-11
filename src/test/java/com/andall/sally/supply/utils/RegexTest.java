package com.andall.sally.supply.utils;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 15:51 2020/3/10
 */
public class RegexTest {

    @Test
    public void test() {
        // 判断手机号
        String regex = "1[38]\\d{9}";
        String mobile = "13924672341";
        boolean matches = mobile.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void test1() {
        // 判断手机号
        String regex = "(1[358][689])\\d{4}(\\d{4})";
        String mobile = "13924672341";
        String replace = mobile.replaceAll(regex, "$1****$2");
        System.out.println(replace);
    }

    @Test
    public void test2() {
        String content = "cooking singing";
        // 从内容上截取路径数组
        Pattern pattern = Pattern.compile(".*(?=ing)");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test3() {
        String content = "cooking singing";
        // 从内容上截取路径数组
        Pattern pattern = Pattern.compile("[a-z]*(?=ing)");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test4() {
        String content = "abcdefg abced";
        Pattern pattern = Pattern.compile("(?<=abc)[a-z]*");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test5() {
        String content = "Windows3.1";
        Pattern pattern = Pattern.compile("Windows(?!95|98|NT|2000)");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
