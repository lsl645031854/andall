package com.andall.sally.supply.netty.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 08:19 2020/5/4
 */
public class NewIoClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        String fileName = "protoc-3.6.1-win32.zip";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        // transferTo 底层是零拷贝 linux 一次调用可以完成， Windows有大小限制，最多传8M，需要分段
        long startTime = System.currentTimeMillis();
        long total = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
