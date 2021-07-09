package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:37 上午 2020/12/25
 */
public class YouRenTest {

    @Test
    public void test() throws Exception {
        String format = MessageFormat.format("2222{1}---{0}", "you", null);
        System.out.println(format);
    }

    @Test
    public void testTreeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("{0}", "");
        map.put("{1}", 123);
        Object s = JSONObject.toJSONString(map);
        System.out.println(s);

        Map<String, Object> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        for (Object o : treeMap.values().toArray()) {
            System.out.print(o);
        }
    }

    @Test
    public void testDate() {
        LocalDate now = LocalDate.now();
        //获取昨天的日期
        LocalDate yesterday = now.plusDays(-1);
    }

    @Test
    public void testRefer() throws Exception {
        Date date = fromString("2021-01-31 23:59:59", false);
        Date date1 = fromString("2021-02-01", false);
        if (date1.after(date)){
            System.out.println(111);
        } else {
            System.out.println(2222);
        }

        System.out.println(date.getTime());
        System.out.println(date1.getTime());
    }


    public static Date fromString(String dateString, boolean isDateTime) {
        String formatString = "yyyy-MM-dd";
        if (isDateTime) {
            formatString = "yyyy-MM-dd HH:mm:ss";
        }

        return parseString(dateString, formatString);
    }

    public static Date parseString(String dateString, String formatString) {
        Date result = null;
        SimpleDateFormat pattern = new SimpleDateFormat(formatString);

        try {
            pattern.setLenient(false);
            result = pattern.parse(dateString);
        } catch (ParseException var5) {
            result = null;
        } catch (NullPointerException var6) {
            result = null;
        }

        return result;
    }


    public static void main(String[] args) throws ParseException {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        System.out.println(cal.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse("2021-01-23 00:00:01");
        if (parse.compareTo(cal.getTime()) >= 0) {
            System.out.println("111");
        } else {
            System.out.println("2222");
        }
    }

    @Test
    public void testAdd() {
        int point = 0;
        System.out.println(point);
        point = addPoint(10);
        System.out.println(point);
    }

    private Integer addPoint(int point) {
        return point + 100;
    }

    @Test
    public void testHttp() {
        // https://apis.map.qq.com/ws/distance/v1/matrix/
        // mode=walking&from=31.169172%2C121.365291&to=31.14573%2C121.43644&key=44FBZ-WIP3X-F6W4R-TBK3O-RZYVK-ZTBWL&callback=jsonp_13b64e046df85e0
        Map<String, String> map = new HashMap<>();
        map.put("mode", "walking");
        map.put("from", "31.169172,121.365291");
        map.put("to", "31.14573,121.43644");
        map.put("key", "44FBZ-WIP3X-F6W4R-TBK3O-RZYVK-ZTBWL");
//        map.put("callback", "jsonp_13b64e046df85e0");
        String s = HttpClientUtils.doGet("https://apis.map.qq.com/ws/distance/v1/matrix", map);
        System.out.println(s);
    }
}
