package com.andall.sally.supply.datastructure.tree;

/**
 * @Author: lsl
 * @Description: 二叉排序树
 * @Date: Created on 14:02 2020/11/2
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {


    }

    static class BinarySortTree {
        private Node root;

        public Node getRoot() {
            return root;
        }

        // 查找要删除的节点
        public Node search(int value) {
            if (root == null) {
                return null;
            } else {
                return root.search(value);
            }
        }

        // 查找父节点
        public Node searchParent(int value) {
            if (root == null) {
                return null;
            } else {
                return root.searchParent(value);
            }
        }

        // 返回以node为根节点的二叉排序树的最小节点的值
        public int delRightTreeMin(Node node) {
            return 0;
        }


    }

    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value +  '}';
        }

        // 添加节点
        public void add(Node node) {
            if (node == null) {
                return;
            }

            // 判断传入的节点的值，和当前子树根节点的值关系
            if (node.value < this.value) {
                if (this.left == null) {
                    this.left = node;
                } else {
                    // 递归向左子树添加
                    this.left.add(node);
                }
            } else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    // 递归向左子树添加
                    this.right.add(node);
                }
            }
        }

        // 中序遍历
        public void indexOrder() {
            if (this.left != null) {
                this.left.indexOrder();
            }

            System.out.println(this);
            if (this.right != null) {
                this.right.indexOrder();
            }
        }

        // 查找要删除的节点
        public Node search(int value) {
            if (value == this.value) {
                return this;
            } else if (value < this.value) {
                //如果左子节点为空
                if (this.left == null) {
                    return null;
                }
                return this.left.search(value);
            } else {
                if (this.right == null) {
                    return null;
                }
                return this.right.search(value);
            }
        }

        // 查找要删除节点的父节点
        public Node searchParent(int value) {
            if ((this.left != null && this.left.value == value) ||
                    (this.right != null && this.right.value == value)) {
                return this;
            } else {
                if (value < this.value && this.left != null) {
                    return this.left.searchParent(value);
                } else if (value >= this.value && this.right != null) {
                    return this.right.searchParent(value);
                } else {
                    return null;
                }
            }
        }
    }
}
