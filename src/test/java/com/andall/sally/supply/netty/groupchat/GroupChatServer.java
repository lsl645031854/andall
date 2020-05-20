package com.andall.sally.supply.netty.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 17:10 2020/5/3
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;

    // 初始化任务
    public GroupChatServer() {

        try {
            // 得到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(6667));

            listenChannel.configureBlocking(false);
            // 将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.selectNow();
                if (count > 0) { // 有事件需要处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();

                            sc.configureBlocking(false);
                            // 将sc注册到selector
                            sc.register(selector, SelectionKey.OP_READ);
                            // 给出提示
                            System.out.println(sc.getRemoteAddress() + "  上线");
                        }

                        if(key.isReadable()) { // read事件
                            // 处理读
                            readData(key);
                        }

                        // 当前的key 删除，防止重复处理
                        iterator.remove();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        //定义一个selectChannel
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);

            if (count > 0) {
                // 把缓冲区的数据转成字符串
                String message = new String(buffer.array(), 0, count);
                System.out.println("客户端消息：" + message);
                // 向其他客户端转发消息 (去除自己)
                sendToOtherClient(message, socketChannel);
            }

            // count -1在网络io中就是socket关闭的含义，在文件是末尾的含义
            if (count == -1) {
                try {
                    System.out.println(socketChannel.getRemoteAddress() + " 离线...");
                    // 取消注册
                    key.cancel();
                    // 关闭同道
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 转发消息给其它客户
    private void sendToOtherClient (String message, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中....");
        // 遍历所有注册到selector的channel，排除自己
        for (SelectionKey key : selector.keys()) {
            // 通过key 取出channel
            Channel channel = key.channel();

            // 排除自己
            if(channel instanceof SocketChannel && channel != selfChannel) {
                // 转型
                SocketChannel socketChannel = (SocketChannel) channel;
                // 将消息存到buffer
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());

                socketChannel.write(buffer);
            }

        }
    }
    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
