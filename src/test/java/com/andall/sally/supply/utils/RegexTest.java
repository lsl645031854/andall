package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<String> ls = new ArrayList<>();
        ls.add("AA1910CAD4B8A6");
        System.out.println(ls);
    }
}
