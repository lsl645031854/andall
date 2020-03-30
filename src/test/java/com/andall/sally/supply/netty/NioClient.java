package com.andall.sally.supply.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lsl
 * @Description: NIO服务端
 * @Date: Created on 09:57 2020/3/21
 */
public class NioClient {

    //
    public static void main(String[] args) throws Exception{
        // 得到网络通道
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞
        socketChannel.configureBlocking(false);

        //提供服务端的端口 IP
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",
                6666);

        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，客户端不会阻塞，可以做其他的工作...");
            }
        }

        // 连接成功
        String message = "hello 大世界";

        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(buffer);

        System.in.read();
    }
}
