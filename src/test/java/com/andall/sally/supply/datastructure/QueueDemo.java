package com.andall.sally.supply.datastructure;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 13:30 2020/3/21
 */
public class QueueDemo {
    /**
     * 数据结构，包括线性结构和非线性结构
     *
     * 稀疏数组：
     *    当一个数组中大部分元素为0时，或者为同一个值的数组时，可以使用稀疏数组来保存该数组
     *    处理方法：
     *        记录数组一共有几行几列，有多少个不同的值
     *        把具有不同值的元素的行和列记录在一个小规模的数组中，从而缩小程序的规模
     *    二维数组转稀疏数组思路：
     *        遍历原始二维数组，得到有效的数据的个数sum
     *        根据sum创建稀疏数组sparseArr[sum+1][3]
     *        将二维数据的有效数据存到稀疏数组中
     *    稀疏数组转二维数组思路：
     *        先读出稀疏数组第一行数据，创建二维数组
     *        再读出下面几行数据，赋给原始二维数组
     *
     * 队列：
     *     先进先出
     *     常用数组和链表模拟队列
     *
     * 环形队列：实现队列的可复用
     *
     *
     */
    @Test
    public void test() {
        // 稀疏数组
        //创建原始的二维数据 11 X 11， 0表示没有棋子，1黑子，2蓝子
        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;

        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        System.out.println("------------------------------");
        // 遍历二维数组，查看非0数据的个数
        AtomicLong sum = new AtomicLong();
        Arrays.stream(chessArr).forEach(arr -> {
            long count = Arrays.stream(arr).filter(item -> item != 0).count();
            sum.accumulateAndGet(count, Math::addExact);

        });
        System.out.println(sum.get());

        // 创建对应的稀疏数组
        int[][] sparseArr = new int[(int)sum.get() + 1][3];
        // 初始化第一行
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = (int)sum.get();

        // 遍历二维数组，将数组赋值到稀疏数组中
        int count = 0; // 记录是第几个非0数组
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    count = count + 1;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }
        for (int[] row : sparseArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }

    @Test
    public void test1() {
        // 数组模拟队列
    }
}

class ArrayQueue{
    private int maxSize; // 队列容量
    private int front; // 队列头
    private int rear; // 队列尾部
    private int[] arr; // 数据

//    创建队列构造器
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1; // 初始化为-1  指向头部
        rear = -1; // 初始化为-1   指向尾部
    }

    // 判断队列是否满
    private boolean isFull() {
        return rear == maxSize - 1;
    }

    // 判断队列是否为空
    private boolean isEmpty() {
        return rear == front;
    }

    // 队列中添加数据
    public void addQueue(int n) {
        if(isFull()) {
            System.out.println("当前队列已经满了");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    // 从队列中获取数据
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("数组中无数据");
        }
        front++;
        return arr[front];
    }
}

/**
 * 环形队列
 */
class CircleQueue {
    private int maxSize; // 队列容量
    // 队列头 front指向队列的第一个元素，初始值为0
    private int front;
    // 队列尾部 real指向队列的最后一个元素的后一个元素的位置, 初始值为0
    private int rear;
    private int[] arr; // 数据

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = 0; // 初始化为0  指向头部
        rear = 0; // 初始化为0   指向尾部
    }


    // 判断队列是否满
    private boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // 判断队列是否为空
    private boolean isEmpty() {
        return rear == front;
    }

    // 队列中添加数据
    public void addQueue(int n) {
        if(isFull()) {
            System.out.println("当前队列已经满了");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;
        // 将real后移
        rear = (rear + 1) % maxSize;
    }

    // 从队列中获取数据
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("数组中无数据");
        }
        int value = arr[front];
        // 将front后移
        front = (front + 1) % maxSize;
        return value;
    }

    //当前队列的有效个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列数据
    public void showQueue() {
        for (int i = 0; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }
}
