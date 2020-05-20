package com.andall.sally.supply.datastructure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author: lsl
 * @Description: 希尔排序 选择交换.
 * @Date: Created on 13:49 2020/6/16
 */
public class ShellSort {
    private static int[] arr = {3,8,4,7,1,6,5,9,2,0};

    public static void main(String[] args) {


        // 分成5组

        int temp;
        for(int i = 5; i < arr.length; i++) {
            for (int j = i - 5; j >= 0; j -= 5) {
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("第一次排序：" + Arrays.toString(arr));

        for(int i = 2; i < arr.length; i++) {
            for (int j = i - 2; j >= 0; j -= 2) {
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("第二次排序：" + Arrays.toString(arr));

        for(int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("第三次排序：" + Arrays.toString(arr));



    }

    @Test
    public void test() {
        int temp;
        for (int grp = arr.length / 2; grp > 0; grp = grp / 2) {
            for(int i = grp; i < arr.length; i++) {
                for (int j = i - grp; j >= 0; j -= grp) {
                    if (arr[j] > arr[j + grp]) {
                        temp = arr[j];
                        arr[j] = arr[j + grp];
                        arr[j + grp] = temp;
                    }
                }
            }
        }
        System.out.println("排序：" + Arrays.toString(arr));
    }
}
