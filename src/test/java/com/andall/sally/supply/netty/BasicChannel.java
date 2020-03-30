package com.andall.sally.supply.netty;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 14:10 2020/3/30
 */
public class BasicChannel {

    @Test
    public void test() throws IOException {

        FileInputStream fileInputStream = new FileInputStream(new File("/Users/andaall/Desktop/work/解锁下单.txt"));

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (channel.read(byteBuffer) != -1) {
            System.out.println(new String(byteBuffer.array()));
            byteBuffer.clear();
        }

        channel.close();
        fileInputStream.close();
    }
}
