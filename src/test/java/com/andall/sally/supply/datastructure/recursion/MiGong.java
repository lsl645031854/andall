package com.andall.sally.supply.datastructure.recursion;

/**
 * @Author: lsl
 * @Description: 迷宫问题
 * 1. map表示地图
 * 2. i,j 表示从地图的哪个位置开始出发（1,1）
 * 3. map[6][5]表示找到通路
 * 4. 1：墙 2：通路 3： 走过，但走不通
 * @Date: Created on 13:25 2020/6/8
 */
public class MiGong {

    public static void main(String[] args) {
        // 模拟地图
        int[][] map = new int[8][7];

        // 使用1标识墙
        // 上下全部设为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        // 左右全部设为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
//        map[1][2] = 1;
//        map[2][2] = 1;

        System.out.println("地图的情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        // 使用递归找路
        setWay(map, 1, 1);
        System.out.println("结束");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean setWay(int[][] map, int i, int j) {

        if (map[6][5] == 2) {
            //  已经找到出口
            return true;
        } else {
            if (map[i][j] == 0) {
                // 还没走过， 下-》右-》上-》左
                map[i][j] = 2; // 假定可以走通
                if (setWay(map, i+1, j)) { // 向下走
                    return true;
                } else if (setWay(map, i, j+1)) { // 右走
                    return true;
                } else if (setWay(map, i -1, j)) { // 向上
                    return true;
                } else if (setWay(map, i, j - 1)) { // 左走
                    return true;
                } else {
                    // 走不通
                    map[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

}
