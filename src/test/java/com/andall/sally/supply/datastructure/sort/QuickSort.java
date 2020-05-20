package com.andall.sally.supply.datastructure.sort;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @Author: lsl
 * @Description: 快速排序.
 * @Date: Created on 20:06 2020/6/16
 */
public class QuickSort {

    private static int[] arr = {-9,20,0,78, 23, 20, 35, -567,20};

    public static void main(String[] args) {
        quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length ; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
    }

    public static void quickSort(int[] arr, int left, int right) {
        int l = left; // 左下标
        int r = right; // 右下标

        // 中轴值
        int pivot = arr[(left + right) / 2];
        int temp = 0; //临时变量，作为交换时使用

        /**
         * while 循环的目的是让比pivot的值小放在左边，比pivot大的放在右边
         */

        while (l < r) {

            while (arr[l] < pivot) {
                l += 1;
            }

            while (arr[r] > pivot) {
                r -= 1;
            }

            if (l >= r) {
                break;
            }

            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;


            if (arr[l] == pivot) {
                r -= 1;
            }

            if (arr[r] == pivot) {
                l += 1;
            }

            if (l == r) {
                l += 1;
                r -= 1;
            }

            if (left < r) {
                quickSort(arr, left, r);
            }

            if (right > l) {
                quickSort(arr, l, right);
            }

        }

    }


    @Test
    public void test() {
        boolean number = NumberUtils.isNumber("1321212..");
        System.out.println(number);
    }
}
