package com.andall.sally.supply.datastructure.hash;

/**
 * @Author: lsl
 * @Description: hash
 * @Date: Created on 17:58 2020/9/7
 */
public class HashTabDemo {
    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);

        hashTab.add(new Emp(5, "tom"));
        hashTab.add(new Emp(7, "mary"));
        hashTab.add(new Emp(15, "rose"));

        hashTab.findEmpById(5);

        hashTab.list();
    }

}

class HashTab {

    private EmpLinkedList[] empLinkedListArr;
    private int size; // 表示有几根链表

    public HashTab (int size) {
        this.size = size;
        empLinkedListArr = new EmpLinkedList[size];

        for (int i = 0; i < size; i++) {
            empLinkedListArr[i] = new EmpLinkedList();
        }
    }

    // 添加雇员
    public void add(Emp emp) {
        // 使用散列函数确定哪条链表查找
        int empLinkedListNo = hashFun(emp.id);

        // 将emp加入到对应的链表
        empLinkedListArr[empLinkedListNo].add(emp);
    }


    // 遍历所有的链表
    public void list() {
        for (int i = 0; i < empLinkedListArr.length; i++) {
            empLinkedListArr[i].list(i);
        }
    }

    // 根据输入的ID，查找雇员
    public void findEmpById(int id) {
        // 使用散列函数确定哪条链表查找
        int listNo = hashFun(id);

        Emp empById = empLinkedListArr[listNo].findEmpById(id);

        if (empById != null) {
            System.out.printf("在%d条链表找到雇员 id = %d \n", listNo + 1, id);
        }
    }

    // 散列函数
    public int hashFun(int id) {
        return id % size;
    }

}


class Emp {
    public int id;
    public String name;
    public Emp next; // 默认为null

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}

class EmpLinkedList {
    // 头指针
    private Emp head;

    // 添加雇员到链表
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }

        Emp cur = head;
        while (true) {
            if (cur.next == null) {
                break;
            }

            cur = cur.next;
        }

        cur.next = emp;
    }

    // 遍历链表的雇员的信息
    public void list(int no) {
        if (head == null) {
            System.out.println("第" + (no + 1) + "链表为空");
            return;
        }

        System.out.println("第" + (no + 1) + "链表信息为：");

        Emp cur = head; // 辅助指针

        while (true) {

            System.out.printf("=> id = %d, name = %s \t", cur.id, cur.name);

            if (cur.next == null) {
                break;
            }

            cur = cur.next;
        }

        System.out.println();
    }

    // 根据ID查找雇员
    public Emp findEmpById(int id) {
        // 判断链表是否为空
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }

        // 辅助指针
        Emp cur = head;

        while (true) {
            if (cur.id == id) {
                break; // cur指向的就是要查找的雇员
            }

            if (cur.next == null) {
                cur = null;
                break;
            }

            cur = cur.next;

        }
        return cur;
    }
}