package com.andall.sally.supply.jvm;

import cn.hutool.core.date.DateUtil;
import com.andall.sally.supply.utils.DateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 2:47 下午 2021/7/14
 */
public class BinaryTest {


    @Test
    public void test() {
        System.out.println(1&7);
        System.out.println(Integer.toBinaryString(5));

        Calendar calendar = Calendar.getInstance();
        //得到前一个月
        calendar.add(Calendar.MONTH, -1);
        Date lastMonthTime = calendar.getTime();
        System.out.println(DateUtil.format(lastMonthTime, "yyyy-MM-dd"));
    }

    @Test
    public void test1() {
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 100001; i++) {
            list.add((long) i);
        }
        System.out.println(list.size());
        int listSize = list.size();
        int toIndex = 10000;

        for (int i = 0; i < listSize; i += toIndex) {
            if (i + toIndex > listSize) {
                toIndex = listSize - i;
            }
            List<Long> subList = list.subList(i, i + toIndex);
            System.out.print(i + "\t" + (i + toIndex) + "\t" + subList.size());
            System.out.println();
        }
    }

}
