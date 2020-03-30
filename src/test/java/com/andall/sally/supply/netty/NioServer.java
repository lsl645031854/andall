package com.andall.sally.supply.netty;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 11:13 2020/4/25
 */
public class NioServer {

    public static void main(String[] args) throws Exception {

        //创建ServerSocketChannel  --> socketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //等待客户端连接
        while (true) {

            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1秒,无连接");
                continue;
            }

            // 获取有事件发生的相关的selectedKeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    // 给该客户端生成socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将当前的socketChannel注册到selector
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(512));
                }

                if (selectionKey.isReadable()) {
                    // 通过key反向获取channel
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    // 获取channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

                    int read = socketChannel.read(byteBuffer);
                    System.out.println("客户端 : "+new String(byteBuffer.array(),0,read));


                }

                // 从集合中移除selectionKey,防止重复操作
                iterator.remove();
            }

        }
    }

}
