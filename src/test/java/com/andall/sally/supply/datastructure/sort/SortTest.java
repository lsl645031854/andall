package com.andall.sally.supply.datastructure.sort;

import com.sun.javafx.css.CssError;
import org.junit.Test;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 16:47 2020/6/9
 */
public class SortTest {

    private int[] arr = {3, 9, -1, 20, 10};

    @Test
    public void bubbleSortTest() {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length -1; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        print();

    }

    @Test
    public void bubbleSort2() {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        print();
    }

    @Test
    public void choiceSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            // 假定最小值
            int min = arr[i];
            // 假定最小值的index
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 发现最小值不对
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
        print();
    }

    @Test
    public void insertSort() {
        int insertVal;
        int insertIndex;

        for (int i = 1; i < arr.length; i++) {
            // 定义待插入的数
            insertVal = arr[i];
            insertIndex = i - 1;
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }
        }

        print();
    }





    private void print() {
        for (int i : arr) {
            System.out.println(i);
        }
    }

}
