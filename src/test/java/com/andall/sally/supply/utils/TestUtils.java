package com.andall.sally.supply.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:18 2020/3/9
 */
public class TestUtils {

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "tom");
        map.put("mobile", "12323132");

        String s = JwtHelper.genToken(map);
        System.out.println(s);

        Map<String, String> maps = JwtHelper.verifyToken(s);
        maps.forEach((k,v) -> {
            System.out.println(k + ":" + v);
        });
    }


    @Test
    public void test1() {
        String regex = "^AA([a-zA-Z0-9]){12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("AA19032CD6D4HA");
        boolean matches = matcher.matches();
        System.out.println(matches);
    }

    @Test
    public void test4() {
        String regex = "^SN([a-zA-Z0-9]){13}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("SN0410000000001");
        boolean matches = matcher.matches();
        System.out.println(matches);
    }

    @Test
    public void test2() {
        String s = "AA19032CD6D4HA";
        if (s.startsWith("AA")) {
            System.out.println(true);
        }
    }

    @Test
    public void test3() {
        String s = "AA19032CD6D4HA";
    }

}
