package com.andall.sally.supply.datastructure.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 15:07 2020/6/18
 */
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr  = {-1, 0, 10, 10, 45};

        // 线性查找
//        boolean search = search(arr, -11);
//        System.out.println(search);

        // 二分法查找, 返回index
//        int i = binarySearch(arr, 0, arr.length - 1, 19);
//        System.out.println(i);

        // 二分法查找, 多个情况，将满足的元素的下标放到list
        List<Integer> list = binarySearch2(arr, 0, arr.length - 1, 10);

        for (Integer integer : list) {
            System.out.print(integer + "\t");
        }
    }

    // 线性查找
    private static boolean search(int[] arr, int num) {
        for (int i : arr) {
            if (i == num) {
                return true;
            }
        }
        return false;
    }

    // 二分法查找
    private static int binarySearch(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

    // 二分法查找 将满足的元素的下标放到list
    private static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        List<Integer> list = new ArrayList<>();
        if (left > right) {
            return list;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (findVal > midVal) {
            return binarySearch2(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {
            return binarySearch2(arr, left, mid - 1, findVal);
        } else {
            // 找到mid索引值，不要马上返回
            // 向mid值左边扫描，将满足的元素的下标加入的集合中
            // 向mid值右边扫描，将满足的元素的下标加入到集合
            // 将list返回
            int temp = mid - 1;
            while (temp >= 0 && arr[temp] == findVal) {
                list.add(temp);
                temp -= 1;
            }

            temp += 1;
            while (temp <= arr.length - 1 && arr[temp] == findVal) {
                list.add(temp);
                temp += 1;
            }
        }

        return list;
    }

}
