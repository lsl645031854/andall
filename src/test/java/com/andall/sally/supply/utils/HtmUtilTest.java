package com.andall.sally.supply.utils;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:27 2022/1/13
 */
public class HtmUtilTest {
	
	@Test
	public void testPhone() {
		
		String sql = "(now(), '测试批量', NULL, now(), now(), 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIxH2t1FKX7DoeTeaodSKCOyuz1ldEZRsiapJ2SZzH0qejfW0p72WFia6y9gZ2N9Jva0O0VRiaXzCADA/132', '%s', '707254135796350461', '0', '', '%s', '1', '1', ''),";
		
		String sql1 = "(NULL, now(), 'oTTid5K-kzhMbWkmXxISktJOwmC0', '1', '%s', NULL, '%s', NULL, '0', '1'),";
		Set<String> set = new HashSet<>();
		
		for (int i = 4001; i < 5001; i++) {
//			Integer headRandom=new Random().nextInt(25);
//			String mobile = getHeadMobile(5) + getEndMobile() + getEndMobile();
//			boolean add = set.add(mobile);
//			if (!add) {
//				continue;
//			}
			
			String format = String.format(sql1, i, i);
			System.out.println(format);
		}
		
		
		
		
	}
	
	
	public static String getHeadMobile(Integer type){
		switch (type) {
			case 1:
				return "130";
			case 2:
				return "131";
			case 3:
				return "132";
			case 4:
				return "133";
			case 5:
				return "134";
			case 6:
				return "135";
			case 7:
				return "136";
			case 8:
				return "137";
			case 9:
				return "138";
			case 10:
				return "139";
			case 11:
				return "150";
			case 12:
				return "151";
			case 13:
				return "152";
			case 14:
				return "153";
			case 15:
				return "155";
			case 16:
				return "156";
			case 17:
				return "157";
			case 18:
				return "158";
			case 19:
				return "159";
			case 20:
				return "177";
			case 21:
				return "186";
			case 22:
				return "183";
			case 23:
				return "187";
			case 24:
				return "188";
			case 25:
				return "189";
			default:
				return "173";
		}
	}
	
	/**
	 * 获取尾号4位
	 */
	public static String getEndMobile(){
		String ychar = "0,1,2,3,4,5,6,7,8,9";
		int wei = 4;
		String[] ychars = ychar.split(",");
		String endMobile = "";
		Random rdm = new Random();
		for (int i = 0; i < wei; i++) {
			int j = (rdm.nextInt() >>> 1) % 10;
			if (j > 10) {
				j = 0;
			}
			endMobile = endMobile + ychars[j];
		}
		return endMobile;
		
	}
	
	
	public static <T> List<List<T>> splitList(List<T> list, int splitSize) {
		//判断集合是否为空
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyList();
		//计算分割后的大小
		int maxSize = (list.size() + splitSize - 1) / splitSize;
		//开始分割
		return Stream.iterate(0, n -> n + 1)
				.limit(maxSize)
				.parallel()
				.map(a -> list.parallelStream().skip((long) a * splitSize).limit(splitSize).collect(Collectors.toList()))
				.filter(b -> !b.isEmpty())
				.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<>(1001);
		for (int i = 0; i < 900; i++) {
			list.add(i);
		}
		int splitSize = 100;
		
		List<List<Integer>> splitList = splitList(list, splitSize);
		for (List<Integer> integers : splitList) {
			System.out.println(integers);
		}
		
		
	}
	
	@Test
	public void testParseStr() {
		BigDecimal bigDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
		
		System.out.println(bigDecimal);
	}
	
}
