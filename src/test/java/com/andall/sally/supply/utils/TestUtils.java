package com.andall.sally.supply.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.andall.sally.supply.entity.User;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    public void testS() {
        int i = StringUtils.isNotBlank("") ? 1 : 0;
        System.out.println(i);

        Map<String, String> map = new ConcurrentHashMap<>(15);
        map.put("a", "a");

        System.out.println(15 >>> 1);
    }

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>(15);
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
    public void test0() {

        long l = System.currentTimeMillis() / 1000;
        int i = RandomUtil.randomInt(800, 900);

        String signStr = "NZu5P1f1Ifok" + l + i + "41NNiCYd8YnL";
        System.out.println("时间戳：" + l);
        System.out.println("随机数：" + i);
        System.out.println("signStr:" + signStr);

        String s = MD5.MD5Encode(signStr.getBytes());
        System.out.println("get请求加密加密后：" + s);

        System.out.println("====================");

    }

    @Test
    public void test00() {
        String s = "x7ElksN43yAaOCKYRNozb0+CM2wGPPhyQ0i2O9R9t+jVdzy8q7P10DGBQL8vWrme9ev/rJ8emAuriJ\n"
                + "29zgbDpiTwc3m7K/k80BxUnpMWmQFHfXzL2pKQszHLqbPJ3Cp14uFSY6Dg7bCT2MOpA1nZrQI5odD\n"
                + "YfB5mFvAcyeRdzIam7Kie3yuLsTjCtKcX1BoN4";

        String o000o = s.replace("+", "o000o");
        System.out.println(o000o);
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
        double random = Math.random();
        System.out.println(random);
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
        String jsonString = JSON.toJSONString(null);
        System.out.println(jsonString);
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

    @Test
    public void test9() {
        String s = "宝宝营养基因检测a0002";
        String substring = StringUtils.substring(s, s.length() - 5, s.length());
        System.out.println(substring);

        String regex = "[a-zA-Z][A-Za-z0-9]{4}";
        boolean matches = substring.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void test10() {
        String s = "2600.12";
        int i1 = new BigDecimal(2700).compareTo(new BigDecimal(s));
        System.out.println(i1);
    }


    @Test
    public void test11() {
       String s = "000001";

        long l = Long.parseLong(s);

        System.out.println(l);

        String result = "";
        result = String.format("%06d", Integer.parseInt("2") + 1);
        System.out.println(result);

        String numStr = "1.01";

        String regex = "[\\d.]+";
        boolean matches = numStr.matches(regex);
        System.out.println(matches);

    }

    @Test
    public void test12() {
        int n = 1000;

        int cnt = 0;

        while (n > 0) {
            cnt++;
            n = n & (n - 1);
        }
        System.out.println(cnt);
    }

    @Test
    public void test13() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        for (int i = 0; i < list.size(); i++) {
            list.remove("2");
        }

//       for (String s : list) {
//           list.remove("2");
//       }
       list.stream().forEach(System.out::println);
    }

    @Test
    public void test14() {
        // 随机生成字符串.
        String random = RandomStringUtils.randomAlphabetic(12);
        System.out.println(random);

        // 随机生成数字
        int i = RandomUtil.randomInt(999);
        System.out.println(i);

        int j = RandomUtil.randomInt(800, 900);
        System.out.println(j);
    }

    @Test
    public void testDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date time = cal.getTime();

        long time1 = time.getTime();

        System.out.println(time1);
        System.out.println(cal.getTimeInMillis());
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String format = simpleDateFormat.format(time);
        System.out.println(format);
    }

    @Test
    public void testAward() {
        String url = "/allergyApply";
        boolean b = "/allergyApply/save".startsWith(url);
        System.out.println(b);
    }

    @Test
    public void testStr() {
        BigDecimal a = BigDecimal.valueOf(99);
        BigDecimal b = BigDecimal.valueOf(88);

        List<BigDecimal> list = new ArrayList<>();
        list.add(a);
        list.add(b);

        list.sort((o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            if (o1.compareTo(o2) > 0) {
                return -1;
            }
            return 0;
        });
        list.forEach(System.out::println);
    }

    @Test
    public void testStream() {
        List<User> list = new ArrayList<>();
        User user1 = new User(50, true);
        User user2 = new User(40, true);
        User user3 = new User(35, false);
        User user4 = new User(30, true);
        User user5 = new User(25, false);

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
//        Optional<User> first = list.stream().filter(User::isMember).findFirst();
//        Optional<User> second = list.stream().filter(user -> !user.isMember()).findFirst();
//        System.out.println(first.get());
//        System.out.println(second.get());

//        List<User> list1 = new ArrayList<>();
//        list1.clear();
//        System.out.println(list1.size());
        list.forEach(System.out::println);
        System.out.println("=========================");

        TreeSet<User> collect = list.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getAge))));
        List<User> userList = new ArrayList<>(collect);
        userList.forEach(System.out::println);

        System.out.println("=========================");

        List<User> collect1 = list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getAge))), ArrayList::new)
        );
        collect1.forEach(System.out::println);

    }

    @Test
    public void testSort() {
        List<String> list = Arrays.asList("ab","name", "nc", "age", "point", "member", "password");
        List<String> collect = list.stream().sorted(String::compareTo).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Test
    public void test23() {
        String s = "shop/activ/videos/yanchan-75c8397c-d687-418e-8d9d-809015a0b811.mp4";
        String s1 = StringUtils.substringAfterLast(s, "/");
        String s2 = StringUtils.substringBeforeLast(s1, ".");
        System.out.println(s2);
    }

    @Test
    public void test24() {
        String path = "/Users/andaall/Desktop/work/安我";

        File pwd = new File(path);

        listFiles(pwd);
    }

    private void listFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                listFiles(file1);
            }
        }
        String name = file.getName();
        if (name.contains("txt")) {
            System.out.println(file.getName());
        }

    }


    @Test
    public void testDate1() {
        List<Date> dateList = new ArrayList<>();

        Date date = new Date();

        dateList.add(DateUtils.addDays(date, -2));
        dateList.add(DateUtils.addDays(date, 2));
        dateList.add(DateUtils.addDays(date, 1));
        dateList.add(DateUtils.addDays(date, -1));
        dateList.add(date);

        dateList.forEach(date1 -> {
            System.out.println(DateUtil.format(date1, "yyyy-MM-dd"));
        });

        System.out.println("=========");
        dateList.sort((r1, r2) -> -(r1.compareTo(r2)));

        dateList.forEach(date1 -> {
            System.out.println(DateUtil.format(date1, "yyyy-MM-dd"));
        });
    }

    @Test
    public void testSpilt() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

//        for (int i = 0; i < list.size(); i++) {
//            if (i == 2) {
//                list.remove(i);
//            }
//        }

        for (Integer integer : list) {
            if (integer.equals(3)) {
                list.remove(integer);
            }
            System.out.println(integer);
        }

        System.out.println("========");
        list.forEach(System.out::println);
    }

    @Test
    public void testHashMap() {
            Map<String, String> map = new HashMap<>();
            map.put("1", "1");
            map.put("2", "2");
            map.put("3", "3");

            Set<Map.Entry<String, String>> entries = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                iterator.remove();
            }

            System.out.println(map);

    }

    @Test
    public void testRe() throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parse = sdf.parse("2020-11-23 14:57:09");
//        System.out.println(parse.getTime());
//        String endTimeDate = sdf.format(new Date(Long.parseLong(parse+"000")));
//        System.out.println(endTimeDate);
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testMap() throws Exception {
        Map<String,Double> map = new HashMap<>();
        map.put("1", 30D);
        map.put("3", 40D);
        map.put("2", 13D);
        map.put("4", 6D);
        map.put("6", 23D);
        map.put("8", 6D);

        Optional<Map.Entry<String, Double>> first = map.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue));
        Map.Entry<String, Double> stringDoubleEntry = first.get();
        System.out.println(stringDoubleEntry.getKey());
        System.out.println(stringDoubleEntry.getValue());

    }

    @Test
    public void testObj() {

    }

    @Test
    public void testM() {
        Map<String, Object> map = new HashMap<>();
        map.put("S1", "123");
        map.put("W1", "123");
        map.put("G1", "123");
        map.put("A1", "123");
        map.put("C1", null);
        System.out.println(map);
        String s = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        System.out.println(s);

        User user = new User();
        user.setUserName("123");
        user.setAge(0);
        user.setMember(false);

        System.out.println(JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
    }


    public <T> List<T> getData(Class<T> tClass) throws Exception {
        List<T> list = new ArrayList<>();
        T t = tClass.newInstance();
        list.add(t);
        return list;
    }
    
    
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间:  "+now);
        
        
        
        //当天的零点
        System.out.println("当天的零点:  "+ LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));
        
        //当天的最后时间
        System.out.println("当天的最后时间:  "+LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
    
        //时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("formate: "+ LocalDateTime.of(now.toLocalDate(), LocalTime.MAX).format(formatter));
        
        //最近的五分钟点位
        Integer minute = now.getMinute();
        minute = minute/5*5;
        System.out.println("最近的五分钟点位:  "+LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), minute, 0));
        
        //Date转为LocalDateTime
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("Date转为LocalDateTime: "+instant.atZone(zoneId).toLocalDateTime());
    }
    
    @Test
    public void testNull() {
        List<String> list = new ArrayList<>();
        
        list.forEach(s -> {
            System.out.println(s);
                }
        );
        
        if (CollectionUtils.isEmpty(list)) {
            System.out.println(11);
        }
    }
    
    @Test
    public void test112() {
        
        
        buildUser(null);
        
    }
    
    private void buildUser(Integer age) {
        User user = new User();
        user.setAge(age);
    }


    @Test
    public void testFile() {
        File file = new File("/Users/shuailingli/Downloads/斯佳丽.jpeg");
        byte[] bytes = FileUtil.readBytes(file);
        byte[] encodeBytes = Base64.getEncoder().encode(bytes);
        byte[] decodeBytes = Base64.getDecoder().decode(encodeBytes);
        FileUtil.writeBytes(decodeBytes, new File("/Users/shuailingli/Downloads" +
                "/11/斯佳丽.jpeg"));
    }

}
