package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSONObject;
import com.andall.sally.supply.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}
