package com.andall.sally.supply.netty;

import java.nio.IntBuffer;

/**
 * @Author: lsl
 * @Description: 了解nio的buffer
 * @Date: Created on 10:10 2020/3/21
 */
public class BisicBuffer {

    public static void main(String[] args) {
        // 创建一个buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存在数据

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //将buffer读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
