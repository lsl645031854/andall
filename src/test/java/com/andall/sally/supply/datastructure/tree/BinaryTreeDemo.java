package com.andall.sally.supply.datastructure.tree;


/**
 * @Author: lsl
 * @Description: 二叉树demo
 * @Date: Created on 09:48 2020/11/2
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        root.setLeft(node2);
        root.setRight(node3);

        node2.setLeft(node4);
        node2.setRight(node5);
        binaryTree.setRoot(root);

        binaryTree.indexOrder();
        System.out.println("============");
        HeroNode node = binaryTree.indexOrderSearch(3);
        System.out.println(node);

    }


    // 定义二叉树
    static class BinaryTree {
        private HeroNode root;


        public void setRoot(HeroNode root) {
            this.root = root;
        }

        // 前序遍历
        public void preOrder() {
            if (this.root != null) {
                this.root.preOrder();
            } else {
                System.out.println("二叉树为空，无法遍历");
            }
        }

        // 中序遍历
        public void indexOrder() {
            if (this.root != null) {
                this.root.indexOrder();
            } else {
                System.out.println("二叉树为空，无法遍历");
            }
        }

        // 后序遍历
        public void postOrder() {
            if (this.root != null) {
                this.root.postOrder();
            } else {
                System.out.println("二叉树为空，无法遍历");
            }
        }

        // 前序遍历查找
        public HeroNode preOrderSearch(int no) {
            if (root != null) {
                return root.preOrderSearch(no);
            } else {
                return null;
            }
        }
        // 中序遍历查找
        public HeroNode indexOrderSearch(int no) {
            if (root != null) {
                return root.indexOrderSearch(no);
            } else {
                return null;
            }
        }
        // 后序遍历查找
        public HeroNode postOrderSearch(int no) {
            if (root != null) {
                return root.postOrderSearch(no);
            } else {
                return null;
            }
        }
    }

    // 创建HeroNode节点
    static class HeroNode {
        private int no;
        private String name;
        private HeroNode left;
        private HeroNode right;

        public HeroNode(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HeroNode getLeft() {
            return left;
        }

        public void setLeft(HeroNode left) {
            this.left = left;
        }

        public HeroNode getRight() {
            return right;
        }

        public void setRight(HeroNode right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "HeroNode{" + "no=" + no + ", name='" + name + '\'' + ", left=" + left + ", right=" + right + '}';
        }

        // 前序遍历
        public void preOrder() {
            // 先输出父节点
            System.out.println(this);
            // 递归向左子树前序遍历
            if (this.left != null) {
                this.left.preOrder();
            }
            // 递归向右子树前序遍历
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        // 中序遍历
        public void indexOrder() {
            // 递归向左子树中序遍历
            if (this.left != null) {
                this.left.indexOrder();
            }
            // 输出父节点
            System.out.println(this);

            if (this.right != null) {
                this.right.indexOrder();
            }
        }

        // 后序遍历
        public void postOrder() {
            if (this.left != null) {
                this.left.postOrder();
            }

            if (this.right != null) {
                this.right.postOrder();
            }

            System.out.println(this);
        }

        // 前序遍历查找
        public HeroNode preOrderSearch(int no) {
            System.out.println("前序遍历查找");
            if (this.no == no) {
                return this;
            }

            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.preOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }

            if (this.right != null) {
                resNode = this.right.preOrderSearch(no);
            }
            return resNode;
        }
        // 中序遍历查找
        public HeroNode indexOrderSearch(int no) {
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.indexOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }
            System.out.println("中序遍历查找");
            if (this.no == no) {
                return this;
            }
            if (this.right != null) {
                resNode = this.right.indexOrderSearch(no);
            }
            return resNode;
        }
        // 后序遍历查找
        public HeroNode postOrderSearch(int no) {
            HeroNode resNode = null;
            if (this.left != null) {
                resNode = this.left.indexOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }
            if (this.right != null) {
                resNode = this.right.indexOrderSearch(no);
            }
            if (resNode != null) {
                return resNode;
            }
            System.out.println("后序遍历查找");
            if (this.no == no) {
                return this;
            }
            return resNode;
        }
    }
}
