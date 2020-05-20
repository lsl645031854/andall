package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public void testNumber() {
        // 判断手机号
        String regex = "Jdo\\d{9}";
        String mobile = "Jdo000000011";
        boolean matches = mobile.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void test1() {
        // 判断手机号
        String regex = "(\\d{3})\\d{4}(\\d{4})";
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
    public void test31() {
        String content = "22.中国";
        // 从内容上截取路径数组
        Pattern pattern = Pattern.compile("[0-9.]*");
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test4() {
        String content = "abcdefg中国 abced";
        Pattern pattern = Pattern.compile("(?<=abc)[^\\s]*");
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

    @Test
    public void test51() {
        //获取
        String s = "我的名字是：${person.name}，年龄为：${person.age}岁 ---> ${person.name}";
        String regex = "[${]{2}?([a-zA-Z.]+)[}]";
        //将正则表达式封装在pattern对象
        Pattern pattern = Pattern.compile(regex);
        //调用匹配器对象
        Matcher matcher = pattern.matcher(s);
        List<String> placeList = new ArrayList<>();
        while (matcher.find()) {
            System.out.println(matcher.group());
            placeList.add(matcher.group());
        }

        Map<String, String> map = new HashMap<>();
        map.put("person.name", "tom");
        map.put("person.age", "18");

        // 占位符和值的映射关系
        Map<String, String> placeMap = new HashMap<>();
        for (String place : placeList) {
            String replace = place.replace("${", "").replace("}", "");
            String value = map.get(replace);
            placeMap.put(place, value);
        }

        String str = finalStr(s, placeMap);
        System.out.println(str);

    }

    private String finalStr(String s, Map<String, String> placeMap) {

        for (Map.Entry<String, String> entry : placeMap.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }
        return s;
    }

    @Test
    public void test6() {
        String content = "快捷方式点击a000a[131231312]";
        String regex = "[a-zA-Z][A-Za-z0-9]{4}";
        boolean matches = content.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void test61() {
        String content = "KCRT||儿童乳糖耐受能力检测 宝宝免疫系统 代谢能力  安我基因a0001[612828562165]";
        String regex = "[a-zA-Z][A-Za-z0-9]{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test7() {
        String content = "    121a  000a    ";
        System.out.println(content);

        String s = StringUtils.trimToEmpty(content);
        System.out.println(s);
    }

    @Test
    public void test8() {
        JSONObject object = new JSONObject();
        object.put("name", new ArrayList<>());

        Map object1 = object;
        System.out.println(object1);
    }

    @Test
    public void test9() {
        String value = "{\"barcode\":[\"AA1910CAD4B8A6\"]}";
        JSONObject object = JSON.parseObject(value);
        System.out.println(object);

        for (Map.Entry<String, Object> entry : ((Map<String, Object>) object).entrySet()) {
            Object value1 = entry.getValue();
            if (value1 instanceof List) {
                List<Object> list = (List<Object>) value1;
                List<Object> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
                entry.setValue(collect);
                System.out.println(entry.getValue());
            } else {
                System.out.println(entry.getValue());
            }
        }
    }

    @Test
    public void test10() {
       String name = "李帅领";
        String reg = "(?<=.)./g";
        String s = name.replaceAll(reg, "*");
        System.out.println(s);
    }

    @Test
    public void test11() {
        String name = "李帅领";
        String reg = "\\S{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(name);
        int i = 0;
        while(m.find()){
            i++;
            if(i==1)
                continue;
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }
    @Test
    public void test12() {
        String name = "王323 龄46 1可那看";
//        String reg = "[龄]{1}[\\d]*";
        String reg = "(?<=龄)[\\d]*";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(name);
        while(m.find()){
            String group = m.group();
            System.out.println(group);
        }
    }
}
