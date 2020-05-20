package com.andall.sally.supply.hutool;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 17:16 2020/7/15
 */
@Slf4j
public class HuToolDateTest {

    @Test
    public void test() {
        //当前时间
        Date date = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();
        log.info("输出时间：{}", date);
        log.info("输出时间：{}", date2);
        log.info("输出时间：{}", date3);
        log.info("输出时间：{}", now);
        log.info("输出时间：{}", today);
    }


    @Test
    public void test1() throws ParseException {
        Date date = DateUtils.parseDate("2020-07-31", "yyyy-MM-dd");
        Date date1 = DateUtils.parseDate("2020-08-14", "yyyy-MM-dd");

        long between = DateUtil.between(date, date1, DateUnit.WEEK);
        System.out.println(between);
    }
}
