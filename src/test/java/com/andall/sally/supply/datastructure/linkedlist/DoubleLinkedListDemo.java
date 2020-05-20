package com.andall.sally.supply.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:13 2020/5/24
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode2 heroNode = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 heroNode1 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 heroNode2 = new HeroNode2(3, "吴用", "智多星");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(heroNode);
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode2);

        doubleLinkedList.update(new HeroNode2(3, "吴用111", "智多星"));

        doubleLinkedList.list();

        doubleLinkedList.delete(3);
        System.out.println("--------------");
        doubleLinkedList.list();
    }
}


class DoubleLinkedList{
    private HeroNode2 head = new HeroNode2(0, "", "");

    public void add(HeroNode2 heroNode) {
        // head节点不能动，需要辅助节点遍历  temp
        HeroNode2 temp = head;
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
        heroNode.setPre(temp);
    }

    public void update(HeroNode2 heroNode) {
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        // 需要辅助节点
        HeroNode2 temp = head.getNext();
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
        HeroNode2 temp = head.getNext();
        boolean flag = false; // 是否找到待删除掉节点

        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.getNo() == no) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        if (flag) {
            temp.getPre().setNext(temp.getNext());
            if (temp.getNext() != null) {
                temp.getNext().setPre(temp.getPre());
            }
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
        HeroNode2 temp = head.getNext();

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
class HeroNode2{
    private int no;
    private String name;
    private String nickName;
    private HeroNode2 next; // 指向下一个节点
    private HeroNode2 pre; // 指向上一个节点


    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }



}