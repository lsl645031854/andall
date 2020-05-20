package com.andall.sally.supply.datastructure.recursion;

import java.util.Stack;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:27 2020/6/9
 */
public class MiGong2 {
    private int[][] map = {                           //迷宫地图,1代表墙壁，0代表通路
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,1,0,0,0,1,0,1},
            {1,0,0,1,0,0,0,1,0,1},
            {1,0,0,0,0,1,1,0,0,1},
            {1,0,1,1,1,0,0,0,0,1},
            {1,0,0,0,1,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,1,1,0,1,1,0,1},
            {1,1,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    private int mapX = map.length - 1;                //地图xy边界

    private int mapY = map[0].length - 1;

    private int startX = 1;                           //起点

    private int startY = 1;

    private int endX = mapX - 1;                      //终点

    private int endY = mapY - 1;
    //内部类，封装一个点    
    public class Dot{
        private int x;            //行标
        private int y;            //列标

        public Dot(int x , int y) {
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }
    }

    //内部类，封装走过的每一个点,自带方向
    public class Block extends Dot{

        private int dir;          //方向,1向上，2向右，3向下，4向左

        public Block(int x , int y) {
            super(x , y);
            dir = 1;
        }

        public int getDir(){
            return dir;
        }

        public void changeDir(){
            dir++;
        }
    }




    private Stack<Block> s = new Stack<>();      //回溯法全局辅助栈
    private Stack<Block> stack = new Stack<>();  //回溯法存储路径的栈

    private void findPath2(){
        if(startX >= 0 && startX <= mapX && startY >= 0 && startY <= mapY && map[startX][startY] == 0){
            find(startX , startY);
        }
    }
    private void find(int x , int y){
        map[x][y] = 1;
        if(x == endX && y == endY) {
            s.push(new Block(x , y));
            while(!s.empty()){
                Block pop = s.pop();
                stack.push(pop);
            }
            //return ;               //在此处返回会使后续递归再次寻找路线会经过这里，如果不返回，整个函数执行完毕，所有路径都会被访问到
        }
        s.push(new Block(x , y));
        if( x - 1 >= 0 && map[x - 1][y] == 0 ){  //可以往上走，那么往上走
            find(x - 1 , y);
        }
        if(x + 1 <= mapX && map[x + 1][y] == 0){ //可以往下走，那么往下走
            find(x + 1 , y);
        }
        if(y - 1 >= 0 && map[x][y - 1] ==0){     //往左
            find(x , y - 1);
        }
        if(y + 1 <= mapY && map[x][y + 1] == 0){ // 往右
            find(x , y + 1);
        }
        if(!s.empty()){
            map[x][y] = 3;
            s.pop();
        }
    }

    //打印栈
    private void printStack(){
        int count = 1;
        while(!stack.empty()){
            Block b = stack.pop();
            System.out.print("(" + b.getX() + "," + b.getY() + ") ");
            if(count % 10 == 0)
                System.out.println("");
            count++;
        }
    }

    public static void main(String[] args) {
        MiGong2 miGong2 = new MiGong2();

        miGong2.findPath2();
        miGong2.printStack();
        System.out.println();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(miGong2.map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
