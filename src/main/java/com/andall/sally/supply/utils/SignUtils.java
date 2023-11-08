package com.andall.sally.supply.utils;

import cn.hutool.bloomfilter.bitMap.BitMap;
import cn.hutool.bloomfilter.bitMap.IntMap;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 *   用一个31位的Bitmap来记录用户一个月的签到数据，bit位为0时表示未签到，为1时表示已签到。
 *   当用户1号签到后把第1位bit标记为1，2号签到后把第2位bit标记为1。
 * @Date: Created on 10:33 上午 2023/11/7
 */
public class SignUtils {

	/**
	 * 指定日期签到
	 *
	 * @param sign 已签到的集合
	 * @param day  需要添加的签到日期 1,2,3...
	 * @return
	 */
	public static Integer sign(Integer sign, Integer day) {
		return sign | 1 << day;
	}

	public static void main(String[] args) {
//		Integer sign = sign(30, 5);
//		System.out.println(sign);
		System.out.println(checkSign(62, 5));
//
		System.out.println( Integer.toBinaryString(62));
//		System.out.println( Integer.parseInt("10000000000000000000000000001010", 2));


		int day = DateUtil.thisDayOfMonth();
		System.out.println( day);

		BitMap bitMap = new IntMap();
		bitMap.add(1);
		System.out.println(JSON.toJSON(bitMap));
		System.out.println(bitMap);
	}

	/**
	 * 检查指定日期是否已签到
	 *
	 * @param sign
	 * @param day
	 * @return
	 */
	public static boolean checkSign(Integer sign, Integer day) {
		return 0 != (sign & 1 << day);
	}


	/**
	 * 统计本月已签到次数
	 * @param sign
	 * @return
	 */
	public static Integer countSign(Integer sign) {
		int count = 0;
		while (sign != 0) {
			count += (sign & 1);
			sign = sign >>> 1;
		}
		return count;
	}

	/**
	 * 统计当前连续签到次数
	 * @param sign
	 * @return
	 */
	public static Integer continueSign(Integer sign) {
		int count = 1;
		List<String> thisMonthSign = getSignDays(sign);
		Date thisDate = DateUtil.date();
		int thisMonth = DateUtil.month(thisDate) + 1;
		Date lastDate = DateUtil.offsetDay(thisDate,-1);
		while (true){
			String day = DateUtil.dayOfMonth(lastDate)+"";
			int lastMonth = DateUtil.month(lastDate) + 1;
			if (thisMonth != lastMonth || !thisMonthSign.contains(day)){
				break;
			}
			count ++;
			lastDate = DateUtil.offsetDay(lastDate,-1);

		}
		return count;
	}

	/**
	 * 获取以前到的日期列表
	 * @param sign
	 * @return
	 */
	public static List<String> getSignDays(int sign) {
		List<String> result = new ArrayList<String>();
		int day = 0;
		while (sign != 0) {
			day++;
			sign = sign >>> 1;
			if (1 == (sign & 1)) {
				result.add(String.valueOf(day));
			}

		}
		return result;
	}

}
