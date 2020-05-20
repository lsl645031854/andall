package com.andall.sally.supply.utils;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:14 2020/5/20
 */
public class LiKouTest {

    @Test
    public void test() {
        // 改变一个数，将数组变成递增数组
        int[] nums = new int[]{4, 2, 3};

        boolean b = checkPossibility(nums);
        System.out.println(b);
    }

    private boolean checkPossibility(int[] nums) {
        int cnt = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                int tmp = nums[i];
                if (i >= 1) {
                    nums[i] = nums[i - 1];
                } else {
                    nums[i] = nums[i + 1];
                }
                if (nums[i] > nums[i + 1]) {
                    nums[i] = tmp;
                    nums[i + 1] = nums[i];
                }
                cnt++;
                if (cnt == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testLCS() {
        // 最大公共字符串
        String a = "abcdeeqf";
        String b = "eqeqabcdefrw";
        String lcs = LCS(a, b);
        System.out.println(lcs);
    }

    private String LCS(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return LCS(str2, str1);
        }

        int[][] vec = new int[str1.length()][str2.length()];

        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();

        int iIndex = -1;
        int iMax = 0;

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1Chars[i] == str2Chars[j]) {
                    int n = 1 + ((i > 0 && j > 0) ? vec[i - 1][j - 1] : 0);
                    vec[i][j] = n;
                    if (iMax < n) {
                        iMax = n;
                        iIndex = i;
                    }

                }
            }
        }

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                System.out.print(vec[i][j] + " ");
            }
            System.out.println();
        }

        if (iIndex == -1) return "";
        return str1.substring(iIndex - iMax + 1, iIndex + 1);
    }

    @Test // 寻找两个有序数组的中位数
    public void findMedianSortedArrays() {
        int[] num1 = {1, 3, 4, 5, 6};
        int[] num2 = {2, 3, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20};
        double medianSortedArrays = findMedianSortedArrays(num1, num2);
        System.out.println(medianSortedArrays);
    }

    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int size = len1 + len2;
        if (size % 2 == 1) return findKth(nums1, 0, len1, nums2, 0, len2, size / 2 + 1);
        else
            return (findKth(nums1, 0, len1, nums2, 0, len2, size / 2) + findKth(nums1, 0, len1, nums2, 0, len2, size / 2 + 1)) / 2;
    }

    private double findKth(int[] nums1, int start1, int len1, int[] nums2, int start2, int len2, int k) {
        if (len1 - start1 > len2 - start2)  // 传进来的时候统一让短的数组为nums1
            return findKth(nums2, start2, len2, nums1, start1, len1, k);

        if (len1 - start1 == 0)  // 表示nums1已经全部加入前K个了，第k个为nums2[k - 1];
            return nums2[k - 1];

        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        // k==1表示已经找到第k-1小的数，下一个数为两个数组start开始的最小值

        int p1 = start1 + Math.min(len1 - start1, k / 2); // p1和p2记录当前需要比较的那个位

        int p2 = start2 + k - p1 + start1;

        if (nums1[p1 - 1] < nums2[p2 - 1]) {
            return findKth(nums1, p1, len1, nums2, start2, len2, k - p1 + start1);
        } else if (nums1[p1 - 1] > nums2[p2 - 1]) {
            return findKth(nums1, start1, len1, nums2, p2, len2, k - p2 + start2);
        } else {
            return nums1[p1 - 1];
        }
    }

    @Test
    public void test2() {
        int a = 2;
        int b = 3;
        System.out.println(a ^ b ^ a);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a=" + a + ",b=" + b);
    }

    @Test
    public void testSingleNumber() {
        /** 按位异或的3个特点：

         　　（1） 0^0=0，0^1=1 0异或任何数＝任何数

         　　（2） 1^0=1，1^1=0 1异或任何数－任何数取反

         　　（3） 任何数异或自己＝把自己置0
         **/
        int[] arr = {0, 2, 3, 4, 5, 4, 2, 3, 0};
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result ^= arr[i];
            System.out.println(result);
        }
        System.out.println("----------");
        System.out.println(result);
    }

}
