package com.andall.sally.supply.utils;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        long l = System.currentTimeMillis();
        long time = new Date().getTime();
        System.out.println(l);
        System.out.println(time);
    }

    @Test
    public void test3() {
        List<Long> list = new ArrayList<>();
        list.add(null);
        list.add(1L);
        List<Long> collect = list.stream().distinct().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println(collect);

        long aNull = Long.parseLong("null");
        System.out.println(aNull);
    }

    @Test
    public void test5() {
        String string = "11";
        Pattern pattern = Pattern.compile("[0-9]*");
        boolean matches = pattern.matcher(string).matches();
        System.out.println(matches);
    }

    @Test
    public void test6() {
        String url = "https://www.baidu.com";
        url = url + String.format("?aId=%s&viewType=%s", 12324, "Share");
        System.out.println(url);
    }


    @Test
    public void test7() {
        String ids = "111,222,333 ";
        List<String> list = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .splitToList(ids);
        System.out.println(list);
    }

    @Test
    public void test8() {
        List<String> list = new ArrayList<>();
        list.add("tom");
        list.add("mary");
        list.add("ACD");
        List<String> list1 = new ArrayList<>();
        list1.add("BCB");
        list1.add("WWA");
        list1.add("ACD");
        boolean disjoint = Collections.disjoint(list1, list);
        System.out.println(disjoint);
    }

}
