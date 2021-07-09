package com.andall.sally.supply.timewheel;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:39 下午 2021/7/8
 */
public class Main {

    public static void main(String[] args) {
        Timer timer = new Timer(1, 20);
        System.out.println("start\t" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        timer.addTask(new TimerTask(10 * 1000, () -> {
            System.out.println("task1\t" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }, "task1Desc"));
        timer.addTask(new TimerTask(5 * 1000, () -> {
            System.out.println("task2\t" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }, "task2Desc"));
        System.out.println("stop\t" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
