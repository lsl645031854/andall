package com.andall.sally.supply.datastructure.recursion;

import java.util.Scanner;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:27 2020/6/9
 */
public class MiGong3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("请输入迷宫的行列数:");
        int row, col, data;
        row = in.nextInt();
        col = in.nextInt();

        Maze maze = new Maze(row, col);

        System.out.println("请输入迷宫路径");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data = in.nextInt();
                maze.initMazeNode(data, i, j);
            }
        }

        // 修改迷宫所有节点四个方向的行走状态信息
        maze.initMazeNodePathState();
        // 寻找迷宫路径
        maze.findMazePath();
        // 打印迷宫路径搜索的结果
        maze.showMazePath();
    }
}
