package com.andall.sally.supply.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.jpedal.parser.shape.S;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 10:32 上午 2021/6/23
 */
public class DateTest {

    @Test
    public void testDate() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date gradeChangeDateStart = simpleDateFormat.parse("2021-04-08");

        Date gradeChangeDateEnd = DateUtils.addDays(gradeChangeDateStart, 90);
        System.out.println(simpleDateFormat.format(gradeChangeDateEnd));
        Date startRemindDate = addDay(gradeChangeDateStart, 90 - 14, false);
        System.out.println(simpleDateFormat.format(startRemindDate));
    }

    public Date addDay(Date date, int addDay, boolean timeFlag) {
        try {
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            cd.add(Calendar.DATE, addDay); //增加天数
            if (timeFlag) {
                fillTime(cd);
            } else {
                cutTime(cd);
            }
            return cd.getTime();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 开始时间 00:00:00
     */
    private void cutTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    }

    /**
     * 结束时间 23:59:59
     */
    private void fillTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    }

    /**
     * 两个时间间隔.
     */
    public int getBetweenDays(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.after(c2)) {
            c1.setTime(d2);
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            betweenDays += c1.getActualMaximum(Calendar.DAY_OF_YEAR);
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
        }
        return betweenDays;
    }

}
