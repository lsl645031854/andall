package com.andall.sally.supply.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 22:57 2020/3/28
 *
 * 链表是以节点的方式来存储
 * 每个节点包含data域和指向下一个节点的next域
 * 节点不一定是连续存储的
 * 链表分带头节点的链表和不带头节点的链表
 *  单链表
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode heroNode = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode1 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode2 = new HeroNode(3, "吴用", "智多星");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByOrder(heroNode);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode1);

        // 更新操作
        HeroNode heroNodeNew = new HeroNode(2, "卢俊", "麒麟");
        singleLinkedList.update(heroNodeNew);

        singleLinkedList.delete(2);

        singleLinkedList.list();
    }
}

class SingleLinkedList {
    // 初始化头节点， 头节点不会变，不存数据
    private HeroNode head = new HeroNode(0, "", "");
    // 添加节点到单向链表， 不考虑顺序是，找到链表的最后一个节点，将最后节点的next指向新的节点

    public void add(HeroNode heroNode) {
        // head节点不能动，需要辅助节点遍历  temp
        HeroNode temp = head;
        while (true) {
            // 退出循环时，指向链变最后
            if (temp.getNext() == null) {
                break;
            }
            // 没找到最后，将temp后移
            temp = temp.getNext();
        }

        // 最后节点的next指向新的节点
        temp.setNext(heroNode);
    }

    // 第二种方式添加英雄，按照英雄顺序添加
    public void addByOrder(HeroNode heroNode) {
        // 需要个辅助变量
        HeroNode temp = head;
        // 标志改顺序的节点是否存在
        boolean flag = false;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }

            if (temp.getNext().getNo() > heroNode.getNo()) {
                // 位置找到
                break;
            } else if (temp.getNext().getNo() == heroNode.getNo()) {
                // 说明已经存在改节点
                flag = true;
                break;
            }

            // 将指针后移
            temp = temp.getNext();
        }

        if (flag) {
            System.out.printf("准备插入的英雄编号%d已经存在\n", heroNode.getNo());
        } else {
            // 插入到temp后面
            heroNode.setNext(temp.getNext());
            temp.setNext(heroNode);
        }
    }

    public void update(HeroNode heroNode) {
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        // 需要辅助节点
        HeroNode temp = head.getNext();
        boolean flag = false; // 表示是否找到该节点
        while (true) {
            if (temp == null) { // 表示是否找到该节点
                break;
            }

            if (temp.getNo() == heroNode.getNo()) {
                flag = true;
                break;
            }

            temp = temp.getNext();
        }

        if (flag) {
            temp.setName(heroNode.getName());
            temp.setNickName(heroNode.getNickName());
        } else {
            System.out.println("没有找到该节点");
        }

    }

    public void delete(int no) {
        HeroNode temp = head;
        boolean flag = false; // 是否找到待删除掉节点

        while (true) {
           if (temp.getNext() == null) {
               break;
           }

           if (temp.getNext().getNo() == no) {
               flag = true;
               break;
           }
           temp = temp.getNext();
        }

        if (flag) {
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.println("没有找到该节点");
        }
    }

    // 显示所有的链表
    public void list() {
        // 判断链表是否为空
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        //需要辅助节点遍历
        HeroNode temp = head.getNext();

        while (true) {
            if (temp == null) {
                break;
            }

            System.out.println(temp.toString());
            // 将next后移
            temp = temp.getNext();
        }
    }

}

@Getter
@Setter
@ToString(exclude = "next")
class HeroNode{
    private int no;
    private String name;
    private String nickName;
    private HeroNode next; // 指向下一个节点


    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }



}
