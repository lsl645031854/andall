package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

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
    public void test111() {
        // 判断手机号
        String regex = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{8,}$";
        String mobile = "1111111@@";
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
        String name = "夫卡镇也经历了外国武装干涉和内战的岁月。红军解放了谢佩托夫卡镇，但很快就撤走了。只留下老布什维克朱赫来在镇上做地下工作。 朱赫来很友好，教保尔学会了英式拳击，还培养了保尔朴素的革命热情。一次，因为解救朱赫来，保尔自己被关进了监狱。而后敌人因疏忽把他错放了，保尔怕重新落入魔掌，不敢回家，遂不由自主地来到了冬妮娅的花园门前，纵身跳进了花园。由于上次钓鱼时，保尔解救过冬妮娅，加上她又喜欢他“热情和倔强”的性格，他的到来让她很高兴。保尔也觉得冬妮娅跟别的富家女孩不一样，他们都感受到了朦胧的爱情。为了避难，他答应了冬妮娅的请求，住了下来。几天后，冬妮娅找到了保尔的哥哥阿尔焦姆，他把弟弟柯察金送到喀查丁参加了红军。";
//        String reg = "[龄]{1}[\\d]*";
        String keyWord = "红军";
        String reg = ".{1,15}(?<="+ keyWord +").{1,15}";
        String reg1 = "(?<=红军).{1,10}";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(name);
        while(m.find()){
            String group = m.group();
            System.out.println(group);
        }
    }
    
    
    @Test
    public void test13() {
        String str = "<div class=\"cont\">\n" + "<div id=\"contson9ebe5eef393d\" class=\"contson\">\n" + "<p" +
                ">故今日之责任，不在他人，而全在我少年。少年智则国智，少年富则国富；少年强则国强，少年独立则国独立；少年自由则国自由；少年进步则国进步；少年胜于欧洲，则国胜于欧洲；少年雄于地球，则国雄于地球。</p" +
                ">\n" + "<p>　　红日初升，其道大光。河出伏流，一泻汪洋。潜龙腾渊，鳞爪飞扬。乳虎啸谷，百兽震惶。鹰隼试翼，风尘翕张。奇花初胎，矞矞皇皇。干将发硎，有作其芒。天戴其苍，地履其黄。纵有千古，横有八荒。前途似海，来日方长。美哉我少年中国，与天不老！壮哉我中国少年，与国无疆！(翕张 一作：吸张)</p>\n" + "</div>\n" + "</div>\n" + "<div class=\"tool\">\n" + "<div style=\"width: 100%;\"><img class=\"htm-notice-img\" style=\"width: 100%;\" src=\"https://htm-bucket-test.oss-cn-hangzhou.aliyuncs.com/oms/payment/picture/487311a50511498da3ea0c874feb9bc9\" /></div>\n" + "</div>";
//        String reg = "[龄]{1}[\\d]*";
        String regEx_html = "<[^>]+>";
        String regEx_space = "\\s*|\t|\r|\n";
        Pattern p = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("");
        
        Pattern p1 = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(s);
        String s1 = m1.replaceAll("");
    
        System.out.println(s1);
    }
    
    
    public void city(List<Children> converts, String pCode) {
        if (CollectionUtils.isEmpty(converts)) {
            return;
        }
        for (int i = 0; i < converts.size(); i++) {
            Children convert = converts.get(i);
            System.out.println("insert into htm_region (code, name, sort, parent_code) values ('" + convert.getValue() +  "','" + convert.getLabel() + "', "+ (i + 1) + ",  "+ pCode + ");");
//            System.out.println(convert.getValue() + ":" + convert.getLabel());
            List<Children> children = convert.getChildren();
            region(children, pCode, convert.getValue());
        }
    }
    
    public void region(List<Children> converts, String pCode, String cCode) {
        if (CollectionUtils.isEmpty(converts)) {
            return;
        }
    
        for (int i = 0; i < converts.size(); i++) {
            Children convert = converts.get(i);
            System.out.println("insert into htm_region (code, name, sort, parent_code) values ('" + convert.getValue() +  "','" + convert.getLabel() + "', "+ (i + 1) + ",  "+ cCode + ");");
        }
    }
}
