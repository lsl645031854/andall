package com.andall.sally.supply.utils;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:52 下午 2021/6/18
 */
public final class DateUtils {

    /**
     * 字段.
     */
    public static final int FIELD_YEAR = 1;

    /** 一天的毫秒数. */
    public static final long ONEDAYTIMES = 1000L * 60 * 60 * 24;

    private DateUtils() {

    }

    public static boolean compare(Date arg0, Date arg1) {
        if (arg0 == null && arg1 == null) {
            return true;
        } else if (arg0 != null && arg1 != null) {
            return arg0.getTime() == arg1.getTime();
        } else {
            return false;
        }
    }

    public static boolean isToday(Date date) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(date, new Date());
    }

    public static boolean afterNow(Date date) {
        return date.getTime() > System.currentTimeMillis();
    }

    public static boolean beforeNow(Date date) {
        return date.getTime() < System.currentTimeMillis();
    }

    private static void cutTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    }

    public static Date cutTime(Date date) {
        return dayStart(date);
    }

    private static void fillTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    }

    public static Date fillTime(Date date) {
        return dayEnd(date);
    }

    public static Date getWeekBeginDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cutTime(cal);
        return cal.getTime();
    }

    public static Date getWeekEndDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, 7);
        fillTime(cal);
        return cal.getTime();
    }

    public static Date getMonthBeginDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cutTime(cal);
        return cal.getTime();
    }

    public static Date getMonthEndDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        fillTime(cal);
        return cal.getTime();
    }

    public static Date getYearBeginDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cutTime(cal);
        return cal.getTime();
    }

    public static Date getYearEndDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 12);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        fillTime(cal);
        return cal.getTime();
    }

    public static Date yearStart(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date yearEnd(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date monthStart(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date monthEnd(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date dayStart(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date dayEnd(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date parseDate(String s) {
        return new Date(Long.parseLong(s));
    }

    public static int getBetweenDays(Date d1, Date d2) {
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

    public static int getYear(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH);
    }

    public static int getDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DATE);
    }

    public static void main(String[] args) {
        // System.out.println(getBetweenDays(FormatUtils.parseDate("2011-12-31"), FormatUtils.parseDate("2012-03-01")));
        // System.out.println(getMonthEndDate(new Date()));

        Date monthEndDate = getMonthBeginDate(new Date());
        System.out.println(monthEndDate);
    }

    /**
     * 格式化日期为.
     * <p>
     * 5分,10秒,5天10分5秒
     * </p>
     * @param date the date
     * @return 返回格式化后的日期
     */
    public static String format(Date date) {
        return format(date, new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date1, Date date2, String format) {
        if (date1 == null || date2 == null) {
            return "";
        }
        long day_millis = 86400000; // 一天的毫秒数 60*60*1000*24
        long times = date2.getTime() - date1.getTime();
        long day = times / day_millis;
        //如果时间间隔大于30天就按format的格式返回
        if (day > 30 && StringUtils.isNotBlank(format)) {
            return DateFormatUtils.format(date2, format);
        }
        return format(times);
    }

    /**
     * 显示时间长度.
     * @param times 时间长度 ms.
     * @return 显示字符串.
     */
    public static String format(long times) {
        long day_millis = 86400000; // 一天的毫秒数 60*60*1000*24
        long hour_millis = 3600000; // 一小时的毫秒数 60*60*1000
        long minute_millis = 60000; // 一分钟的毫秒数 60*1000
        long second_millis = 1000; // 一秒的毫秒数 1*1000
        long day = times / day_millis;
        long hour = (times % day_millis) / hour_millis;
        long minute = ((times % day_millis) % hour_millis) / minute_millis;
        long second = (((times % day_millis) % hour_millis) % minute_millis / second_millis);
        String time = "";
        if (day != 0) {
            time = time + java.lang.Math.abs(day) + "天";
        }
        if (hour != 0) {
            time = time + java.lang.Math.abs(hour) + "小时";
        }
        if (minute != 0) {
            time = time + java.lang.Math.abs(minute) + "分";
        }
        if (second != 0) {
            time = time + java.lang.Math.abs(second) + "秒";
        }
        if (StringUtils.isBlank(time)) {
            time = "0";
        }
        return time;
    }
}
