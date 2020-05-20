package com.andall.sally.supply.datastructure;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:47 2020/9/21
 */
public class Algorithm {


    @Test // 动态规划
    public void dynamicTest() {
        int[] w = {1,4,3}; // 物品的重量
        int[] val = {1500, 3000, 2000}; //物品价值
        int m = 4; //背包重量
        int n = val.length; // 物品的重量
        // 创建一个二维数组，v[i][j] 表示前i个物品中能够装入容量为j的背包的最大价值
        int[][] v = new int[n + 1][m + 1];

        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0; // 第一列设为0
        }

        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0; // 第一行设为0
        }


    }

    @Test // 二分法查找，非递归
    public void binarySearchNoRecur() {
        int[] arr = {1,3,8,10,11,67,100};

        int index = -1;

        // 需要查找的数
        int target = 3;

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                index = mid;
                break;
            } else if (arr[mid] > target) {
                right = mid - 1; // 需要向左查找
            } else {
                left = mid + 1; // 需要向右边查找
            }
        }

        System.out.println(index);

    }

    @Test // 贪心算法 集合覆盖问题
    public void testGreedy() {
        // 创建广播电台
        HashMap<String, HashSet<String>> broadCasts = new HashMap<>();

        // 将各个电台存到broadCast
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");


        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadCasts.put("K1", hashSet1);
        broadCasts.put("K2", hashSet2);
        broadCasts.put("K3", hashSet3);
        broadCasts.put("K4", hashSet4);
        broadCasts.put("K5", hashSet5);

        // allAreas 存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");

        // 存放选择的电台集合
        List<String> selects = new ArrayList<>();

        // 临时集合，存放电台覆盖的地区和还没覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();

        // 定义maxKey，保存一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台key
        // 如果maxKey不为null, 则加入到selects
        String maxKey;
        while (allAreas.size() != 0) {
            maxKey = null;

            for (String key : broadCasts.keySet()) {
                tempSet.clear();

                // 当前key能够覆盖的地区
                HashSet<String> areas = broadCasts.get(key);
                tempSet.addAll(areas);

                // 求出tempSet 和 allAreas 集合的交集，交集会赋给 tempSet
                tempSet.retainAll(allAreas);

                // 如果当前这个集合包含的未覆盖的地区的数量比 maxKey指向的地区集合多，重制maxKey
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > broadCasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }

            if (maxKey != null) {
                selects.add(maxKey);

                allAreas.removeAll(broadCasts.get(maxKey));
            }
        }

        System.out.println(selects);
    }

}
