package com.andall.sally.supply.datastructure.tree;


/**
 * @Author: lsl
 * @Description: 顺序二叉树、
 *  需求：给定一个数组{1,2,3,4,5,6,7},要求以二叉前序遍历的方式进行遍历
 *
 *  从数据存储来看，数据的存储方式和树的存储方式可以相互转换，即数组可以转换为树，树可以转换为数组
 *  特点：
 *      顺序二叉树只考虑完全二叉树
 *      第n个元素的左子节点为2n + 1
 *      第n个元素的右子节点为2n + 2
 *      第n个元素的父节点为（n - 1）/ 2
 *      n: 表示二叉树的第n个元素（从0开始）
 * @Date: Created on 11:02 2020/11/2
 */
public class ArrBinaryTreeDemo {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }

    static class ArrBinaryTree {
        private int[] arr;

        public ArrBinaryTree(int[] arr) {
            this.arr = arr;
        }

        public void preOrder() {
            preOrder(0);
        }

        private void preOrder(int index) {
            if (arr == null || arr.length == 0) {
                System.out.println("数组为空");
            }
            System.out.print(arr[index] + "\t");

            if (index * 2 + 1 < arr.length) {
                preOrder(index * 2 + 1);
            }
            if (index * 2 + 2 < arr.length) {
                preOrder(index * 2 + 2);
            }
        }
    }
}
