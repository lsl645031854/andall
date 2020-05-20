package com.andall.sally.supply.netty.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 08:11 2020/5/4
 */
public class NewIoServer {
    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket socket = serverSocketChannel.socket();

        socket.bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel channel = serverSocketChannel.accept();

            int readCount = 0;
            while (-1 != readCount ) {
                try {
                    readCount = channel.read(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                buffer.rewind();
            }
        }
    }
}
