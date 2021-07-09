package com.andall.sally.supply.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 4:53 下午 2021/6/18
 */
public final class FormatUtils {

    private static final Logger logger = LoggerFactory.getLogger(FormatUtils.class);

    private FormatUtils() {
    }

    public static String formatDate(Date date, String format) {
        return (date == null) ? "" : new SimpleDateFormat(format).format(date);
    }

    public static Date parseDate(String str, String format) {
        try {
            return org.apache.commons.lang3.StringUtils.isBlank(str) ? null : DateUtils.parseDate(str, format);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 格式化日期对象.
     *
     * @param date 日期
     * @return 格式化的结果
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 得到一个日期对象根据指定的日期串.
     *
     * @param str 日期字符串
     * @return 日期或者null
     */
    public static Date parseDate(String str) {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
                return null;
            }
            return DateUtils.parseDate(str, new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyyMMdd'T'HH:mm:ss", "yyyyMMdd'T'HHmmss'Z'",
                    "yyyyMMdd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMdd", "yyyy-MM-dd'T'HH:mm:ss.SSSX",
                    "yyyy-MM-dd'T'HH:mm:ssX", "yyyy年MM月dd日", "yyyy年MM月dd日 HH:mm:ss", "yyyy年MM月dd日 HH:mm", "yyyy年MM月", "yyyy年M月", "yyyy-M", "yyyy-M-d" });
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化日期时间对象.
     *
     * @param date 日期
     * @return 格式化的字符串
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到一个日期对象根据指定的日期时间串.
     *
     * @param str 日期字符串
     * @return 日期
     */
    public static Date parseDateTime(String str) {
        return parseDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    // static final String[] WEEK_NAMES = { "Sun", "Mon", "Tue", "Wed", "Thu",
    // "Fri", "Sat" };
    //
    // static final String[] MONTH_NAMES = { "Jan", "Feb", "Mar", "Apr", "May",
    // "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    public static String formatDateToGMTWithWeek(Date date) {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    public static Date parseDateToGMTWithWeek(String source) {
        try {
            DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

//	/**
//	 * 智能转换日期.
//	 *
//	 * @param source
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	public static Date smartParseDate(String source) {
//		try {
//			return new Date(source);
//		} catch (java.lang.IllegalArgumentException e) {
//		}
//		return parseDate(source);
//	}

    public static String formatShortDate(Date date) {
        return formatDate(date, "MM-dd");
    }

    /**
     * 格式化日期时间对象.
     *
     * @param date 日期
     * @return 返回格式化的字符串
     */
    public static String formatShortDateTime(Date date) {
        return formatDate(date, "MM-dd HH:mm");
    }

    // 定义日期format对象

    public static String formatCNDate(Date date) {
        return formatDate(date, "yyyy年MM月dd日");
    }

    public static String formatCNWeekDay(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 0:
                return "星期日";
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            default:
                return "error";
        }
    }

    public static String formatJSONDate(Date date) {
        return formatDate(date, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public static Date parseJSONDate(String str) {
        return parseDate(str, "yyyy-MM-dd'T'HH:mm:ss");
    }

    // 日期的数字映射
    static Map<String, String> cnm = Maps.newLinkedHashMap();
    static {
        cnm.put("三十一", "31");
        cnm.put("三十", "30");
        cnm.put("二十九", "29");
        cnm.put("二十八", "28");
        cnm.put("二十七", "27");
        cnm.put("二十六", "26");
        cnm.put("二十五", "25");
        cnm.put("二十四", "24");
        cnm.put("二十三", "23");
        cnm.put("二十二", "22");
        cnm.put("二十一", "21");
        cnm.put("二十", "20");
        cnm.put("十九", "19");
        cnm.put("十八", "18");
        cnm.put("十七", "17");
        cnm.put("十六", "16");
        cnm.put("十五", "15");
        cnm.put("十四", "14");
        cnm.put("十三", "13");
        cnm.put("十二", "12");
        cnm.put("十一", "11");
        cnm.put("十", "10");
        cnm.put("九", "9");
        cnm.put("八", "8");
        cnm.put("七", "7");
        cnm.put("六", "6");
        cnm.put("五", "5");
        cnm.put("四", "4");
        cnm.put("三", "3");
        cnm.put("二", "2");
        cnm.put("一", "1");
        cnm.put("〇", "0");
    }

    /**
     * 解析中文数字日期例如：二〇一二年十月十六日.
     *
     * @param str 字符串
     * @return 日期
     */
    public static Date parseCNDate(String str) {
        for (Map.Entry<String, String> entry: cnm.entrySet()) {
            str = str.replaceAll(entry.getKey(), entry.getValue());
        }
        return parseDate(str, "yyyy年M月d日");
    }

    /**
     * 解析中文数字日期例如：二〇一二年十月.
     *
     * @param str 字符串
     * @return 日期
     */

    public static Date parseCNDate2(String str) {
        for (Map.Entry<String, String> entry: cnm.entrySet()) {
            str = str.replaceAll(entry.getKey(), entry.getValue());
        }
        return parseDate(str, "yyyy年M月");
    }

}