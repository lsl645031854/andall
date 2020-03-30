package com.andall.sally.supply.netty;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 09:55 2020/4/6
 */
public class BufferTest {

    @Test
    public void mappedByteBuffer() throws Exception {
        // 直接缓存修改，修改内容
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte)'H');

        randomAccessFile.close();
    }

    @Test
    public void scatteringAndGatheringTest() throws Exception {
        //scatter 将数据写入的buffer数组，依次读
        //Gathering，从buffer读取数据是，采用buffer数组，依次读

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        //等客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 从客户端接受8个字节
        int messageLength = 8;

        // 循环的读取
        while (true) {
            int byteRead = 0;

            // 读操作
            while (byteRead < messageLength) {
                long read = socketChannel.read(buffers);
                byteRead += read;
                System.out.println("读数据");
            }

            // 将所有的buffer进行flip
            Arrays.asList(buffers).forEach(Buffer::flip);

            // 将数据显示到客户端
            int byteWrite = 0;

            while (byteWrite < messageLength) {
                long write = socketChannel.write(buffers);
                byteWrite += write;
                System.out.println("写数据");
            }

            // 将所有buffer进行clear
            Arrays.asList(buffers).forEach(Buffer::clear);

        }

    }
}
