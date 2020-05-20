package com.andall.sally.supply.datastructure.linkedlist;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:14 2020/5/24
 */
public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.showBoy();

        System.out.println("-------");
        circleSingleLinkedList.countBoy(1, 2, 5);
    }

}

// 创建环形的单向链表
class CircleSingleLinkedList{
    // 默认第一个小孩
    private Boy first = null;

    // 添加小孩节点
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("人数不能小于 1");
            return;
        }
        Boy curBoy = null; // 帮助构建环形链表
        // 创建小孩
        for (int i = 1; i <= nums; i++) {
            // 根据编号创建小孩节点
            Boy boy = new Boy(i);

            // 如果是第一个小孩
            if (i == 1) {
                first = boy;
                first.setNext(first); //构成环
                curBoy = first; // 让辅助指针指向第一个小孩
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy; // 辅助指针后移
            }
        }

    }

    /**
     *
     * @param startNo 从第几个小孩开始
     * @param countNum 数几下
     * @param nums 小孩的总数
     */
    public void countBoy(int startNo, int countNum, int nums) {
        // 先对数据进行校验
        if(first == null || startNo < 1 || startNo > nums ) {
            System.out.println("参数异常");
            return;
        }

        // 创建辅助指针
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }

            helper = helper.getNext();
        }

        // 将helper和first指针移到开始位置
        for (int i = 0; i < startNo - 1; i ++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        // 开始报数
        while (true) {
            if (helper == first) {
                System.out.println("只剩下一个人");
                break;
            }

            // 让helper和first同时移动countNum - 1
            for(int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            // first指向的节点出圈
            System.out.println(first);

            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈子的小孩：%d \n", helper.getNo());
    }


    public void showBoy() {
        if (first == null) {
            System.out.println("无数据");
        }

        Boy curBoy = first; // 辅助指针

        while (true) {
            System.out.println(curBoy);

            if (curBoy.getNext() == first) {
                System.out.println("遍历结束");
                break;
            }
            curBoy = curBoy.getNext();
        }
    }
}

// 创建一个boy
class Boy{
    private int no; // 小孩的编号
    private Boy next; // 指向下一个节点，默认为null

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" + "no=" + no + '}';
    }
}